package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Hero;
import eu.ubi.techradar.repository.HeroRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class HeroController {

    Logger logger = getLogger(HeroController.class);

    @Autowired
    private HeroRepository heroRepository;

    @GetMapping("/heroes")
    public List<Hero> getAll(HttpServletResponse response) {

        List<Hero> heroes = heroRepository.findAll();
        response.setHeader("access-control-allow-origin", "*");
        response.setHeader("access-control-allow-credentials", "true");
        response.setHeader("connection", "keep-alive");
        return heroes;
    }

    @GetMapping("/heroes/{id}")
    public Hero one(@PathVariable Long id, HttpServletResponse response) {

        Hero hero = heroRepository.findById(id).get();
        response.setHeader("access-control-allow-origin", "*");
        response.setHeader("access-control-allow-credentials", "true");
        response.setHeader("connection", "keep-alive");
        return hero;
    }

    @PostMapping("/heroes")
    public Hero newHero(@RequestBody Hero newHero, HttpServletResponse response) {

        logger.info("POST");
        Hero hero = heroRepository.save(newHero);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("access-control-allow-origin", "*");
        response.setHeader("access-control-allow-methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("connection", "keep-alive");
        logger.info("Save hero: " + hero.getName() + " Id: "+ hero.getId());
        return hero;
    }

}
