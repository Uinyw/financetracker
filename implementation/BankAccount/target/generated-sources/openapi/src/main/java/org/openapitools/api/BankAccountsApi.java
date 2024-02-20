/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.2.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.BankAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-12-13T10:13:06.151214400+01:00[Europe/Berlin]")
@Validated
@Tag(name = "bankAccounts", description = "the bankAccounts API")
public interface BankAccountsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /bankAccounts : Get all Bank Accounts
     *
     * @return Retrieve a list of all existing bank accounts. (status code 200)
     */
    @Operation(
        operationId = "bankAccountsGet",
        summary = "Get all Bank Accounts",
        tags = { "BankAccount" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list of all existing bank accounts.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccount.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/bankAccounts",
        produces = { "application/json" }
    )
    default ResponseEntity<List<BankAccount>> bankAccountsGet(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"dispoLimit\" : { \"amount\" : 0.8008281904610115 }, \"balance\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"transactions\" : [ { \"amount\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"type\" : \"incoming\", \"referenceID\" : \"referenceID\", \"labels\" : [ \"labels\", \"labels\" ] }, { \"amount\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"type\" : \"incoming\", \"referenceID\" : \"referenceID\", \"labels\" : [ \"labels\", \"labels\" ] } ], \"labels\" : [ \"labels\", \"labels\" ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /bankAccounts/{id} : Delete a Bank Account
     *
     * @param id ID of the bank account to delete (required)
     * @return Bank Account deleted successfully (status code 200)
     *         or Bank Account not found (status code 404)
     */
    @Operation(
        operationId = "bankAccountsIdDelete",
        summary = "Delete a Bank Account",
        tags = { "BankAccount" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Bank Account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Bank Account not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/bankAccounts/{id}"
    )
    default ResponseEntity<Void> bankAccountsIdDelete(
        @Parameter(name = "id", description = "ID of the bank account to delete", required = true) @PathVariable("id") String id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /bankAccounts/{id} : Get Bank Account by ID
     *
     * @param id Retrieve detailed information about a bank account using its unique identifier. (required)
     * @return Successful response (status code 200)
     *         or Bank account not found (status code 404)
     */
    @Operation(
        operationId = "bankAccountsIdGet",
        summary = "Get Bank Account by ID",
        tags = { "BankAccount" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccount.class))
            }),
            @ApiResponse(responseCode = "404", description = "Bank account not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/bankAccounts/{id}",
        produces = { "application/json" }
    )
    default ResponseEntity<BankAccount> bankAccountsIdGet(
        @Parameter(name = "id", description = "Retrieve detailed information about a bank account using its unique identifier.", required = true) @PathVariable("id") String id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"dispoLimit\" : { \"amount\" : 0.8008281904610115 }, \"balance\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"transactions\" : [ { \"amount\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"type\" : \"incoming\", \"referenceID\" : \"referenceID\", \"labels\" : [ \"labels\", \"labels\" ] }, { \"amount\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"type\" : \"incoming\", \"referenceID\" : \"referenceID\", \"labels\" : [ \"labels\", \"labels\" ] } ], \"labels\" : [ \"labels\", \"labels\" ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /bankAccounts/{id} : Update Bank Account by ID
     *
     * @param id Retrieve detailed information about a bank account using its unique identifier. (required)
     * @param bankAccount  (required)
     * @return Bank Account updated successfully (status code 200)
     *         or Bank account not found (status code 404)
     */
    @Operation(
        operationId = "bankAccountsIdPatch",
        summary = "Update Bank Account by ID",
        tags = { "BankAccount" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Bank Account updated successfully"),
            @ApiResponse(responseCode = "404", description = "Bank account not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/bankAccounts/{id}",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> bankAccountsIdPatch(
        @Parameter(name = "id", description = "Retrieve detailed information about a bank account using its unique identifier.", required = true) @PathVariable("id") String id,
        @Parameter(name = "BankAccount", description = "", required = true) @Valid @RequestBody BankAccount bankAccount
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /bankAccounts : Create a new Bank Account
     *
     * @param bankAccount  (required)
     * @return Bank Account created. (status code 200)
     */
    @Operation(
        operationId = "bankAccountsPost",
        summary = "Create a new Bank Account",
        tags = { "BankAccount" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Bank Account created.")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/bankAccounts",
        consumes = { "application/json" }
    )
    default ResponseEntity<Void> bankAccountsPost(
        @Parameter(name = "BankAccount", description = "", required = true) @Valid @RequestBody BankAccount bankAccount
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}