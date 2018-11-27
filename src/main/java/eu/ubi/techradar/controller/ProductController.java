package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Product;
import eu.ubi.techradar.exceptions.ItemNotFoundException;
import eu.ubi.techradar.repository.ProductRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.slf4j.LoggerFactory.*;

@RestController
public class ProductController {

    Logger logger = getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;




    @GetMapping("/products")
    public List<Product> getAll(HttpServletResponse response) {

        List<Product> products = productRepository.findAll();
        response.setHeader("access-control-allow-origin", "*");
        response.setHeader("access-control-allow-credentials", "true");
        response.setHeader("connection", "keep-alive");

        //"Origin, X-Requested-With, Content-Type, Accept, Authorization"

        return products;
    }

    @GetMapping("/products/{id}")
    public Product one(@PathVariable Long id, HttpServletResponse response) {

        Product product = productRepository.findById(id).get();
        response.setHeader("access-control-allow-origin", "*");
        response.setHeader("access-control-allow-credentials", "true");
        response.setHeader("connection", "keep-alive");

        return product;
    }

    @PostMapping("/products")
    @CrossOrigin(origins = "http://localhost:8080")
    public Product newProduct(@RequestBody Product newProduct, HttpServletResponse response) {

        logger.info("POST");
        Product product = productRepository.save(newProduct);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("access-control-allow-origin", "*");
        response.setHeader("access-control-allow-methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("connection", "keep-alive");

        logger.info("Save product: " + product.getName() + " Id: "+ product.getId());

        return product;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:8080")
    public Product replaceItem(@RequestBody Product newProduct, @PathVariable Long id) {

        Product updatedProduct = productRepository.findById(id)
                .map(item -> {
                    item.setName(newProduct.getName());
                    item.setDescription(newProduct.getDescription());
                    item.setPrice(newProduct.getPrice());
                    return productRepository.save(item);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });

        return updatedProduct;
    }


    @DeleteMapping("/products/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Product deleteItem(@PathVariable Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("id: " + id));
        productRepository.deleteById(id);
        return product;
    }


}
