package se.solrike.books.controller;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.annotations.Api;
import se.solrike.books.model.Car;
import se.solrike.books.model.CarRepository;


// curl http:/localhost:9443/app/api/1.0/cars

@Component
@Path("/api/1.0/cars")
@Api()
public class CarController {

  private CarRepository mCarRepository;
  private CarService mCarService;

  @Autowired
  public CarController(CarRepository carRepository, CarService carService) {
    mCarRepository = carRepository;
    mCarService = carService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public Collection<Car> getCars() {
    return mCarRepository.findAll();
  }



  @DELETE
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  @Path("{carId}")
  public void deleteCar(@PathParam("carId") Long carId) {
    Car car = mCarRepository.findById(carId).get();
    mCarService.delete(car);
  }
}
