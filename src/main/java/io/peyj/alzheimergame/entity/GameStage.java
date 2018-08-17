package io.peyj.alzheimergame.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
public class GameStage implements Serializable {
    @Id
    private long id;
    private StageType stageType;
    @ManyToOne(fetch = FetchType.LAZY)
    private GameLevel level;
    //TODO: add location definition
    @ManyToOne
    private RiddleScenario riddleScenario;
    @Column(nullable = false)
    private String answer;
    @JoinColumn(name = "previous_stage_id")
    @OneToOne(fetch = FetchType.LAZY, targetEntity = GameStage.class, cascade = CascadeType.ALL)
    private GameStage previousStage;
    //TODO: consider current location of the player
    @OneToOne(mappedBy = "previousStage", fetch = FetchType.LAZY, targetEntity = GameStage.class, cascade = CascadeType.ALL)
    private GameStage nextStage;

    public boolean validateAnswer(String input) {
        return !StringUtils.isEmpty(input) && this.answer.trim().equals(input.trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameStage)) return false;
        GameStage stage = (GameStage) o;
        return this.id == stage.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
