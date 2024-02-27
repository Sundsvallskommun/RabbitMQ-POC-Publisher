package se.sundsvall.rabbitmqpublisher.service.util;

import se.sundsvall.rabbitmqpublisher.api.model.Order;
import se.sundsvall.rabbitmqpublisher.integration.db.OrderEntity;

public final class Mapper {

	private Mapper() {
	}

	public static OrderEntity toOrderEntity(final Order order) {
		return OrderEntity.builder()
			.withTest(order.getTest())
			.withTest2(order.getTest2())
			.withStatus("PENDING")
			.build();
	}

	public static Order toOrder(final OrderEntity orderEntity) {
		return Order.builder()
			.withId(orderEntity.getId())
			.withTest(orderEntity.getTest())
			.withTest2(orderEntity.getTest2())
			.withStatus(orderEntity.getStatus())
			.build();
	}
}
