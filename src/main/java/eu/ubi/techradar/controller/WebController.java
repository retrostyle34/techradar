package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Item;
import eu.ubi.techradar.exceptions.ItemNotFoundException;
import eu.ubi.techradar.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class WebController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public List<Item> retrieveAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/item/{id}")
    public Item retrieveItem(@PathVariable long id) {
        Optional<Item> item = itemRepository.findById(id);

        if (!item.isPresent())
            throw new ItemNotFoundException("id: " + id);

        return item.get();
    }

}
