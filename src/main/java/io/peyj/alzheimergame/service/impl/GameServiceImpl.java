package io.peyj.alzheimergame.service.impl;

import io.peyj.alzheimergame.entity.ActionLog;
import io.peyj.alzheimergame.entity.GameLevel;
import io.peyj.alzheimergame.entity.GameStage;
import io.peyj.alzheimergame.entity.Player;
import io.peyj.alzheimergame.repository.GameLevelRepository;
import io.peyj.alzheimergame.service.GameService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    private GameLevelRepository gameLevelRepository;

    public GameServiceImpl(GameLevelRepository gameLevelRepository) {
        this.gameLevelRepository = gameLevelRepository;
    }

    @Override
    public Optional<GameLevel> getFirstLevel() {
        return gameLevelRepository.findById(1L);
    }

    @Override
    public Optional<GameStage> getFirstStage(GameLevel gameLevel) {
        List<GameStage> stages = gameLevel.getStages();
        //the first stage of each level is the one that has no previous stages
        for (GameStage stage : stages) {
            if (stage.getPreviousStage() == null)
                return Optional.of(stage);
        }
        return Optional.empty();
    }

    @Override
    public GameLevel getNextLevel(Player player) {
        Set<Long> finishedLevelIds = getPlayerFinishedLevels(player);
        List<GameLevel> notFinishedYet = gameLevelRepository.findByIdNotIn(finishedLevelIds);
        Random random = new Random();
        int nextLevelIndex = random.nextInt(notFinishedYet.size());
        GameLevel nextLevel = notFinishedYet.get(nextLevelIndex);
        return nextLevel;
    }

    private Set<Long> getPlayerFinishedLevels(Player player) {
        Set<Long> finishedLevelIds = new HashSet<>();
        for(ActionLog log : player.getLogs()){
            finishedLevelIds.add(log.getGameStage().getLevel().getId());
        }
        return finishedLevelIds;
    }
}
