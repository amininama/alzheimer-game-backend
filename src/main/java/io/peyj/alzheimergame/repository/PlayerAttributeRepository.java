package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.PlayerAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAttributeRepository extends JpaRepository<PlayerAttribute, Long> {
}
