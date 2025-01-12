package Dino.Duett.domain.message.scheduler;

import Dino.Duett.domain.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Component
public class MessageScheduler {
    private final MessageRepository messageRepository;
    @Scheduled(cron = "0 0 4 * * ?", zone = "Asia/Seoul")
    @Transactional
    public void deleteOldMessagesScheduler() {
        LocalDate cutoffDate = LocalDateTime.now().toLocalDate().minusWeeks(2);
        messageRepository.deleteByCreatedDateBefore(cutoffDate.atStartOfDay());
    }

}
