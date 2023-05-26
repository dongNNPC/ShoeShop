package com.poly.asm.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor


public class User {
    @NotEmpty(message = "{NotEmpty.User.iD}")
    @Size(min = 20, message = "{Size.User.iD}")
    private String iD;

    @NotEmpty(message = "{NotEmpty.User.fullName}")
    private String fullName;

    @NotEmpty(message = "{NotEmpty.User.password}")
    @Size(min = 8, message = "{Size.User.password}")
    private String password;

    @NotEmpty(message = "{NotEmpty.User.phoneNumber}")
    @Pattern(regexp = "\\d{10}", message = "{Pattern.User.phoneNumber}")
    private String phoneNumber;

    @NotEmpty(message = "{NotEmpty.User.email}")
    @Email(message = "{Email.User.email}")
    private String email;

    @NotEmpty(message = "{NotEmpty.User.address}")
    private String address;

    // Các trường khác và getter/setter
}



