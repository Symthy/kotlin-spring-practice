package com.example.kotlinspringpractice.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @field:NotBlank(message = "Name is required") @Column(nullable = false) val name: String,
    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    val email: String,
    @Column val age: Int? = null,
)
