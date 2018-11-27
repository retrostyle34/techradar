package eu.ubi.techradar.tools;

import eu.ubi.techradar.controller.ItemController;
import eu.ubi.techradar.entity.Item;
import eu.ubi.techradar.entity.Product;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ProductResourceAssembler implements ResourceAssembler<Product, Resource<Product>> {

    @Override
    public Resource<Product> toResource(Product product) {

        Resource<Product> itemResource = new Resource<>(product,
                linkTo(methodOn(ItemController.class).getItem(product.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).getAll()).withRel("products")
        );
        return itemResource;
    }
}