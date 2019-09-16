package se.solrike.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.solrike.books.model.Car;
import se.solrike.books.model.CarRepository;

@Component
public class CarService {
  
  
  
  private CarRepository mCarRepository;

  @Autowired
  public CarService(CarRepository carRepository) {
    mCarRepository = carRepository;
  }
 
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void delete(Car car) {
    mCarRepository.delete(car);
  }

}
