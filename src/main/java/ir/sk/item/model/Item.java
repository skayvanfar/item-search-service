package ir.sk.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private List<String> authors;

    private String title;

    private ItemType mediaType;
}
