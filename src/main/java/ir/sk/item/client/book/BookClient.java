package ir.sk.item.client.book;

import ir.sk.item.dto.book.Book;
import org.springframework.boot.actuate.health.Health;

import java.util.List;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
public interface BookClient {
    List<Book> getBooks(final String searchTerm);
    Health isRunning();
}
