package com.example.pro3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Please enter your account number")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String accountNumber;
    @NotNull(message = "Please enter your balance")
    @Positive
    @Column(columnDefinition = "double not null")
    private Double balance;
    @AssertFalse
    @Column(columnDefinition = "boolean ")
    private Boolean isActive;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

}
