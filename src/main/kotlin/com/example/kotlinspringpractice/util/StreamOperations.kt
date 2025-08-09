package com.example.kotlinspringpractice.util

import com.example.kotlinspringpractice.model.User

/**
 * Kotlin Collection Operations vs Java Stream
 * JavaのStreamに相当するKotlinの機能デモ
 */

// サンプルデータ
fun getSampleUsers(): List<User> = listOf(
    User(id = 1, name = "Alice", email = "alice@example.com", age = 25),
    User(id = 2, name = "Bob", email = "bob@example.com", age = 30),
    User(id = 3, name = "Charlie", email = "charlie@example.com", age = 35),
    User(id = 4, name = "Diana", email = "diana@example.com", age = 28),
    User(id = 5, name = "Eve", email = "eve@example.com", age = 22),
    User(id = 6, name = "Frank", email = "frank@example.com", age = 40),
    User(id = 7, name = "Grace", email = "grace@example.com", age = null)
)

// ======================
// 1. フィルタリング操作
// ======================

// Java Stream equivalent:
// users.stream().filter(user -> user.getAge() != null && user.getAge() > 25)
fun filterAdultUsers(): List<User> {
    val users = getSampleUsers()
    
    // Kotlin Collection - 直接的でわかりやすい
    return users.filter { it.age != null && it.age > 25 }
}

// ======================
// 2. マッピング操作
// ======================

// Java Stream equivalent:
// users.stream().map(User::getName).collect(Collectors.toList())
fun getUserNames(): List<String> {
    val users = getSampleUsers()
    
    // Kotlin Collection
    return users.map { it.name }
}

// 複雑なマッピング
// Java Stream equivalent:
// users.stream().map(user -> user.getName().toUpperCase() + " (" + user.getAge() + ")")
fun getUserDisplayInfo(): List<String> {
    val users = getSampleUsers()
    
    return users
        .filter { it.age != null }
        .map { "${it.name.uppercase()} (${it.age})" }
}

// ======================
// 3. グループ化操作
// ======================

// Java Stream equivalent:
// users.stream().collect(Collectors.groupingBy(user -> user.getAge() / 10))
fun groupUsersByAgeGroup(): Map<Int, List<User>> {
    val users = getSampleUsers()
    
    return users
        .filter { it.age != null }
        .groupBy { it.age!! / 10 }
}

// 名前でグループ化（より複雑な例）
fun groupUsersByNameLength(): Map<Int, List<String>> {
    val users = getSampleUsers()
    
    return users
        .groupBy { it.name.length }
        .mapValues { (_, userList) -> userList.map { it.name } }
}

// ======================
// 4. 集約操作（reduce, fold）
// ======================

// Java Stream equivalent:
// users.stream().mapToInt(User::getAge).sum()
fun getTotalAge(): Int {
    val users = getSampleUsers()
    
    return users
        .mapNotNull { it.age }
        .sum()
}

// 平均年齢
fun getAverageAge(): Double {
    val users = getSampleUsers()
    
    return users
        .mapNotNull { it.age }
        .average()
}

// カスタム集約
fun getConcatenatedNames(): String {
    val users = getSampleUsers()
    
    return users
        .map { it.name }
        .reduce { acc, name -> "$acc, $name" }
}

// fold を使った集約（初期値あり）
fun getUserSummary(): String {
    val users = getSampleUsers()
    
    return users.fold("Users: ") { acc, user ->
        "$acc${user.name}(${user.age ?: "unknown"}); "
    }
}

// ======================
// 5. Sequence（遅延評価）
// ======================

// Java Stream の遅延評価に相当
fun expensiveOperationWithSequence(): List<String> {
    val users = getSampleUsers()
    
    // Sequence を使用（遅延評価）
    return users.asSequence()
        .filter { 
            println("Filtering: ${it.name}") // デバッグ用
            it.age != null && it.age > 25 
        }
        .map { 
            println("Mapping: ${it.name}") // デバッグ用
            it.name.uppercase() 
        }
        .take(3) // 最初の3つだけ取得
        .toList() // 最終的にListに変換（ここで評価が実行される）
}

// 通常のCollection操作（即座に評価）
fun expensiveOperationWithCollection(): List<String> {
    val users = getSampleUsers()
    
    return users
        .filter { 
            println("Filtering: ${it.name}") // 全要素が処理される
            it.age != null && it.age > 25 
        }
        .map { 
            println("Mapping: ${it.name}") // 全要素が処理される
            it.name.uppercase() 
        }
        .take(3)
}

// ======================
// 6. 条件チェック操作
// ======================

// Java Stream equivalent:
// users.stream().anyMatch(user -> user.getAge() > 30)
fun hasUserOver30(): Boolean {
    val users = getSampleUsers()
    
    return users.any { it.age != null && it.age > 30 }
}

// 全ての条件チェック
fun allUsersHaveEmail(): Boolean {
    val users = getSampleUsers()
    
    return users.all { it.email.isNotEmpty() }
}

// 該当なしチェック
fun noUserUnder18(): Boolean {
    val users = getSampleUsers()
    
    return users.none { it.age != null && it.age < 18 }
}

// ======================
// 7. 検索操作
// ======================

// Java Stream equivalent:
// users.stream().filter(user -> user.getName().startsWith("A")).findFirst()
fun findFirstUserStartingWithA(): User? {
    val users = getSampleUsers()
    
    return users.find { it.name.startsWith("A") }
}

// 最初と最後
fun findOldestUser(): User? {
    val users = getSampleUsers()
    
    return users
        .filter { it.age != null }
        .maxByOrNull { it.age!! }
}

fun findYoungestUser(): User? {
    val users = getSampleUsers()
    
    return users
        .filter { it.age != null }
        .minByOrNull { it.age!! }
}

// ======================
// 8. ソート操作
// ======================

// Java Stream equivalent:
// users.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList())
fun sortUsersByAge(): List<User> {
    val users = getSampleUsers()
    
    return users
        .filter { it.age != null }
        .sortedBy { it.age }
}

// 複数条件でソート
fun sortUsersByAgeAndName(): List<User> {
    val users = getSampleUsers()
    
    return users
        .filter { it.age != null }
        .sortedWith(compareBy<User> { it.age }.thenBy { it.name })
}

// 降順ソート
fun sortUsersByAgeDescending(): List<User> {
    val users = getSampleUsers()
    
    return users
        .filter { it.age != null }
        .sortedByDescending { it.age }
}

// ======================
// 9. 重複除去とユニーク操作
// ======================

// Java Stream equivalent:
// users.stream().map(User::getAge).distinct()
fun getUniqueAges(): List<Int> {
    val users = getSampleUsers()
    
    return users
        .mapNotNull { it.age }
        .distinct()
}

// カスタム重複除去
fun getUniqueNameLengths(): List<Int> {
    val users = getSampleUsers()
    
    return users
        .map { it.name.length }
        .distinct()
        .sorted()
}

// ======================
// 10. パーティション操作
// ======================

// Java Stream equivalent:
// users.stream().collect(Collectors.partitioningBy(user -> user.getAge() >= 30))
fun partitionUsersByAge(): Pair<List<User>, List<User>> {
    val users = getSampleUsers()
    
    val (adults, young) = users
        .filter { it.age != null }
        .partition { it.age!! >= 30 }
    
    return Pair(adults, young)
}

// ======================
// 11. フラット化操作
// ======================

// 複雑なデータ構造のフラット化
fun flattenUserEmails(): List<String> {
    val userGroups = listOf(
        getSampleUsers().take(3),
        getSampleUsers().drop(3).take(2),
        getSampleUsers().drop(5)
    )
    
    // Java Stream equivalent:
    // userGroups.stream().flatMap(List::stream).map(User::getEmail)
    return userGroups
        .flatten()
        .map { it.email }
}

// ======================
// 12. 統計操作
// ======================

fun getUserStatistics(): Map<String, Any> {
    val users = getSampleUsers()
    val ages = users.mapNotNull { it.age }
    
    return mapOf(
        "totalUsers" to users.size,
        "usersWithAge" to ages.size,
        "averageAge" to if (ages.isNotEmpty()) ages.average() else 0.0,
        "minAge" to (ages.minOrNull() ?: 0),
        "maxAge" to (ages.maxOrNull() ?: 0),
        "totalAge" to ages.sum(),
        "ageGroups" to groupUsersByAgeGroup().keys.sorted()
    )
}
