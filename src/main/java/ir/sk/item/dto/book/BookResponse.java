package ir.sk.item.dto.book;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * the Response object of Book upstream service.
 * <p>
 * Created by sad.kayvanfar on 12/31/2021
 */
@Data
public class BookResponse {
    private List<Book> items;

    public List<Book> getItems() {
        if (items != null)
            return items;
        else return Collections.EMPTY_LIST;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
