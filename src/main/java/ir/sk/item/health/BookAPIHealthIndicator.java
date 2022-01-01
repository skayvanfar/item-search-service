package ir.sk.item.health;

import ir.sk.item.client.book.BookClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by sad.kayvanfar on 1/1/2022
 */
@Component
@RequiredArgsConstructor
public class BookAPIHealthIndicator implements HealthIndicator {

    private final BookClient bookClient;

    @Override
    public Health health() {
        return bookClient.isRunning();
    }
}
