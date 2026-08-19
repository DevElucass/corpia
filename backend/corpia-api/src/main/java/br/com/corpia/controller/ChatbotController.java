package br.com.corpia.controller;

import br.com.corpia.dto.ChatbotRequest;
import br.com.corpia.service.ChatbotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/perguntar")
    public String perguntar(@RequestBody ChatbotRequest dados) {

        String pergunta = dados.pergunta();

        return chatbotService.responder(pergunta);
    }
}