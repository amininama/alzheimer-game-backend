package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.GameLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GameLevelRepository extends JpaRepository<GameLevel, Long> {
    List<GameLevel> findByIdNotIn(Set<Long> ids);
}
