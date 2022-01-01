package ir.sk.item.dto.book;

import lombok.Data;

import java.util.List;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
@Data
public class VolumeInfo {
    private String title;
    private List<String> authors;
}
