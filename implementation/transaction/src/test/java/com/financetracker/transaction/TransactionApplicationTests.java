package com.financetracker.transaction;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionApplicationTests {

	@LocalServerPort
	private int port;

	private static final String LOCAL_BASE_URL_WITHOUT_PORT = "http://localhost";

	@Test
	void x() {
		final var oneTimeTransactionDto = createOneTimeTransactionDto();

		given().port(port)
				.get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.OK.value())
				.and().body("size()", is(0));

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.OK.value())
				.and().body("size()", is(1))
				.and().body("[0].id", is(oneTimeTransactionDto.getId().toString()));
	}

	private OneTimeTransactionDto createOneTimeTransactionDto() {
		final var result = new OneTimeTransactionDto();
		result.setId(UUID.randomUUID());
		result.setType(OneTimeTransactionDto.TypeEnum.INCOME);

		final var transferDto = new TransferDto();
		transferDto.setSourceId("source");
		transferDto.setTargetBankAccountId(UUID.randomUUID());
		result.setTransfer(transferDto);

		final var monetaryAmountDto = new MonetaryAmountDto();
		monetaryAmountDto.setAmount(10.0);
		result.setAmount(monetaryAmountDto);

		result.setDate("2018-05-05");

		return result;
	}

}
