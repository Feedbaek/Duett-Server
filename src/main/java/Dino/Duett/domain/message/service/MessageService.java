package Dino.Duett.domain.message.service;

import Dino.Duett.domain.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2(topic = "messageServiceLogger")
public class MessageService {
    private final MessageRepository messageRepository;

}
