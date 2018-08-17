package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PlayerAchievementLog {
    @Id
    private long id;
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;
    @ManyToOne(fetch = FetchType.LAZY)
    private GameLevel level;
}
