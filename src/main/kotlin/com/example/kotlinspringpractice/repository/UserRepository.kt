package com.example.kotlinspringpractice.repository

import com.example.kotlinspringpractice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun findByNameContaining(name: String): List<User>

    fun findByAge(age: Int): List<User>
}
