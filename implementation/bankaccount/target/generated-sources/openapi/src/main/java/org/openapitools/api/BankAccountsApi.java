/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.BankAccountDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-23T07:54:23.223970+01:00[Europe/Berlin]")
@Validated
@Tag(name = "Bank Account", description = "the Bank Account API")
public interface BankAccountsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /bankAccounts : Get all existing bank accounts
     *
     * @return Retrieve a list of all existing bank accounts. (status code 200)
     */
    @Operation(
        operationId = "bankAccountsGet",
        summary = "Get all existing bank accounts",
        tags = { "Bank Account" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list of all existing bank accounts.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BankAccountDto.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/bankAccounts",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<BankAccountDto>> bankAccountsGet(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"dispositionLimit\" : { \"amount\" : 0.8008281904610115 }, \"balance\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"labels\" : [ \"labels\", \"labels\" ] }, { \"dispositionLimit\" : { \"amount\" : 0.8008281904610115 }, \"balance\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"labels\" : [ \"labels\", \"labels\" ] } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /bankAccounts/{id} : Delete a bank account by ID
     *
     * @param id Bank Account ID (required)
     * @return Successfully deleted bank account. (status code 200)
     *         or Bank Account not found (status code 404)
     */
    @Operation(
        operationId = "bankAccountsIdDelete",
        summary = "Delete a bank account by ID",
        tags = { "Bank Account" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted bank account."),
            @ApiResponse(responseCode = "404", description = "Bank Account not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/bankAccounts/{id}"
    )
    
    default ResponseEntity<Void> bankAccountsIdDelete(
        @Parameter(name = "id", description = "Bank Account ID", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /bankAccounts/{id} : Get bank account by ID
     *
     * @param id Bank Account ID (required)
     * @return Successfully retrieved bank account. (status code 200)
     *         or Bank account not found (status code 404)
     */
    @Operation(
        operationId = "bankAccountsIdGet",
        summary = "Get bank account by ID",
        tags = { "Bank Account" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank account.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Bank account not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/bankAccounts/{id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<BankAccountDto> bankAccountsIdGet(
        @Parameter(name = "id", description = "Bank Account ID", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"dispositionLimit\" : { \"amount\" : 0.8008281904610115 }, \"balance\" : { \"amount\" : 0.8008281904610115 }, \"name\" : \"name\", \"description\" : \"description\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"labels\" : [ \"labels\", \"labels\" ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PATCH /bankAccounts/{id} : Update bank account by ID
     *
     * @param id Bank Account ID (required)
     * @param bankAccountDto  (required)
     * @return Successfully updated bank account. (status code 200)
     *         or Bank account not found (status code 404)
     */
    @Operation(
        operationId = "bankAccountsIdPatch",
        summary = "Update bank account by ID",
        tags = { "Bank Account" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated bank account."),
            @ApiResponse(responseCode = "404", description = "Bank account not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/bankAccounts/{id}",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> bankAccountsIdPatch(
        @Parameter(name = "id", description = "Bank Account ID", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
        @Parameter(name = "BankAccountDto", description = "", required = true) @Valid @RequestBody BankAccountDto bankAccountDto
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /bankAccounts : Create a new bank account
     *
     * @param bankAccountDto  (required)
     * @return Successfully created bank account. (status code 200)
     */
    @Operation(
        operationId = "bankAccountsPost",
        summary = "Create a new bank account",
        tags = { "Bank Account" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully created bank account.")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/bankAccounts",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> bankAccountsPost(
        @Parameter(name = "BankAccountDto", description = "", required = true) @Valid @RequestBody BankAccountDto bankAccountDto
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
