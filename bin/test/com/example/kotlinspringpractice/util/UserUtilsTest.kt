package com.example.kotlinspringpractice.util

import com.example.kotlinspringpractice.model.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserUtilsTest {

    @Test
    fun `validateEmail should return true for valid emails`() {
        assertTrue(validateEmail("test@example.com"))
        assertTrue(validateEmail("user.name@domain.co.jp"))
        assertTrue(validateEmail("user+tag@example.org"))
    }

    @Test
    fun `validateEmail should return false for invalid emails`() {
        assertFalse(validateEmail("invalid.email"))
        assertFalse(validateEmail("@example.com"))
        assertFalse(validateEmail("test@"))
        assertFalse(validateEmail(""))
    }

    @Test
    fun `formatUserName should capitalize names`() {
        assertEquals("John Doe", formatUserName("john", "doe"))
        assertEquals("Alice Smith", formatUserName("ALICE", "smith"))
        assertEquals("Bob Johnson", formatUserName("bob", "JOHNSON"))
    }

    @Test
    fun `calculateUserAge should return correct age`() {
        val currentYear = java.time.LocalDate.now().year
        assertEquals(25, calculateUserAge(currentYear - 25))
        assertEquals(0, calculateUserAge(currentYear))
    }

    @Test
    fun `User extension function getDisplayName should work`() {
        val user = User(id = 1, name = "John Doe", email = "john@example.com", age = 30)
        assertEquals("John Doe (john@example.com)", user.getDisplayName())
    }

    @Test
    fun `processUsers should apply processor function`() {
        val users = listOf(
            User(id = 1, name = "John", email = "john@example.com", age = 25),
            User(id = 2, name = "Jane", email = "jane@example.com", age = 30)
        )
        
        val result = processUsers(users) { user -> 
            createWelcomeMessage(user.name, "Hi")
        }
        
        assertEquals(2, result.size)
        assertEquals("Hi, John! Welcome to our service.", result[0])
        assertEquals("Hi, Jane! Welcome to our service.", result[1])
    }

    @Test
    fun `createWelcomeMessage should use default greeting`() {
        assertEquals("Hello, John! Welcome to our service.", createWelcomeMessage("John"))
    }

    @Test
    fun `createWelcomeMessage should use custom greeting`() {
        assertEquals("Hi, John! Welcome to our service.", createWelcomeMessage("John", "Hi"))
    }

    @Test
    fun `single expression functions should work`() {
        assertEquals(25, square(5))
        assertEquals(20, multiply(4, 5))
        assertTrue(isEven(4))
        assertFalse(isEven(5))
    }

    @Test
    fun `secondOrNull extension function should work`() {
        val list = listOf("first", "second", "third")
        assertEquals("second", list.secondOrNull())
        assertEquals(null, listOf("only").secondOrNull())
        assertEquals(null, emptyList<String>().secondOrNull())
    }

    @Test
    fun `classifyUserByAge should categorize correctly`() {
        assertEquals("Unknown", classifyUserByAge(null))
        assertEquals("Minor", classifyUserByAge(15))
        assertEquals("Adult", classifyUserByAge(25))
        assertEquals("Adult", classifyUserByAge(64))
        assertEquals("Senior", classifyUserByAge(65))
        assertEquals("Senior", classifyUserByAge(80))
    }
}
