package eu.ubi.techradar.tools;

import eu.ubi.techradar.controller.ItemController;
import eu.ubi.techradar.entity.Item;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ItemResourceAssembler implements ResourceAssembler<Item, Resource<Item>> {

    @Override
    public Resource<Item> toResource(Item item) {

        Resource<Item> itemResource = new Resource<>(item,
                linkTo(methodOn(ItemController.class).getItem(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).getAll()).withRel("items")
        );
        return itemResource;
    }
}