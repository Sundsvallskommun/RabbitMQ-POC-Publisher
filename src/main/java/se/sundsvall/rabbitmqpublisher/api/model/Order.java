package se.sundsvall.rabbitmqpublisher.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class Order {

	@Schema(description = "The order id", example = "1234", accessMode = Schema.AccessMode.READ_ONLY)
	private String id;

	private String test;

	private String test2;

	@Schema(description = "The order status", example = "PENDING", accessMode = Schema.AccessMode.READ_ONLY)
	private String status;
}
