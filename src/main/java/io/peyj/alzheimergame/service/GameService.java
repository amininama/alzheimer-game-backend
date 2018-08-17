package io.peyj.alzheimergame.service;

import io.peyj.alzheimergame.entity.GameLevel;
import io.peyj.alzheimergame.entity.GameStage;
import io.peyj.alzheimergame.entity.Player;
import io.peyj.alzheimergame.entity.RiddleScenario;

import java.util.Optional;

/**
 * handles the functionalities related to GameLevels and GameStages
 */
public interface GameService {
    /**
     * this method is used to fetch the first level of the game.
     * by convention, the game with id = 1 will be considered the first level
     *
     * @return first level of the game
     */
    Optional<GameLevel> getFirstLevel();

    /**
     * returns the first stage of a given level.
     *
     * @param gameLevel target level
     * @return first stage of the target level
     */
    Optional<GameStage> getFirstStage(GameLevel gameLevel);

    /**
     * puts the player into the next level
     *
     * @param player player who has won a level
     * @return  GameLevel instance
     */
    GameLevel getNextLevel(Player player);
}
