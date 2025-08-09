package com.example.kotlinspringpractice.service

import com.example.kotlinspringpractice.model.User
import com.example.kotlinspringpractice.repository.UserRepository
import com.example.kotlinspringpractice.util.classifyUserByAge
import com.example.kotlinspringpractice.util.createWelcomeMessage
import com.example.kotlinspringpractice.util.formatUserName
import com.example.kotlinspringpractice.util.processUsers
import com.example.kotlinspringpractice.util.validateEmail
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)

    fun getUserByEmail(email: String): User? = userRepository.findByEmail(email)

    fun searchUsersByName(name: String): List<User> = userRepository.findByNameContaining(name)

    fun createUser(user: User): User {
        // トップレベル関数を使用してバリデーション
        if (!validateEmail(user.email)) {
            throw IllegalArgumentException("Invalid email format: ${user.email}")
        }

        val savedUser = userRepository.save(user)

        // 拡張関数を使用
        println("Created user: ${savedUser.name}")

        return savedUser
    }

    fun createUserWithNames(firstName: String, lastName: String, email: String, age: Int?): User {
        // トップレベル関数を使用
        if (!validateEmail(email)) {
            throw IllegalArgumentException("Invalid email format: $email")
        }

        val fullName = formatUserName(firstName, lastName)
        val user = User(name = fullName, email = email, age = age)

        val savedUser = userRepository.save(user)
        println("Created user: ${savedUser.name}")

        return savedUser
    }

    fun getUserWelcomeMessages(): List<String> {
        val users = userRepository.findAll()
        // 高階関数を使用
        return processUsers(users) { user ->
            createWelcomeMessage(user.name)
        }
    }

    fun getUsersByAgeCategory(): Map<String, List<User>> {
        val users = userRepository.findAll()
        return users.groupBy { user ->
            classifyUserByAge(user.age)
        }
    }

    fun getUserStatistics(): Map<String, Any> {
        val users = userRepository.findAll()
        val totalUsers = users.size
        val averageAge = users.mapNotNull { it.age }.average().takeIf { !it.isNaN() } ?: 0.0
        val ageCategories = getUsersByAgeCategory()

        return mapOf(
            "totalUsers" to totalUsers,
            "averageAge" to averageAge,
            "ageDistribution" to ageCategories.mapValues { it.value.size },
        )
    }

    fun updateUser(id: Long, updatedUser: User): User? {
        return if (userRepository.existsById(id)) {
            // バリデーションを追加
            if (!validateEmail(updatedUser.email)) {
                throw IllegalArgumentException("Invalid email format: ${updatedUser.email}")
            }
            userRepository.save(updatedUser.copy(id = id))
        } else {
            null
        }
    }

    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
