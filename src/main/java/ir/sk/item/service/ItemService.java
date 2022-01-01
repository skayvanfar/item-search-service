package ir.sk.item.service;

import ir.sk.item.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
@Service
public interface ItemService {
    List<Item> search(String term);
}
