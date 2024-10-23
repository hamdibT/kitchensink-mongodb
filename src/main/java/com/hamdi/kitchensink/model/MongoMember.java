package com.hamdi.kitchensink.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Member entity representing a member in the system associated with MongoDB
 */
@Data
@Document(collection = "members")
@Schema(description = "Member entity representing a member in the system")
public class MongoMember {

    @Id
    @Schema(hidden = true)
    private String id;

    @Size(min = 1, max = 25)
    @NotNull
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    @Schema(description = "Name of the member", example = "HBT")
    private String name;

    @NotEmpty
    @NotNull
    @Email
    @Indexed(unique = true)
    @Schema(description = "Email address of the member", example = "hbt@example.com")
    private String email;

    @Size(min = 10, max = 12)
    @NotNull
    @Pattern(regexp = "\\d{10,12}", message = "Phone number must be between 10 and 12 digits")
    @Schema(description = "Phone number of the member", example = "1234567890")
    private String phoneNumber;
}
