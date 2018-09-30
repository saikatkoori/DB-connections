package com.javasampleapproach.newfeaturerequestmapping.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.newfeaturerequestmapping.model.Customer;

@RestController
public class WebController {

	Map<Integer, Customer> custStores = new HashMap<Integer, Customer>();

	@PostConstruct
	public void initIt() throws Exception {
		Customer cust1 = new Customer(1, "Jack", "Smith", 20);
		Customer cust2 = new Customer(2, "Peter", "Johnson", 25);

		custStores.put(cust1.getCustId(), cust1);
		custStores.put(cust2.getCustId(), cust2);
	}

	@GetMapping("/get")
	public Customer getMethod(@RequestParam("id") int custId) {
		return custStores.get(custId);
	}

	@PostMapping("/post")
	public Customer postMethod(@RequestBody Customer customer) {
		Random r = new Random();
		customer.setCustId(r.nextInt());

		// POST processing
		custStores.put(customer.getCustId(), customer);

		// Log out custStores after POST
		System.out.println("Customer Stores after POST:");
		custStores.forEach((id, cust) -> System.out.println(cust.toString()));

		return customer;
	}

	@PutMapping("/put/{custId}")
	public Customer putMethod(@PathVariable int custId, @RequestBody Customer customer) {
		// PUT processing
		try{
			custStores.remove(custId);
			customer.setCustId(custId);
			custStores.put(custId, customer);
		}catch(Exception e){
			System.out.println(e.getStackTrace());
			return null;
		}

		// Log out custStores after PUT
		System.out.println("Customer Stores after PUT");
		custStores.forEach((id, cust) -> System.out.println(cust.toString()));

		return customer;
	}

	@DeleteMapping("/delete/{custId}")
	public String deleteMethod(@PathVariable int custId) {
		try {
			// DELETE processing
			custStores.remove(custId);
		} catch (Exception e) {
			return "Error";
		}

		// Log out custStores after DELETE
		System.out.println("Customer Stores after DELETE");
		custStores.forEach((id, cust) -> System.out.println(cust.toString()));

		return "Done";
	}

}
