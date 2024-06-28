package Dino.Duett.domain.message.controller;

import Dino.Duett.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    // todo: Implement this
    @GetMapping("/all")
    public String getAllMessages() {
        return "All messages";
    }

    // todo: implement this
    @PostMapping("/send")
    public String sendMessage() {
        return "Message sent";
    }
}
