package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.PlayerState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStateRepository extends CrudRepository<PlayerState, String> {
}
