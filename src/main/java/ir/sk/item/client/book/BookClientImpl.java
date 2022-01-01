package ir.sk.item.client.book;

import ir.sk.item.dto.book.Book;
import ir.sk.item.dto.book.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BookClientImpl implements BookClient {

    private static final String QUERY_PARAM_MAX_RESULTS = "maxResults";
    private static final String QUERY_PARAM_Q = "q";


    @Value("${spring.application.google.baseUrl}")
    private String bookRequestUrl;

    @Value("${spring.application.google.limit:5}")
    private int maxResults;

    private final RestTemplate restTemplate;

    @Override
    public List<Book> getBooks(String searchTerm) {
        try {
            final UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(bookRequestUrl)
                    .queryParam(QUERY_PARAM_Q, searchTerm)
                    .queryParam(QUERY_PARAM_MAX_RESULTS, maxResults)
                    .queryParam("fields", "items(volumeInfo(title,authors))");

            BookResponse bookResponse = restTemplate.getForEntity(urlBuilder.build().toUri().toString(), BookResponse.class).getBody();
            return bookResponse.getItems();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("An http error was happened on get Books from Google");
            log.debug("Http Status: [] Body: []", e.getStatusCode(), e.getResponseBodyAsString());
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("An error was happened on get Books from Google: []", e.getMessage());
            return Collections.emptyList();
        }
    }


    /**
     * the Actuator Health Endpoint for Book API
     */
    public Health isRunning() {
        try {
            final UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(bookRequestUrl)
                    .queryParam(QUERY_PARAM_Q, "reza")
                    .queryParam(QUERY_PARAM_MAX_RESULTS, maxResults);

            ResponseEntity<BookResponse> result = restTemplate.getForEntity(urlBuilder.build().toUri().toString(), BookResponse.class);
            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                return Health.up().withDetail("data", result.getBody()).build();
            } else {
                return Health.down().withDetail("status", result.getStatusCode()).build();
            }
        } catch (RestClientException ex) {
            return Health.down().withException(ex).build();
        }
    }
}
