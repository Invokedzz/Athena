package com.book.store.athena.services;

import com.book.store.athena.exceptions.BadRequestException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ProfanityContentService {

    private final ChatClient chatClient;

    public ProfanityContentService(ChatClient.Builder chatClient) {

        this.chatClient = chatClient.build();

    }

    public void checkProfanityLevel (String text) {

        var aiResponse = chatClient.prompt()
                        .system("""
                                Task: You are a moderator for a server. Your job is to analyze each message from users and check if it contains any slurs or offensive phrases.
                                
                                Instructions:
                                
                                If the message contains any form of a slur, hate speech, or offensive language, respond with the word: "profanity".
                                
                                If the message does not contain any offensive language or slurs, respond with the word: "normal".""")
                        .user(text)
                        .call()
                        .content();

        assert aiResponse != null;

        if (aiResponse.contains("profanity")) {

            throw new BadRequestException("Your message contains an offensive phrase. Please, try again.");

        }

    }

}
