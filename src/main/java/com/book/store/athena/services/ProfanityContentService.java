package com.book.store.athena.services;

import com.book.store.athena.exceptions.BadRequestException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ProfanityContentService {

    private final ChatClient chatClient;

    public ProfanityContentService (ChatClient.Builder chatClient) {

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

    public void checkHarmfulLinksAndUrls (String text) {

        var aiResponse = chatClient.prompt()
                        .system("""

                        You are a security AI responsible for verifying the safety of URLs. When given a URL, your task is to check the following:

                        Ensure that the URL starts with "https://" for secure connection.

                        If the URL contains any malicious content, such as viruses, phishing attempts, adult content, or anything inappropriate, return the word "prohibited."

                        If the URL is safe and meets the criteria, do not return anything (just leave it blank).
                     
                      """)
                        .user(text)
                        .call()
                        .content();

        assert aiResponse != null;

        if (aiResponse.contains("prohibited")) {

            throw new BadRequestException("This URL is unsafe. Try again.");

        }

    }

}
