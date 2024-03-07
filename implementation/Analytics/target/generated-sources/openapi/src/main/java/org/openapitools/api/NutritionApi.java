/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import org.openapitools.model.DurationDto;
import org.openapitools.model.NutritionDto;
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

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-05T07:47:16.544140400+01:00[Europe/Berlin]")
@Validated
@Tag(name = "Analytics", description = "the Analytics API")
public interface NutritionApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /nutrition : Get the Nutrition in a given timeframe
     *
     * @param durationDto  (required)
     * @return Successful response (status code 200)
     *         or Nutrition values for this timeframe could not be found (status code 404)
     */
    @Operation(
        operationId = "nutritionGet",
        summary = "Get the Nutrition in a given timeframe",
        tags = { "Analytics" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = NutritionDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Nutrition values for this timeframe could not be found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/nutrition",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<NutritionDto> nutritionGet(
        @Parameter(name = "DurationDto", description = "", required = true) @Valid @RequestBody DurationDto durationDto
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"carbohydrates\" : 1.4658129805029452, \"protein\" : 5.962133916683182, \"fat\" : 5.637376656633329, \"calories\" : 6.027456183070403, \"servingSize\" : 0.8008281904610115, \"sugar\" : 2.3021358869347655 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
