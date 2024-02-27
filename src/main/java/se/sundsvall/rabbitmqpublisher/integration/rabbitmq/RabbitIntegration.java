package se.sundsvall.rabbitmqpublisher.integration.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import se.sundsvall.rabbitmqpublisher.api.model.Order;

@Component
public class RabbitIntegration {

	private static final Logger LOG = LoggerFactory.getLogger(RabbitIntegration.class);
	public static final String POC_EXCHANGE = "poc-exchange";
	public static final String NEW_ORDER_ROUTING_KEY = "RK_newOrderQueue";
	public static final String CANCEL_ORDER_ROUTING_KEY = "RK_cancelOrderQueue";

	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;

	public RabbitIntegration(final RabbitTemplate rabbitTemplate, final ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}

	public void publishEventToNewOrderQueue(final Order order) {
		try {
			rabbitTemplate.convertAndSend(POC_EXCHANGE, NEW_ORDER_ROUTING_KEY, objectMapper.writeValueAsBytes(order));
		} catch (AmqpException ex) {
			LOG.error("Error while sending message to RabbitMQ", ex);
		} catch (JsonProcessingException ex) {
			LOG.error("Error while converting order to bytes", ex);
		}
	}

	public void publishEventToCancelOrderQueue(final Order order) {
		try {
			rabbitTemplate.convertAndSend(POC_EXCHANGE, CANCEL_ORDER_ROUTING_KEY, objectMapper.writeValueAsBytes(order));
		} catch (AmqpException ex) {
			LOG.error("Error while sending message to RabbitMQ", ex);
		} catch (JsonProcessingException ex) {
			LOG.error("Error while converting order to bytes", ex);
		}
	}
}
