package com.example.kotlinspringpractice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {
    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun `should return hello message`() {
        mockMvc
            .perform(get("/api/hello"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello, Kotlin Spring Boot!"))
    }

    @Test
    fun `should return health status`() {
        mockMvc
            .perform(get("/api/health"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("UP"))
            .andExpect(jsonPath("$.message").value("Application is running"))
    }
}
