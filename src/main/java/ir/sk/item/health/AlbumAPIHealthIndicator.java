package ir.sk.item.health;

import ir.sk.item.client.album.AlbumClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by sad.kayvanfar on 1/1/2022
 */
@Component
@RequiredArgsConstructor
public class AlbumAPIHealthIndicator implements HealthIndicator {

    private final AlbumClient albumClient;

    @Override
    public Health health() {
        return albumClient.isRunning();
    }
}
