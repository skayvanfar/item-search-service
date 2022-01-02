package ir.sk.item.model;

import ir.sk.item.dto.album.Album;
import ir.sk.item.dto.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String title;
    private List<String> authors;
    private ItemType itemType;

    public static Item valueOf(Book book) {
        return new Item(book.getVolumeInfo().getTitle(), book.getVolumeInfo().getAuthors(), ItemType.BOOK);
    }

    public static Item valueOf(Album album) {
        String trackName = Optional.ofNullable(album.getTrackName()).orElse("");
        String title =  Optional.ofNullable(album.getArtistName()).orElse("");
        return new Item(trackName, List.of(title), ItemType.ALBUM);
    }
}
