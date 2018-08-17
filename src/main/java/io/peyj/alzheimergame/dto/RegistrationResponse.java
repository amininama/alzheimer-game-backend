package io.peyj.alzheimergame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    @NotNull
    private String uuid;
    @NotNull
    private String message;
}
