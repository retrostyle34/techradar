package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Item;
import eu.ubi.techradar.exceptions.ItemNotFoundException;
import eu.ubi.techradar.repository.ItemRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@RestController
public class ItemController {

    @Autowired
    private ItemRepository repository;
    Logger logger = getLogger(ProductController.class);

    ItemController() {}


    @GetMapping(value = "/items")
    public List<Item> getAll(HttpServletResponse response) {
        List<Item> items = repository.findAll();
        response.setHeader("connection", "keep-alive");
        return items;
    }


    @GetMapping(value = "/items/{id}")
    public Item getItem(@PathVariable Long id, HttpServletResponse response) {
        Item item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException(" id: " + id));
        response.setHeader("connection", "keep-alive");
        return item;
    }


    @PostMapping("/items")
    @CrossOrigin(origins = "http://localhost:8080")
    public Item newItem(@RequestBody Item newItem, HttpServletResponse response) {
        Item item = repository.save(newItem);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("connection", "keep-alive");
        logger.info("Save item: " + item.getName() + " Id: "+ item.getId());
        return item;
    }


    @PutMapping("/items/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {

        Item updatedItem = repository.findById(id)
            .map(item -> {
                item.setName(newItem.getName());
                item.setDetails(newItem.getDetails());
                item.setLevel(newItem.getLevel());
                item.setType(newItem.getType());
                item.setChangeDate(newItem.getChangeDate());
                return repository.save(item);
            })
            .orElseGet(() -> {
                newItem.setId(id);
                return repository.save(newItem);
            });

        return updatedItem;
    }


    @DeleteMapping("/items/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Item deleteItem(@PathVariable Long id) throws URISyntaxException {
        Item item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("id: " + id));
        repository.deleteById(id);
        return item;
    }

}
