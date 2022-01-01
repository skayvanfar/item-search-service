package ir.sk.item.controller;

import ir.sk.item.model.Item;
import ir.sk.item.model.ItemType;
import ir.sk.item.service.ItemService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sad.kayvanfar on 1/1/2022
 */
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    private String baseUrl = "/api/items";
    private List<Item> items;

    @BeforeEach
    public void setUp() {
        items = Arrays.asList(
                new Item("book1", List.of("hossein", "Vahid"), ItemType.BOOK)
                , new Item("book2", List.of("Hafezi"), ItemType.BOOK)
                , new Item("album1", List.of("Noz", "Vasdi"), ItemType.ALBUM));
    }

    @Test
    public void getItemsBySize() throws Exception {
        when(itemService.search("reza")).thenReturn(items);

        RequestBuilder request = MockMvcRequestBuilders
                .get(baseUrl + "?term=reza")
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andReturn();
    }

    @Test
    public void getItemsByContent() throws Exception {
        when(itemService.search("reza")).thenReturn(items);
        RequestBuilder request = MockMvcRequestBuilders
                .get(baseUrl + "?term=reza")
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("book1")))
                .andReturn();
    }

}
