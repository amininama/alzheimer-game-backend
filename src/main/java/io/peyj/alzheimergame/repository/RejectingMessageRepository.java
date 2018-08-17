package io.peyj.alzheimergame.repository;

import io.peyj.alzheimergame.entity.RejectingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectingMessageRepository extends JpaRepository<RejectingMessage, Long> {
}
