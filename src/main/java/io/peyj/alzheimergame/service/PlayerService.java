package io.peyj.alzheimergame.service;

import io.peyj.alzheimergame.dto.RegistrationResponse;
import io.peyj.alzheimergame.entity.RiddleScenario;
import io.peyj.alzheimergame.exception.PlayerNotFoundException;
import io.peyj.alzheimergame.exception.RegistrationException;

/**
 * handles the flow of the game.
 * player is only able to interact with this service
 */
public interface PlayerService {
    /**
     * registers a new player
     *
     * @return player's uuid + initial message
     */
    RegistrationResponse register(String uuid) throws RegistrationException;

    /**
     * decides what to send to player in response to a given input.
     * the response will be made based on player's current state(level, stage, location, ...)
     * TODO: add location parameter
     *
     * @param playerUUID player's uuid
     * @param input      player's input
     * @return next RiddleScenario to be sent to the player
     */
    RiddleScenario play(String playerUUID, String input);
}
