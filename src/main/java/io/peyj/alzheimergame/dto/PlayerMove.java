package io.peyj.alzheimergame.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PlayerMove {
    @NotNull(message = "invalid request")
    @Size(min = 36, max = 36, message = "invalid request")
    private String uuid;
    @NotNull(message = "invalid request")
    private String input;
}
