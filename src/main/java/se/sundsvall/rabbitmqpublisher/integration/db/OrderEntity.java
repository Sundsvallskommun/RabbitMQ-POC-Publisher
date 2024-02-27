package se.sundsvall.rabbitmqpublisher.integration.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String test;

	private String test2;

	private String status;

}
