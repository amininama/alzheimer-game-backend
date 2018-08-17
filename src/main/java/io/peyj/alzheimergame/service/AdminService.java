package io.peyj.alzheimergame.service;

import io.peyj.alzheimergame.entity.GameLevel;

/**
 * should handle all the services the admin needs
 */
public interface AdminService {
    /**
     * creates a new level
     *
     * @param basicLevelInfo
     * @return information about the new level to be displayed to the admin
     */
    GameLevel createNewLevel(GameLevel basicLevelInfo);

    /**
     * adds to or updates the list of levels
     *
     * @param updatedLevelData
     * @return updated version of game-level to be displayed to the admin
     */
    GameLevel updateLevel(GameLevel updatedLevelData);
}
