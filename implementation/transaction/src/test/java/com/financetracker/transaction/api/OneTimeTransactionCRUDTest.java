package com.financetracker.transaction.api;

import com.financetracker.transaction.IntegrationTestBase;
import com.financetracker.transaction.data.TestOneTimeTransactionBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.BankAccountDto;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class OneTimeTransactionCRUDTest extends IntegrationTestBase {

	@BeforeEach
	void setUp() {
		when(bankAccountProvider.getBankAccount(anyString())).thenReturn(Optional.of(BankAccountDto.builder().build()));
	}

	@Test
	void givenOneTimeTransactionDto_whenCreateOneTimeTransaction_thenOneTimeTransactionExists() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();

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

	@Test
	void givenInvalidOneTimeTransactionDto_whenCreateOneTimeTransaction_thenBadRequest() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();
		oneTimeTransactionDto.setDate(null);

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void givenExistingOneTimeTransaction_whenGetTransactionById_thenTransactionIsReturned() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
				.then()
				.statusCode(HttpStatus.OK.value())
				.and().body("id", is(oneTimeTransactionDto.getId().toString()))
				.and().body("type", is(oneTimeTransactionDto.getType().toString()));
	}

	@Test
	void givenExistingOneTimeTransaction_whenGetTransactionByInvalidId_thenNotFound() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();
		final var invalidId = "foo";

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.contentType(ContentType.JSON)
				.get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + invalidId)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void givenExistingOneTimeTransaction_whenPatchTransaction_thenTransactionIsUpdated() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();
		oneTimeTransactionDto.setName("Name");

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
				.then()
				.statusCode(HttpStatus.OK.value())
				.and().body("name", is("Name"));

		oneTimeTransactionDto.setName("New Name");

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
				.then()
				.statusCode(HttpStatus.OK.value())
				.and().body("name", is("New Name"));
	}

	@Test
	void givenExistingOneTimeTransaction_whenPatchTransactionByInvalidId_thenNotFound() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();
		final var invalidId = "foo";

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.patch(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + invalidId)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void givenExistingOneTimeTransaction_whenDeleteTransaction_thenTransactionDoesNotExistAnymore() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.contentType(ContentType.JSON)
				.delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + oneTimeTransactionDto.getId().toString())
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.get(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.OK.value())
				.and().body("size()", is(0));
	}

	@Test
	void givenExistingOneTimeTransaction_whenDeleteTransactionByInvalidId_thenNotFound() {
		final var oneTimeTransactionDto = TestOneTimeTransactionBuilder.buildWithDefaults();
		final var invalidId = "foo";

		given().port(port)
				.contentType(ContentType.JSON)
				.body(oneTimeTransactionDto)
				.post(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime")
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());

		given().port(port)
				.contentType(ContentType.JSON)
				.delete(LOCAL_BASE_URL_WITHOUT_PORT + "/transactions/onetime/" + invalidId)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

}
