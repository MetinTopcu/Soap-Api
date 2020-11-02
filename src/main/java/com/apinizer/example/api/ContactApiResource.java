package com.apinizer.example.api;

import com.apinizer.example.api.openapi.Contact;
import com.apinizer.example.api.openapi.ContactService;
import com.apinizer.example.api.rest.CustomErrorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/openapi")
@Tag(name = "contact", description = "the Contact API")
public class ContactApiResource {

    public static final Logger logger = LoggerFactory.getLogger(ContactApiResource.class);

    @Autowired
    ContactService contactService; //Service which will do all data retrieval/manipulation work


    @Operation(summary = "Find Contacts by name", description = "Name search by %name% format", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))})
    @GetMapping(value = "/contacts/", produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Contact>> findAll(
            @Parameter(description = "Page number, default is 1") @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @Parameter(description = "Name of the contact for search.") @RequestParam(required = false) String name) {

        List<Contact> users = contactService.findAllContacts();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Contact>>(users, HttpStatus.OK);
    }

    // -------------------Retrieve Single User------------------------------------------


    @Operation(summary = "Find contact by ID", description = "Returns a single contact", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")})
    @GetMapping(value = "/contacts/{contactId}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Contact> getByIdPathParam(
            @Parameter(description = "Id of the contact to be obtained. Cannot be empty.", required = true)
            @PathVariable long contactId) {
        return getById(contactId);
    }

    @Operation(summary = "Find contact by ID", description = "Returns a single contact", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")})
    @GetMapping(value = "/contacts", produces = {"application/json", "application/xml"})
    public ResponseEntity<Contact> getByIdQueryParam(
            @Parameter(description = "Id of the contact to be obtained. Cannot be empty.", required = true)
            @RequestParam("id") long contactId) {
        return getById(contactId);
    }

    private ResponseEntity<Contact> getById(long id) {
        Contact contact = contactService.findById(id);
        if (contact == null) {
            return new ResponseEntity(new CustomErrorType("contact with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }


    // -------------------Create a User-------------------------------------------

    @Operation(summary = "Add a new contact", description = "", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created",
                    content = @Content(schema = @Schema(implementation = Contact.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Contact already exists")})
    @PostMapping(value = "/contacts", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> createByBody(
            @Parameter(description = "Contact to add. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = Contact.class))
            @Valid @RequestBody Contact contact)
            throws URISyntaxException {

        if (contactService.isContactExist(contact)) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A Contact with name " +
                    contact.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        contactService.saveContact(contact);

        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(contact.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    // ------------------- Update a User ------------------------------------------------

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
        contactService.updateContact(contact);
        return ResponseEntity.ok().build();
    }

    // ------------------- Delete a User-----------------------------------------

    @Operation(summary = "Deletes a contact", description = "", tags = {"contact"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Contact not found")})
    @DeleteMapping(path = "/contacts/{contactId}")
    public ResponseEntity<Void> deleteContactById(
            @Parameter(description = "Id of the contact to be delete. Cannot be empty.",
                    required = true)
            @PathVariable long contactId) {
        contactService.deleteContactById(contactId);
        return ResponseEntity.ok().build();
    }
}