package io.peyj.alzheimergame.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NewGameLevelRequest {
    @NotNull(message = "dude, Give the new level a title!")
    private String title;
}
