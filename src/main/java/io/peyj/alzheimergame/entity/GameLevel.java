package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class GameLevel {
    @Id
    private long id;
    private String title;
    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
    private List<GameStage> stages;
    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
    private List<GameLevelPreCondition> preConditions;
    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
    private List<PlayerAchievementLog> achievementLogs;

    public void addAchievementLog(PlayerAchievementLog newLog){
        this.achievementLogs.add(newLog);
    }
}
