package com.example.kotlinspringpractice

import com.example.kotlinspringpractice.util.formatUserName

fun main() {
    println("Testing formatUserName function:")
    println("formatUserName('john', 'doe') = '${formatUserName("john", "doe")}'")
    println("formatUserName('ALICE', 'smith') = '${formatUserName("ALICE", "smith")}'")
    println("formatUserName('bob', 'JOHNSON') = '${formatUserName("bob", "JOHNSON")}'")
}
