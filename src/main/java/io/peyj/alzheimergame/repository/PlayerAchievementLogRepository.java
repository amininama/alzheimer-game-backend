package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.PlayerAchievementLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAchievementLogRepository extends JpaRepository<PlayerAchievementLog, Long> {
}
