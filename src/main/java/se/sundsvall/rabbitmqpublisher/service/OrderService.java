package se.sundsvall.rabbitmqpublisher.service;

import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.rabbitmqpublisher.service.util.Mapper.toOrder;
import static se.sundsvall.rabbitmqpublisher.service.util.Mapper.toOrderEntity;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;

import se.sundsvall.rabbitmqpublisher.api.model.Order;
import se.sundsvall.rabbitmqpublisher.integration.db.OrderEntity;
import se.sundsvall.rabbitmqpublisher.integration.db.OrderRepository;
import se.sundsvall.rabbitmqpublisher.integration.rabbitmq.RabbitIntegration;

@Service
public class OrderService {

	private final RabbitIntegration rabbitIntegration;
	private final OrderRepository orderRepository;

	private static final String ORDER_NOT_FOUND = "Order not found";

	public OrderService(final RabbitIntegration rabbitIntegration,
		final OrderRepository orderRepository) {
		this.rabbitIntegration = rabbitIntegration;
		this.orderRepository = orderRepository;
	}

	public Order getOrder(final String id) {
		return toOrder(orderRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ORDER_NOT_FOUND)));
	}

	public Order createOrder(final Order order) {
		OrderEntity orderEntity = toOrderEntity(order);
		orderRepository.save(orderEntity);
		order.setId(orderEntity.getId());
		try {
			rabbitIntegration.publishEventToNewOrderQueue(order);
		} catch (Exception e) {
			throw Problem.valueOf(INTERNAL_SERVER_ERROR, "Error while publishing event to cancel order queue");
		}
		return toOrder(orderEntity);
	}

	public Order cancelOrder(final String id) {
		var orderEntity = orderRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ORDER_NOT_FOUND));
		try {
			rabbitIntegration.publishEventToCancelOrderQueue(toOrder(orderEntity));
		} catch (Exception e) {
			throw Problem.valueOf(INTERNAL_SERVER_ERROR, "Error while publishing event to cancel order queue");
		}
		return toOrder(orderEntity);
	}

	public Order updateOrder(final String id, final Order order) {
		var orderEntity = orderRepository.findById(id)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, ORDER_NOT_FOUND));
		Optional.ofNullable(order.getTest()).ifPresent(orderEntity::setTest);
		Optional.ofNullable(order.getTest2()).ifPresent(orderEntity::setTest2);
		Optional.ofNullable(order.getStatus()).ifPresent(orderEntity::setStatus);
		orderRepository.save(orderEntity);
		return toOrder(orderEntity);
	}
}