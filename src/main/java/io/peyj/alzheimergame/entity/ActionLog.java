package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class ActionLog {
    @Id
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;
    private Date date;
    private String location;
    private String input;
    private String output;
    @ManyToOne(fetch = FetchType.LAZY)
    private GameStage gameStage;
}
