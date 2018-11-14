package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Item;
import eu.ubi.techradar.exceptions.ItemNotFoundException;
import eu.ubi.techradar.repository.ItemRepository;
import eu.ubi.techradar.tools.ItemResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
public class ItemController {

    private final ItemRepository repository;
    private final ItemResourceAssembler assembler;

    ItemController(ItemRepository repository, ItemResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/items")
    ResponseEntity<?> newEmployee(@RequestBody Item newItem) throws URISyntaxException {
        Resource<Item> resource = assembler.toResource(repository.save(newItem));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


    @RequestMapping(value = "/items", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resources<Resource<Item>> getAll() {
        List<Resource<Item>> items = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(items,
                linkTo(methodOn(ItemController.class).getAll()).withSelfRel());
    }


    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<Item> getItem(@PathVariable Long id) {
        Item item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException(" id: " + id));
        return assembler.toResource(item);
    }


    @RequestMapping(value = "/items/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> replaceItem(@RequestBody Item newItem, @PathVariable Long id) throws URISyntaxException {

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

        Resource<Item> resource = assembler.toResource(updatedItem);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id) throws URISyntaxException {

        Item item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("id: " + id));
        repository.deleteById(id);
        Resource<Item> resource = assembler.toResource(item);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

}
