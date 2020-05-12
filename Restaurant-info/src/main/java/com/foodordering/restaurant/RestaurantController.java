package com.foodordering.restaurant;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {
	
	@Autowired
	RestaurantService service;
	
	//notes="Click here to get all restaurants from DB")
	@GetMapping("/restaurants")
	Iterable<Restaurant> getAllRestaurants() {
		return service.getAllRestaurants();
	}
	
//	notes="Click here to add restaurant to DB")
	@PostMapping("/add/restaurant")
	String addRestaurant(@RequestBody Restaurant restaurant) {
		service.addRestaurant(restaurant);
		return restaurant.getRestaurantName()+" was added successfully.";
	}
	
//		  notes="Click here to get that restaurant details")
	@GetMapping("/get/restaurantById/{restaurantId}")
	Optional<Restaurant> getRestaurantById(@PathVariable int restaurantId) {
		return service.getRestaurantById(restaurantId);
	}
	
//			  notes="Click here to update restaurant details")
	@PostMapping("/update/restaurant")
	String updateRestaurant(@RequestBody Restaurant restaurant) {
		service.updateRestaurant(restaurant);
		return restaurant.getRestaurantName()+" was updated.";
	}
	
//			  notes="Click here to remove restaurant details")
	@GetMapping("/remove/restaurant/{restaurantId}") 
	String removeRestaurant(@PathVariable int restaurantId) {
		service.removeRestaurant(restaurantId);
		return "Restaurant with ID " + restaurantId + " was removed.";
	}
	
//			  notes="Click here to get that restaurant details")
	@GetMapping("/search/ownerByUserName/{ownerUserName}")
	Iterable<Restaurant> searchByOwnerUserName(@PathVariable String ownerUserName){
		return service.searchByOwnerUserName(ownerUserName);
	}
	
//	
//	@ApiOperation(value="Retrieve Owner Password",
//			  notes="Click here to get owner's password")
//	@GetMapping("/fetch/ownerPassword/{restaurantId}")
//	String getOwnerPassword(@PathVariable int restaurantId) {
//		return service.getOwnerPassword(restaurantId);
//	}
	
//			 notes="Click here to view restaurants by locality")
	@GetMapping("/fetch/restaurant/locality/{locality}")
	Iterable<Restaurant> getRestaurantByLocation(@PathVariable String locality) {
		return service.searchByLocality(locality);
	}
}
