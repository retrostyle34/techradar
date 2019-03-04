package eu.ubi.techradar.controller;


import eu.ubi.techradar.entity.Car;
import eu.ubi.techradar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;


    @GetMapping("/cars")
    public List<Car> getAll(HttpServletResponse response) {

        List<Car> cars = carRepository.findAll();
        response.setHeader("access-control-allow-origin", "*");
        response.setHeader("access-control-allow-credentials", "true");
        response.setHeader("connection", "keep-alive");
        return cars;
    }

    @GetMapping("/cars/{id}")
    public Car one(@PathVariable Long id, HttpServletResponse response) {

        Car car = carRepository.findById(id).get();
        response.setHeader("X-Special-Header", "test");
        return car;
    }


}
