package io.peyj.alzheimergame.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Player {
    @Id
    private long id;
    private Date subscriptionDate;
    @Column(unique = true)
    private String uuid;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<PlayerAttribute> attributes;
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private List<ActionLog> logs;
    @OneToMany(mappedBy = "player")
    private Set<PlayerAchievementLog> achievements;

    public void addPlayerAttribute(PlayerAttribute attribute){
        this.attributes.add(attribute);
    }

    public void addActionLog(ActionLog actionLog){
        this.logs.add(actionLog);
    }

    public void addAchievement(PlayerAchievementLog achievementLog){
        this.achievements.add(achievementLog);
    }

    public static Player freshPlayer(String uuid) {
        Player player = new Player();
        player.setSubscriptionDate(new Date());
        player.setUuid(StringUtils.isEmpty(uuid) ? UUID.randomUUID().toString() : uuid);
        //TODO: read initial attributes from input. example: client agent.
        Set<PlayerAttribute> initialAttributes = new HashSet<>();
        player.setAttributes(initialAttributes);
        return player;
    }
}

