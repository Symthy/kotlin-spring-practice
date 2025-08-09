package com.example.kotlinspringpractice.controller

import com.example.kotlinspringpractice.model.User
import com.example.kotlinspringpractice.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/search")
    fun searchUsers(@RequestParam name: String): ResponseEntity<List<User>> {
        val users = userService.searchUsersByName(name)
        return ResponseEntity.ok(users)
    }

    @GetMapping("/welcome-messages")
    fun getUserWelcomeMessages(): ResponseEntity<List<String>> {
        val messages = userService.getUserWelcomeMessages()
        return ResponseEntity.ok(messages)
    }

    @GetMapping("/statistics")
    fun getUserStatistics(): ResponseEntity<Map<String, Any>> {
        val stats = userService.getUserStatistics()
        return ResponseEntity.ok(stats)
    }

    @GetMapping("/by-age-category")
    fun getUsersByAgeCategory(): ResponseEntity<Map<String, List<User>>> {
        val categorizedUsers = userService.getUsersByAgeCategory()
        return ResponseEntity.ok(categorizedUsers)
    }

    @PostMapping
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<User> {
        return try {
            val createdUser = userService.createUser(user)
            ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/with-names")
    fun createUserWithNames(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam email: String,
        @RequestParam(required = false) age: Int?
    ): ResponseEntity<User> {
        return try {
            val createdUser = userService.createUserWithNames(firstName, lastName, email, age)
            ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody user: User): ResponseEntity<User> {
        return try {
            val updatedUser = userService.updateUser(id, user)
            if (updatedUser != null) {
                ResponseEntity.ok(updatedUser)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        val deleted = userService.deleteUser(id)
        return if (deleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
