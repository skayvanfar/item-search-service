package ir.sk.item.model;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
public enum ItemType {
    BOOK(1),
    ALBUM(2);

    Integer typeId;

    ItemType(Integer typeId) {
        this.typeId = typeId;
    }
}
