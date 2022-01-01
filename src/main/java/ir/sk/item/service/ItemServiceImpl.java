package ir.sk.item.service;

import ir.sk.item.client.album.AlbumClient;
import ir.sk.item.client.book.BookClient;
import ir.sk.item.dto.album.Album;
import ir.sk.item.dto.book.Book;
import ir.sk.item.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final BookClient bookClient;
    private final AlbumClient albumClient;

    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public List<Item> search(String term) {
        log.info("Starting finding items with term: {} " + term);

        CircuitBreaker BookAPICircuitBreaker = circuitBreakerFactory.create("bookAPIService");
        List<Book> bookList = BookAPICircuitBreaker.run(() -> bookClient.getBooks(term), throwable -> Collections.emptyList());

        CircuitBreaker AlbumAPICircuitBreakerB = circuitBreakerFactory.create("albumAPIService");
        List<Album> albumList = AlbumAPICircuitBreakerB.run(() -> albumClient.getMusics(term), throwable -> Collections.emptyList());

        log.info("Finish finding items with term: {}", term);

        return Stream.concat(bookList.stream().map(Item::valueOf), albumList.stream().map(Item::valueOf))
                .sorted(Comparator.comparing(Item::getTitle))
                .collect(Collectors.toList());
    }

}
