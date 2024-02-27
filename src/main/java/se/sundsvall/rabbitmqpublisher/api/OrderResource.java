package se.sundsvall.rabbitmqpublisher.api;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.sundsvall.rabbitmqpublisher.api.model.Order;
import se.sundsvall.rabbitmqpublisher.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderResource {

	private final OrderService orderService;

	public OrderResource(final OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/{id}")
	ResponseEntity<Order> getOrder(@PathVariable final String id) {
		var result = orderService.getOrder(id);
		return ok(result);
	}

	@PostMapping
	ResponseEntity<Order> createOrder(@RequestBody final Order order) {
		var result = orderService.createOrder(order);
		return ok(result);
	}

	@PatchMapping("/cancel/{id}")
	ResponseEntity<Order> cancelOrder(@PathVariable final String id) {
		var result = orderService.cancelOrder(id);
		return ok(result);
	}

	@PatchMapping("/update/{id}")
	ResponseEntity<Order> updateOrder(@PathVariable final String id, @RequestBody final Order order) {
		var result = orderService.updateOrder(id, order);
		return ok(result);
	}

}
