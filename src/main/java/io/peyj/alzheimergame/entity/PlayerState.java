package io.peyj.alzheimergame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * this entity will hold the player's latest status.
 * player-state will only be stored in redis
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("alzheimer#player#state#")
public class PlayerState implements Serializable {
    @Id
    private String id;              //player's uuid;
    private GameStage currentStage;
    private Date enteredDate;
    //todo: add player's last known location
    private int retryCount;         //shows how many times the player has tried to pass the current stage
}


