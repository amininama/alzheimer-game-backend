package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RiddleStep {
    @Id
    @GeneratedValue
    private long id;
    @Column(name= "order_in_scenario")
    private int order;          //defines the order of this step in the scenario
    @ManyToOne(fetch = FetchType.LAZY)
    private RiddleScenario scenario;
    private String monologue;
    private long delay;         //delay before sending monologue in milliseconds
    private String mediaLink;   //ni case we want to send a pic/vid/etc.
}
