package io.peyj.alzheimergame.controller;

import io.peyj.alzheimergame.dto.PlayerMove;
import io.peyj.alzheimergame.dto.RegistrationResponse;
import io.peyj.alzheimergame.entity.RiddleScenario;
import io.peyj.alzheimergame.exception.RegistrationException;
import io.peyj.alzheimergame.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PlayerController {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/player/play")
    public ResponseEntity<RiddleScenario> play(@RequestBody @Valid PlayerMove playerMove) {
        RiddleScenario riddleScenario = playerService.play(playerMove.getUuid(), playerMove.getInput());
        return ResponseEntity.ok(riddleScenario);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/player/register")
    public ResponseEntity<RegistrationResponse> register(String playerUUID) throws RegistrationException {
        RegistrationResponse registrationResponse = playerService.register(playerUUID);
        return ResponseEntity.ok(registrationResponse);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<RegistrationResponse> handleRegistrationError(RegistrationException error) {
        RegistrationResponse errorResponse = new RegistrationResponse("", error.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }
}
