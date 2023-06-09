package edu.zut.awir2.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Username can not be null")
    @NotBlank(message = "Username can not be empty")
    private String username;

    @NotNull(message = "Email can not be null")
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    private long pdfFileId;
}
