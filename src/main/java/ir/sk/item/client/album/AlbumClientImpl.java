package ir.sk.item.client.album;

import ir.sk.item.dto.album.Album;
import ir.sk.item.dto.album.AlbumResponse;
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
public class AlbumClientImpl implements AlbumClient {

    private static final String QUERY_PARAM_LIMIT = "limit";
    private static final String QUERY_PARAM_TERM = "term";

    @Value("${spring.application.iTunes.baseUrl}")
    private String albumRequestUrl;

    @Value("${spring.application.iTunes.limit:5}")
    private int maxResults;

    private final RestTemplate restTemplate;

    @Override
    public List<Album> getMusics(String searchTerm) {
        try {
            final UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(albumRequestUrl)
                    .queryParam(QUERY_PARAM_TERM, searchTerm)
                    .queryParam(QUERY_PARAM_LIMIT, maxResults);

            AlbumResponse albumResponse = restTemplate.getForEntity(urlBuilder.build().toUri().toString(), AlbumResponse.class).getBody();

            return albumResponse.getResults();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("An http error was happened on get Albums from ITunes");
            log.debug("Http Status: [] Body: []", e.getStatusCode(), e.getResponseBodyAsString());
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("An error was happened on get Albums from ITunes: []", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * the Actuator Health Endpoint for Album API
     */
    @Override
    public Health isRunning() {
        try {
            final UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(albumRequestUrl)
                    .queryParam(QUERY_PARAM_TERM, "reza")
                    .queryParam(QUERY_PARAM_LIMIT, maxResults);

            ResponseEntity<AlbumResponse> result = restTemplate.getForEntity(urlBuilder.build().toUri().toString(), AlbumResponse.class);
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
