package ir.sk.item.interceptor;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    @Value("${spring.application.google.metric}")
    private String metricGoogleAPI;

    @Value("${spring.application.iTunes.metric}")
    private String metricITuneAPI;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        // capture time before the service request.
        long timeBeforeServiceReqInMillis = System.nanoTime();

        ClientHttpResponse response = execution.execute(request, body);

        // capture time after the service response.
        long timeAfterServiceResponseInMillis = System.nanoTime();

        long timeInSeconds = timeAfterServiceResponseInMillis - timeBeforeServiceReqInMillis;

        if (request.getURI().getPath().equals("/books/v1/volumes")) {

            // Making metric for Book upstream service response time in seconds.
            meterRegistry.timer(metricGoogleAPI).record(timeInSeconds, TimeUnit.MILLISECONDS);

            log.debug("Book api response time in milliseconds : {}", timeInSeconds);

        } else if (request.getURI().toString().contains("https://itunes.apple.com/search")) {

            // Making metric for Album upstream service response time in seconds
            meterRegistry.timer(metricITuneAPI).record(timeInSeconds, TimeUnit.MILLISECONDS);

            log.debug("Album api response time in milliseconds : {}", timeInSeconds);
        }

        return response;
    }
}
