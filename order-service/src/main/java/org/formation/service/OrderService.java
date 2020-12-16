package org.formation.service;

import org.formation.model.Order;
import org.formation.model.OrderRepository;
import org.formation.notification.Courriel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
	
	@Autowired
	private CircuitBreakerFactory cbFactory;


	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Order processOrder(Order order ) {
		String response = sendMail(order);
		System.out.println(response);
		return orderRepository.save(order);
	}
	
	private String sendMail(Order order) {
		Courriel courriel = new Courriel();
		courriel.setTo(order.getClient().getEmail());
		courriel.setSubject("Commande" + order.getDate());
		courriel.setText("Text");
//		String bodyResponse = restTemplate.postForObject("http://localhost:9090/sendSimple", request, Courriel.class);
//		ResponseEntity<String> response = restTemplate.postForEntity("http://notification-service:9090/sendSimple", courriel, String.class);
		
//		notification-service est le nom de
		return cbFactory.create("notification-service").run(() -> restTemplate.postForObject("http://notification-service:9090/sendSimple", courriel, String.class), throwable -> "fallback");
		
	}
	
}
