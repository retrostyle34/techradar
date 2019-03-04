package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Level;
import eu.ubi.techradar.exceptions.LevelNotFoundException;
import eu.ubi.techradar.repository.LevelRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


@RestController
public class LevelController {

    @Autowired
    private LevelRepository repository;
    Logger logger = getLogger(ProductController.class);

    LevelController() {}


    @GetMapping(value = "/levels")
    public List<Level> getAll(HttpServletResponse response) {
        List<Level> levels = repository.findAll();
        response.setHeader("connection", "keep-alive");
        return levels;
    }


    @GetMapping(value = "/level/{id}")
    public Level getLevel(@PathVariable Long id, HttpServletResponse response) {
        Level level = repository.findById(id).orElseThrow(() -> new LevelNotFoundException(" id: " + id));
        response.setHeader("connection", "keep-alive");
        return level;
    }


    @PostMapping("/levels")
    @CrossOrigin(origins = "http://localhost:8080")
    public Level newLevel(@RequestBody Level newLevel, HttpServletResponse response) {
        Level level = repository.save(newLevel);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("connection", "keep-alive");
        logger.info("Save level: " + level.getName() + " Id: "+ level.getId() + " Order number: " + level.getOrderNumber());
        return level;
    }


    @PutMapping("/level/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Level replaceLevel(@RequestBody Level newLevel, @PathVariable Long id) {

        Level updatedLevel = repository.findById(id)
            .map(level -> {
                level.setName(newLevel.getName());
                level.setDescription(newLevel.getDescription());
                level.setOrderNumber(newLevel.getOrderNumber());
                return repository.save(level);
            })
            .orElseGet(() -> {
                newLevel.setId(id);
                return repository.save(newLevel);
            });
        return updatedLevel;
    }


    @DeleteMapping("/levels/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public Level deleteLevel(@PathVariable Long id) {
        Level level = repository.findById(id).orElseThrow(() -> new LevelNotFoundException("id: " + id));
        repository.deleteById(id);
        return level;
    }

}
