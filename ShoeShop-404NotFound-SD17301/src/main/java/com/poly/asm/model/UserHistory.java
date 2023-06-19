package com.poly.asm.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Userhistories")
public class UserHistory {
	@OrderBy("idHistory DESC")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idHistory;

	private String note;

	private Date historyDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	// Các trường khác và getter/setter
}
