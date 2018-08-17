package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.AcceptingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcceptingMessageRepository extends JpaRepository<AcceptingMessage, Long> {
}
