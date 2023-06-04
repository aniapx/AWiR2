package edu.zut.awir2.Controllers;

import edu.zut.awir2.Models.Result;
import edu.zut.awir2.Models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "user", description = "the user API")
public interface UserApi {

    @Operation(summary = "Get all users", description = "Retrieves a list of all users.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Error.class)))
    })
    public ResponseEntity getAllUsers();

    @Operation(summary = "Get a user by ID", description = "Returns a user based on the provided ID.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(description = "Successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content)
    })
    public ResponseEntity getUser(@PathVariable long id);

    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    public ResponseEntity addUser(@RequestBody User user);

    @PutMapping("/")
    @Operation(summary = "Edit a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Error updating user")
    })
    public ResponseEntity editUser(@RequestBody User updatedUser);

    @Operation(summary = "Delete a user by ID", description = "Deletes a user with the specified ID.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(description = "User deleted successfully", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
    })
    public ResponseEntity deleteUser(@PathVariable long id);
}
