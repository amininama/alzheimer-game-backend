package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class GameLevelPreCondition {
    @Id
    private long id;
    private String title;
    @ManyToOne()
    private GameLevel level;
    //todo: add location constraints
}
