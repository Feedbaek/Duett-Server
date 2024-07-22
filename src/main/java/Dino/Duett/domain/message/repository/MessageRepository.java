package Dino.Duett.domain.message.repository;

import Dino.Duett.domain.message.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverId(Long memberId, Pageable page);
    List<Message> findAllBySenderId(Long memberId, Pageable page);
    void deleteByCreatedDateBefore(LocalDateTime date);
    boolean existsByReceiverIdAndSenderId(Long receiverId, Long senderId);
}
