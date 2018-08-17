package io.peyj.alzheimergame.service.impl;

import io.peyj.alzheimergame.dto.RegistrationResponse;
import io.peyj.alzheimergame.entity.*;
import io.peyj.alzheimergame.exception.PlayerNotFoundException;
import io.peyj.alzheimergame.exception.RegistrationException;
import io.peyj.alzheimergame.repository.*;
import io.peyj.alzheimergame.service.GameService;
import io.peyj.alzheimergame.service.PlayerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;
    private GameService gameService;
    private ActionLogRepository actionLogRepository;
    private PlayerStateRepository playerStateRepository;
    private AcceptingMessageRepository acceptingMessageRepository;
    private RejectingMessageRepository rejectingMessageRepository;
    private PlayerAchievementLogRepository playerAchievementLogRepository;
    private GameLevelRepository gameLevelRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, GameService gameService, ActionLogRepository actionLogRepository,
                             PlayerStateRepository playerStateRepository, AcceptingMessageRepository acceptingMessageRepository,
                             RejectingMessageRepository rejectingMessageRepository,
                             PlayerAchievementLogRepository playerAchievementLogRepository, GameLevelRepository gameLevelRepository) {
        this.playerRepository = playerRepository;
        this.gameService = gameService;
        this.actionLogRepository = actionLogRepository;
        this.playerStateRepository = playerStateRepository;
        this.acceptingMessageRepository = acceptingMessageRepository;
        this.rejectingMessageRepository = rejectingMessageRepository;
        this.playerAchievementLogRepository = playerAchievementLogRepository;
        this.gameLevelRepository = gameLevelRepository;
    }

    @Override
    @Transactional
    public RegistrationResponse register(String uuid) throws RegistrationException {
        //create initial player instance
        Player player = Player.freshPlayer(uuid);
        player = playerRepository.save(player);
        //put player in the first level
        ActionLog firstLog = registrationActionLog(player);
        player.addActionLog(firstLog);
        playerRepository.saveAndFlush(player);
        return new RegistrationResponse(player.getUuid(), firstLog.getOutput());
    }

    @Override
    public RiddleScenario play(@NotNull String playerUUID, @NotNull String input) {
        Player player = null;
        try {
            player = getPlayerByUUID(playerUUID);
        } catch (PlayerNotFoundException e) {
            return playerNotFoundAction();
        }
        PlayerState currentState = getPlayerCurrentState(player);
        GameStage currentGameStage = currentState.getCurrentStage();
        boolean answerIsCorrect = currentGameStage.validateAnswer(input);
        //TODO: find a way to gather player's attributes
        if (answerIsCorrect) {
            String message = decideAcceptingMessage();
            GameStage nextStage = currentGameStage.getNextStage();
            if (nextStage == null) {    //this means player has completed the mission!
                //consider it an achievement!
                player = addNewPlayerAchievement(player, currentGameStage);
                //decide next mission!
                GameLevel nextLevel = gameService.getNextLevel(player);
                nextStage = nextLevel.getStages().get(0);
            }
            updatePlayerNextStage(player, currentState, nextStage, input);
            RiddleScenario newScenario = nextStage.getRiddleScenario();
            newScenario.insertAcceptingMessage(message);
            return newScenario;
        } else {
            updatePlayerNextStage(player, currentState, currentGameStage, input);
            return decideRejectingMessage();
        }
    }

    private static RiddleScenario playerNofFoundMessage = new RiddleScenario();
    static {
        LinkedList<RiddleStep> riddleSteps = new LinkedList<>();
        RiddleStep riddleStep = new RiddleStep();
        riddleStep.setOrder(0);
        riddleStep.setMonologue("کی هستی تو؟ اصلا یادم نمیاد...");
        riddleSteps.add(riddleStep);
        playerNofFoundMessage.setSteps(riddleSteps);
    }

    /**
     * returns a riddle-scenario with a single message indicating the user is not found
     * @return
     */
    private RiddleScenario playerNotFoundAction() {
        return playerNofFoundMessage;
    }

    private Player getPlayerByUUID(@NotNull String playerUUID) throws PlayerNotFoundException {
        Optional<Player> player = playerRepository.findPlayerByUuid(playerUUID);
        if (!player.isPresent())
            throw new PlayerNotFoundException();
        return player.get();
    }

    private Player addNewPlayerAchievement(Player player, GameStage currentStage) {
        PlayerAchievementLog achievementLog = new PlayerAchievementLog();
        achievementLog.setDate(new Date());
        achievementLog.setLevel(currentStage.getLevel());
        achievementLog.setPlayer(player);
        achievementLog = playerAchievementLogRepository.save(achievementLog);
        GameLevel currentLevel = currentStage.getLevel();
        currentLevel.addAchievementLog(achievementLog);
        gameLevelRepository.save(currentLevel);
        player.addAchievement(achievementLog);
        return playerRepository.save(player);
    }

    /**
     * finds the player's current state
     *
     * @param player player's info
     * @return PlayerState instance
     */
    private PlayerState getPlayerCurrentState(Player player) {
        Optional<PlayerState> currentState = playerStateRepository.findById(player.getUuid());
        if (currentState.isPresent()) {
            return currentState.get();
        }
        //in case player is present, it might mean that we missed the player's last state
        //so we should refer to action-logs and update the cache (if needed)
        List<ActionLog> playerActionLog = player.getLogs();
        ActionLog lastLogFromDB = playerActionLog.get(playerActionLog.size() - 1);
        GameStage lastStageFromLogs = lastLogFromDB.getGameStage();
        Date lastDateFromDB = lastLogFromDB.getDate();
        PlayerState stateFromLogs = new PlayerState(player.getUuid(), lastStageFromLogs, lastDateFromDB, 0);
        return playerStateRepository.save(stateFromLogs);
    }

    /**
     * fetches a random rejecting message & wraps it up in a riddle-scenario
     *
     * @return random rejecting message as a riddleScenario
     */
    private RiddleScenario decideRejectingMessage() {
        Random random = new Random();
        List<RejectingMessage> rejectingMessages = rejectingMessageRepository.findAll();
        String message;
        if (rejectingMessages.isEmpty()) {
            message = "نع";
        } else {
            int randomIndex = random.nextInt(rejectingMessages.size());
            message = rejectingMessages.get(randomIndex).getMessage();
        }
        return RiddleScenario.singleStepScenario(message);
    }

    /**
     * fetches a random accepting message
     *
     * @return random accepting message String
     */
    private String decideAcceptingMessage() {
        List<AcceptingMessage> acceptingMessages = acceptingMessageRepository.findAll();
        if (acceptingMessages.isEmpty())
            return "آفرین";
        Random random = new Random();
        int randomIndex = random.nextInt(acceptingMessages.size());
        return acceptingMessages.get(randomIndex).getMessage();
    }

    /**
     * sets the player into the first stage of the game
     *
     * @param player newly registered player
     * @return first action log
     * @throws RegistrationException in case we're unable to find the first level/stage
     */
    private ActionLog registrationActionLog(Player player) throws RegistrationException {
        Date now = new Date();
        Optional<GameLevel> startingLevel = gameService.getFirstLevel();
        if (!startingLevel.isPresent())
            throw new RegistrationException("no starting level is found");
        Optional<GameStage> firstStage = gameService.getFirstStage(startingLevel.get());
        if (!firstStage.isPresent()) {
            throw new RegistrationException("no first stage is found!");
        }
        ActionLog actionLog = new ActionLog();
        actionLog.setGameStage(firstStage.get());
        actionLog.setDate(now);
        actionLog.setInput("");
        actionLog.setLocation(null);//todo: fix this
        actionLog.setOutput(firstStage.get().getAnswer());
        actionLog.setPlayer(player);
        PlayerState playerState = new PlayerState(player.getUuid(), firstStage.get(), now, 0);
        playerStateRepository.save(playerState);
        return actionLogRepository.saveAndFlush(actionLog);
    }

    /**
     * while the player is proceeding within the game, this method will be used to save their process
     * it saves new player activities and updates the player's state in cache
     *
     * @param player       player's info
     * @param currentState current player state
     * @param nextStage    next stage
     * @param input        player's latest answer
     */
    private void updatePlayerNextStage(Player player, PlayerState currentState, GameStage nextStage, String input) {
        Date now = new Date();
        //insert an action log
        ActionLog actionLog = new ActionLog();
        actionLog.setPlayer(player);
        actionLog.setOutput(nextStage.getRiddleScenario().toString());
        actionLog.setInput(input);
        actionLog.setLocation(null);
        actionLog.setDate(now);
        actionLog.setGameStage(nextStage);
        actionLog = actionLogRepository.save(actionLog);
        //add new actionLog to player's list
        player.addActionLog(actionLog);
        playerRepository.save(player);
        //update player state
        int retryCount = 0;
        if (currentState.getCurrentStage().equals(nextStage)) {
            retryCount = currentState.getRetryCount() + 1;
        }
        PlayerState playerState = new PlayerState(player.getUuid(), nextStage, now, retryCount);
        playerStateRepository.save(playerState);
    }
}
