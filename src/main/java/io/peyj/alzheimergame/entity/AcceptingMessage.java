package io.peyj.alzheimergame.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class AcceptingMessage {
    @Id
    @GeneratedValue
    private long id;
    private String message;
    private Date createdDate;
}
