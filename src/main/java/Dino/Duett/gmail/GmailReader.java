package Dino.Duett.gmail;

import Dino.Duett.config.EnvBean;
import Dino.Duett.gmail.exception.GmailException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.ComparisonTerm;
import jakarta.mail.search.FromStringTerm;
import jakarta.mail.search.ReceivedDateTerm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j(topic = "GmailReader")
@Component
public class GmailReader {
    private final EnvBean envBean;
    private final Session SESSION;
    private final String[] DOMAINS = {"@ktfmms.magicn.com", "@mmsmail.uplus.co.kr", "@vmms.nate.com"};
    GmailReader(EnvBean envBean) {
        this.envBean = envBean;
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");

        SESSION = Session.getInstance(properties);
    }

    private Message getLastMessages(Message[] messages) throws GmailException, MessagingException {
        if (messages == null || messages.length == 0) {
            throw new GmailException.MessageNotFoundException();
        }

        // 통신 3사의 도메인 중 하나가 발신자에 포함되어 있는 메일을 찾음
        for (Message message : messages) {
            Address[] from = message.getFrom();
            // 주소에서 도메인을 확인. 대부분 주소는 하나만 있음
            for (Address address : from) {
                String addressStr = address.toString();
                // 통신 3사의 도메인이 포함되어 있는 경우 해당 메일을 반환
                for (String domain : DOMAINS) {
                    if (addressStr.contains(domain)) {
                        return message;
                    }
                }
            }
        }
        throw new GmailException.MessageNotFoundException();
    }

    private String getBody(Message message) throws IOException, MessagingException {
        Object content = message.getContent();
        // 메일의 내용이 multipart 아닌 경우 예외
        if (!(content instanceof Multipart multipart)) {
            throw new GmailException.InvalidContentTypeException();
        }
        BodyPart bodyPart = multipart.getBodyPart(0);
        String body = bodyPart.getContent().toString();
        // 메일의 내용 중 첫 줄만 가져옴
        int idx = body.indexOf("\r\n");
        if (idx != -1) {
            return body.substring(0, idx);
        }
        return body;
    }

    public void validate(String phoneNumber, String code) throws GmailException {
        // 전화번호가 숫자로만 이루어져 있는지 확인
        if (phoneNumber.matches(".*[^0-9].*")) {
            throw new GmailException.EmailValidationFailedException();
        }

        try (Store store = SESSION.getStore()) {
            if (!store.isConnected()) {
                store.connect(envBean.getEmailUsername(), envBean.getEmailPassword());
            }
            try (Folder inbox = store.getFolder("INBOX")) {
                inbox.open(Folder.READ_ONLY);

                // 발신자 번호를 이용한 검색
                Message[] messages = inbox.search(new FromStringTerm(phoneNumber));

                // 날짜 기준으로 최신순 정렬
                Arrays.sort(messages, (m1, m2) -> {
                    try {
                        return m2.getReceivedDate().compareTo(m1.getReceivedDate());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                });

                // 가장 최근 메일을 가져옴
                Message lastMessage = getLastMessages(messages);
                // 메일의 내용을 가져옴
                String body = getBody(lastMessage);
                // 메일 내용과 코드가 일치하는지 확인
                if (!body.equals(code)) {
                    log.warn("Code does not match: " + body + " " + code);
                    Map<String, String> err = new HashMap<>();
                    err.put("body", body);
                    err.put("code", code);
                    throw new GmailException.EmailValidationFailedException(err);
                }
            }
        } catch (GmailException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GmailException.EmailValidationFailedException();
        }
    }


    public void deleteOldMails() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        try (Store store = SESSION.getStore()) {
            if (!store.isConnected()) {
                store.connect(envBean.getEmailUsername(), envBean.getEmailPassword());
            }
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            LocalDateTime threeDaysAgo = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(3);
            Date searchDate = Date.from(threeDaysAgo.atZone(ZoneId.of("Asia/Seoul")).toInstant());
            ReceivedDateTerm olderThanThreeDays = new ReceivedDateTerm(ComparisonTerm.LT, searchDate);

            Message[] messages = inbox.search(olderThanThreeDays);

            for (Message message : messages) {
                message.setFlag(Flags.Flag.DELETED, true);
            }

            inbox.close(true);
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
    }

    public void sendWithdrawalEmail(String phoneNumber, String reason) {
        // 회원탈퇴 이유 이메일 전송
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(envBean.getEmailUsername(), envBean.getEmailPassword());
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(envBean.getEmailUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(envBean.getEmailWithdrawal()));
            message.setSubject("Duett 회원 탈퇴 안내 - " + phoneNumber);
            message.setText(reason);
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email");
            e.printStackTrace();
            throw new GmailException.EmailValidationFailedException();
        }
    }
}
