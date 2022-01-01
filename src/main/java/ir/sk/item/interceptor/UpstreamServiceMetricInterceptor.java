package ir.sk.item.interceptor;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Interceptor which Monitors and create the metrics for Upstream service response Time.
 *
 * Created by sad.kayvanfar on 1/1/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UpstreamServiceMetricInterceptor implements ClientHttpRequestInterceptor {

    private final MeterRegistry meterRegistry;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        // capture time before the service request.
        double timeBeforeServiceReqInMillis = System.currentTimeMillis();

        ClientHttpResponse response = execution.execute(request, body);

        // capture time after the service response.
        double timeAfterServiceResponseInMillis = System.currentTimeMillis();

        double timeInSeconds = (timeAfterServiceResponseInMillis - timeBeforeServiceReqInMillis) / 1000;

        if (request.getURI().getPath().equals("/books/v1/volumes")) {

            // Making metric for Book upstream service response time in seconds using guage.
            meterRegistry.gauge("book.api.in.seconds", timeInSeconds);

            log.debug("Book api response time in Seconds : {}", timeInSeconds);

        } else if (request.getURI().toString().contains("https://itunes.apple.com/search")) {

            // Making metric for Album upstream service response time in seconds using
            // guage.
            meterRegistry.gauge("album.api.time.in.seconds", timeInSeconds);

            log.debug("Album api response time in Seconds : {}", timeInSeconds);
        }

        return response;
    }
}
