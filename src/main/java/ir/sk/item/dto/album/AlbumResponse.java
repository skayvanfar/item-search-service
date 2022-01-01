package ir.sk.item.dto.album;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * the Response object of Album upstream service.
 * <p>
 * Created by sad.kayvanfar on 12/31/2021
 */
@Data
public class AlbumResponse {
    private List<Album> results;

    public List<Album> getResults() {
        if (results != null)
            return results;
        else return Collections.EMPTY_LIST;
    }

    public void setResults(List<Album> results) {
        this.results = results;
    }
}
