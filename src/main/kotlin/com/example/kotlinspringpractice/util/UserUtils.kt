package com.example.kotlinspringpractice.util

import com.example.kotlinspringpractice.model.User

// トップレベル関数の例
fun validateEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(email)
}

fun formatUserName(firstName: String, lastName: String): String {
    val formattedFirst = firstName.lowercase().replaceFirstChar { it.titlecase() }
    val formattedLast = lastName.lowercase().replaceFirstChar { it.titlecase() }
    return "$formattedFirst $formattedLast"
}

fun calculateUserAge(birthYear: Int): Int {
    val currentYear = java.time.LocalDate.now().year
    return currentYear - birthYear
}

// 拡張関数も定義可能
fun User.getDisplayName(): String {
    return "${this.name} (${this.email})"
}

// 高階関数の例
fun processUsers(users: List<User>, processor: (User) -> String): List<String> {
    return users.map(processor)
}

// デフォルトパラメータ付きの関数
fun createWelcomeMessage(name: String, greeting: String = "Hello"): String {
    return "$greeting, $name! Welcome to our service."
}

// 単一式関数
fun square(x: Int) = x * x

fun isEven(number: Int) = number % 2 == 0

// 型推論も可能
fun multiply(a: Int, b: Int) = a * b

// ジェネリック関数
fun <T> List<T>.secondOrNull(): T? = if (this.size >= 2) this[1] else null

// 年齢による分類
fun classifyUserByAge(age: Int?): String = when {
    age == null -> "Unknown"
    age < 18 -> "Minor"
    age < 65 -> "Adult"
    else -> "Senior"
}
