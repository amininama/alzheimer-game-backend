package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.GameLevelPreCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLevelPreConditionRepository extends JpaRepository<GameLevelPreCondition, Long> {
}
