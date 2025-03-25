package com.book.store.athena.api.google;

import com.book.store.athena.api.AppConfig;
import com.book.store.athena.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SafeBrowsingService {

    @Value("${browsing_api}")
    private String apiKey;

    private final AppConfig appConfig;

    private final static String BROWSING_URL = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=";

    public SafeBrowsingService (AppConfig appConfig) {

        this.appConfig = appConfig;

    }

    public void verifyIfUrlIsUnsafe (String sentUrl) {

        String requestUrl = BROWSING_URL + apiKey;

        HttpEntity<String> requestEntity = getStringHttpEntity(sentUrl);

        ResponseEntity<String> response = appConfig.getRestTemplate().exchange(
                requestUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
            if (response.getBody().contains("matches")) {
                throw new BadRequestException("This URL is unsafe. Please, try another one!");
            }
        }

    }

    private static HttpEntity<String> getStringHttpEntity(String sentUrl) {

        String requestPayload = String.format("""
    
        {
        "client": {
            "clientId": "AthenaLibrary",
            "clientVersion": "1.5.2"
        },
        "threatInfo": {
            "threatTypes": ["MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE", "POTENTIALLY_HARMFUL_APPLICATION"],
            "platformTypes": ["ALL_PLATFORMS", "PLATFORM_TYPE_UNSPECIFIED"],
            "threatEntryTypes": ["URL"],
            "threatEntries": [
                { "url": "%s" }
            ]
        }
    }
    """, sentUrl);


        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        return new HttpEntity<>(requestPayload, headers);

    }

}
