package ir.sk.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.sk.item.model.Item;
import ir.sk.item.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sad.kayvanfar on 12/31/2021
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Get list of Items by text search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Item",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Item.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid info supplied for calling",
                    content = @Content)})
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems(@RequestParam("term") String term) {
        return ResponseEntity.ok(itemService.search(term));
    }
}
