package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Item;
import eu.ubi.techradar.exceptions.ItemNotFoundException;
import eu.ubi.techradar.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class WebController {

    @Autowired
    private ItemRepository repository;

    @GetMapping("/items")
    public List<Item> retrieveAllItems() {
        return repository.findAll();
    }

    @PostMapping("/items")
    public Item saveItem(@RequestBody Item item) {
        return repository.save(item);
    }


    @GetMapping("/items/{id}")
    public Item retrieveItem(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("id: "+id));
    }

    @PostMapping("/items/{id}")
    public Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setDetails(newItem.getDetails());
                    item.setLevel(newItem.getLevel());
                    item.setType(newItem.getType());
                    item.setChangeDate(newItem.getChangeDate());
                    return repository.save(newItem);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
