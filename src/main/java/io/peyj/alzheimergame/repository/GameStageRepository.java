package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.GameStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStageRepository extends JpaRepository<GameStage, Long> {
}
