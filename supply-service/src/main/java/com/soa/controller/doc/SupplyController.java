package com.soa.controller.doc;

import com.soa.dto.SupplyDto;
import com.soa.dto.SupplyDtoPost;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author Stanislav Hlova
 */
@Tag(name = "Supply Controller", description = "Manage supplies and their associated details.")
public interface SupplyController {

    @Operation(
            summary = "Get all supplies",
            description = "Fetch a list of all supplies available in the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of supplies successfully retrieved.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\n  \"id\": \"67580485fa50dc56754d1ccd\",\n  \"supplyStatus\": \"SHIPPED_TO_WAREHOUSE\",\n  \"deliveryName\": \"Nova Poshta\",\n  \"deliveryInvoiceNumber\": \"1234567890\",\n  \"deliveryTotal\": 150.50,\n  \"receivedAt\": \"2023-12-08T10:00:00\",\n  \"shoePairInfoSupplyList\": []\n}]")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Internal server error occurred.\"\n}")
                            )
                    )
            }
    )
    List<SupplyDto> listAllSupplies();

    @Operation(
            summary = "Get a supply by ID",
            description = "Retrieve the details of a specific supply by its ID.",
            parameters = @Parameter(name = "id", description = "The unique identifier of the supply", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Supply details successfully retrieved.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"id\": \"67580485fa50dc56754d1ccd\",\n  \"supplyStatus\": \"SHIPPED_TO_WAREHOUSE\",\n  \"deliveryName\": \"Nova Poshta\",\n  \"deliveryInvoiceNumber\": \"1234567890\",\n  \"deliveryTotal\": 150.50,\n  \"receivedAt\": \"2023-12-08T10:00:00\",\n  \"shoePairInfoSupplyList\": []\n}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Supply not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Supply with ID not found.\"\n}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Internal server error occurred.\"\n}")
                            )
                    )
            }
    )
    SupplyDto getSupplyById(String id);

    @Operation(
            summary = "Create a new supply",
            description = "Add a new supply to the system.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the supply to be created.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SupplyDtoPost.class),
                            examples = @ExampleObject(value = "{\n  \"deliveryName\": \"Nova Poshta\",\n  \"deliveryInvoiceNumber\": \"1234567890\",\n  \"deliveryTotal\": 150.50,\n  \"receivedAt\": \"2023-12-08T10:00:00\",\n  \"shoePairInfoSupplyList\": []\n}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Supply successfully created."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input provided.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Invalid deliveryTotal value.\"\n}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Internal server error occurred.\"\n}")
                            )
                    )
            }
    )
    SupplyDto createSupply(SupplyDtoPost supplyDtoPost);

    @Operation(
            summary = "Update an existing supply",
            description = "Modify the details of an existing supply.",
            parameters = @Parameter(name = "id", description = "The unique identifier of the supply to be updated", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated details of the supply.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SupplyDtoPost.class),
                            examples = @ExampleObject(value = "{\n  \"deliveryName\": \"Nova Poshta\",\n  \"deliveryInvoiceNumber\": \"987654321\",\n  \"deliveryTotal\": 200.00,\n  \"receivedAt\": \"2023-12-10T12:00:00\",\n  \"shoePairInfoSupplyList\": []\n}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Supply successfully updated."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Supply not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Supply with ID not found.\"\n}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Internal server error occurred.\"\n}")
                            )
                    )
            }
    )
    SupplyDto updateSupply(String id, SupplyDtoPost supplyDtoPost);

    @Operation(
            summary = "Get self-price by shoe pair info code",
            description = "Calculate the self-price of a supply based on the shoe pair information code.",
            parameters = @Parameter(name = "shoe-pair-info-code", description = "The unique code for the shoe pair information", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Self-price successfully calculated.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"selfPrice\": 100.50\n}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Shoe pair information not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Shoe pair info code not found.\"\n}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error occurred.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\n  \"error\": \"Internal server error occurred.\"\n}")
                            )
                    )
            }
    )
    Map<String, Object> getSelfPrice(String shoePairInfoCode);
}
