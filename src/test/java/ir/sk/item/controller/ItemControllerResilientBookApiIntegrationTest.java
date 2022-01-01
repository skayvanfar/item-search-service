package ir.sk.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sk.item.ItemSearchApplication;
import ir.sk.item.model.Item;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

// this one to test what happens when google API is not available
@SpringBootTest(classes = ItemSearchApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.application.google.baseUrl=https://google-wrong.com"})
class ItemControllerResilientBookApiIntegrationTest {

    private String baseUrl = "http://localhost";

    @LocalServerPort
    private int port;

    @Value("${spring.application.result.limit:5}")
    public int resultLimit;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/items");
    }

    @Test
    void getItems() throws IOException {
        double timeBeforeServiceReqInMillis = System.currentTimeMillis();

        final ResponseEntity<String> response = restTemplate.exchange(baseUrl + "?term=reza",
                HttpMethod.GET, null, String.class);

        double timeAfterServiceResponseInMillis = System.currentTimeMillis();

        double responseTimeInSeconds = (timeAfterServiceResponseInMillis - timeBeforeServiceReqInMillis) / 1000;

        // check the Http status code.
        Assert.assertEquals("Response should have http status code : 200.", HttpStatus.OK, response.getStatusCode());

        // check the response time should be in 6 seconds.
        Assert.assertTrue("Service response time should be less than 6 seconds but the recorded response time is : "
                + responseTimeInSeconds, responseTimeInSeconds < 6);

        List<Item> responseList = mapper.readValue(response.getBody(), List.class);

        // Check the List in response should not be Null
        Assert.assertNotNull("Result should not be null.", responseList);

        // Check that the list in response that size should be less than the limit
        // configured.
        Assert.assertTrue("Results size should be less than or equal to the configured result limit.",
                responseList.size() <= resultLimit);
    }

}