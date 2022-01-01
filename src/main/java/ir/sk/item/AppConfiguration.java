package ir.sk.item;

import ir.sk.item.interceptor.UpstreamServiceMetricInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by sad.kayvanfar on 1/1/2022
 */
@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

    private final UpstreamServiceMetricInterceptor interceptor;
    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(factory);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
                List.of(MediaType.valueOf("text/javascript;charset=utf-8"),
                        MediaType.parseMediaType("application/json;charset=UTF-8")));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        return restTemplate;
    }

}
