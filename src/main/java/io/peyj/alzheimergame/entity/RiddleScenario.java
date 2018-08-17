package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * this class would be used to transfer a question to the player.
 * it should provide the required data to mimic the behaviour of a real person
 */
@Entity
@Data
public class RiddleScenario {
    @Id
    @GeneratedValue
    private long id;
    @OneToMany(mappedBy = "scenario")
    @OrderBy("order ASC")
    private List<RiddleStep> steps;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (RiddleStep step : steps) {
            result.append(step.getMonologue()).append("_BRK_");
        }
        return result.toString();
    }

    public void insertAcceptingMessage(String message) {
        RiddleStep newFirstStep = new RiddleStep();
        newFirstStep.setDelay(randomDelay());
        newFirstStep.setMediaLink("");
        newFirstStep.setMonologue(message);
        newFirstStep.setOrder(0);
        ((LinkedList<RiddleStep>) steps).addFirst(newFirstStep);
    }

    private long randomDelay() {
        Random random = new Random();
        return random.nextInt(20) * 1000;
    }

    public static RiddleScenario singleStepScenario(String monologue) {
        RiddleScenario riddleScenario = new RiddleScenario();
        riddleScenario.insertAcceptingMessage(monologue);
        return riddleScenario;
    }
}
