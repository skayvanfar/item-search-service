package ir.sk.item.client.album;

import ir.sk.item.dto.album.Album;
import org.springframework.boot.actuate.health.Health;

import java.util.List;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
public interface AlbumClient {
    List<Album> getMusics(final String searchTerm);
    Health isRunning();
}
