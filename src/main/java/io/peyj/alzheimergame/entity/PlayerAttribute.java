package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PlayerAttribute {
    @Id
    private long id;
    @ManyToOne(targetEntity = Player.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Player owner;
    @Column(name = "attribute_key")
    private String key;
    @Column(name = "attribute_value")
    private String value;
    private Date creationDate;
}
