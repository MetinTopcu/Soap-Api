package com.apinizer.example.openapi;

import com.apinizer.example.service.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/openapi")
@Tag(name = "contact", description = "the Contact API")
public class ContactController {

    @Operation(summary = "Find Contacts by name", description = "Name search by %name% format", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))})
    @GetMapping(value = "/contacts", produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Contact>> findAll(
            @Parameter(description = "Page number, default is 1") @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Parameter(description = "Name of the contact for search.") @RequestParam(required = false) String name) {

        return null;
    }

    @Operation(summary = "Find contact by ID", description = "Returns a single contact", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")})
    @GetMapping(value = "/contacts/{contactId}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Contact> findContactById(
            @Parameter(description = "Id of the contact to be obtained. Cannot be empty.", required = true)
            @PathVariable long contactId) {
        return new ResponseEntity<Contact>(new Contact(), HttpStatus.OK);
    }

    @Operation(summary = "Add a new contact", description = "", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created",
                    content = @Content(schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Contact already exists")})
    @PostMapping(value = "/contacts", consumes = {"application/json", "application/xml"})
    public ResponseEntity<Contact> addContact(
            @Parameter(description = "Contact to add. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = Contact.class))
            @Valid @RequestBody Contact contact)
            throws URISyntaxException {

        return new ResponseEntity<Contact>(new Contact(), HttpStatus.OK);
    }

    @Operation(summary = "Update an existing contact", description = "", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Contact not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})
    @PutMapping(value = "/contacts/{contactId}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<Void> updateContact(
            @Parameter(description = "Id of the contact to be update. Cannot be empty.",
                    required = true)
            @PathVariable long contactId,
            @Parameter(description = "Contact to update. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = Contact.class))
            @Valid @RequestBody Contact contact) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletes a contact", description = "", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Contact not found")})
    @DeleteMapping(path = "/contacts/{contactId}")
    public ResponseEntity<Void> deleteContactById(
            @Parameter(description = "Id of the contact to be delete. Cannot be empty.",
                    required = true)
            @PathVariable long contactId) {
        return ResponseEntity.ok().build();
    }
}