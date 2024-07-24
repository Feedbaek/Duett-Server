package Dino.Duett.gmail.scheduler;

import Dino.Duett.gmail.GmailReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GmailScheduler {
    // SESSION
    private final GmailReader gmailReader;

    @Scheduled(cron = "0 0 4 * * ?", zone = "Asia/Seoul")
    public void deleteOldGmailScheduler() {
        gmailReader.deleteOldMails();
    }
}
