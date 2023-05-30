package com.poly.asm.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
	@NotEmpty(message = "{NotEmpty.User.iD}")
	@Size(min = 20, message = "{Size.User.iD}")
	@Id
	private String iD;

	@NotEmpty(message = "{NotEmpty.User.fullName}")
	private String name;

	@NotEmpty(message = "{NotEmpty.User.password}")
	@Size(max = 10, message = "{Size.User.password}")
	private String password;

	@NotEmpty(message = "{NotEmpty.User.phoneNumber}")
	@Pattern(regexp = "\\d{10}", message = "{Pattern.User.phoneNumber}")
	private String phone;

	@NotEmpty(message = "{NotEmpty.User.email}")
	@Email(message = "{Email.User.email}")
	private String email;

	@NotEmpty(message = "{NotEmpty.User.address}")
	private String address;

	private String image;

	boolean admin;

	@OneToMany(mappedBy = "user")
	List<Invoice> invoices;

	// Các trường khác và getter/setter
}
