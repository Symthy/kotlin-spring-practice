package com.example.kotlinspringpractice.util

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Kotlin Collection Operations のテスト
 */
class StreamOperationsTest {

    @Test
    fun `フィルタリング操作のテスト`() {
        val adults = filterAdultUsers()
        
        assertThat(adults)
            .hasSize(4) // Alice(25)は含まれない、Bob(30), Charlie(35), Diana(28), Frank(40)
            .allMatch { it.age != null && it.age > 25 }
            .extracting("name")
            .containsExactly("Bob", "Charlie", "Diana", "Frank")
    }

    @Test
    fun `マッピング操作のテスト`() {
        val names = getUserNames()
        
        assertThat(names)
            .hasSize(7)
            .containsExactly("Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace")
        
        val displayInfo = getUserDisplayInfo()
        assertThat(displayInfo)
            .allMatch { it.contains("(") && it.contains(")") }
            .anyMatch { it.startsWith("ALICE") }
    }

    @Test
    fun `グループ化操作のテスト`() {
        val ageGroups = groupUsersByAgeGroup()
        
        // 20代(2), 30代(3), 40代(1)
        assertThat(ageGroups)
            .hasSize(3)
            .containsKeys(2, 3, 4) // 20代、30代、40代
        
        assertThat(ageGroups[2]) // 20代
            .hasSize(3) // Alice(25), Diana(28), Eve(22)
        
        assertThat(ageGroups[3]) // 30代
            .hasSize(2) // Bob(30), Charlie(35)
        
        val nameLengthGroups = groupUsersByNameLength()
        assertThat(nameLengthGroups)
            .containsKey(3) // "Bob", "Eve"
            .containsKey(5) // "Alice", "Diana", "Frank", "Grace"
    }

    @Test
    fun `集約操作のテスト`() {
        val totalAge = getTotalAge()
        // 25 + 30 + 35 + 28 + 22 + 40 = 180
        assertThat(totalAge).isEqualTo(180)
        
        val avgAge = getAverageAge()
        assertThat(avgAge).isEqualTo(30.0)
        
        val concatenated = getConcatenatedNames()
        assertThat(concatenated)
            .startsWith("Alice")
            .contains("Bob")
            .contains("Charlie")
        
        val summary = getUserSummary()
        assertThat(summary)
            .startsWith("Users: ")
            .contains("Alice(25)")
            .contains("Grace(unknown)")
    }

    @Test
    fun `Sequence vs Collection の動作確認`() {
        println("=== Sequence (遅延評価) ===")
        val sequenceResult = expensiveOperationWithSequence()
        
        println("\n=== Collection (即座評価) ===")
        val collectionResult = expensiveOperationWithCollection()
        
        // 結果は同じ
        assertThat(sequenceResult)
            .hasSize(3)
            .isEqualTo(collectionResult)
    }

    @Test
    fun `条件チェック操作のテスト`() {
        assertThat(hasUserOver30()).isTrue() // Charlie(35), Frank(40)
        assertThat(allUsersHaveEmail()).isTrue() // 全員メールアドレスあり
        assertThat(noUserUnder18()).isTrue() // 18歳未満なし
    }

    @Test
    fun `検索操作のテスト`() {
        val userA = findFirstUserStartingWithA()
        assertThat(userA)
            .isNotNull()
            .extracting("name")
            .isEqualTo("Alice")
        
        val oldest = findOldestUser()
        assertThat(oldest)
            .isNotNull()
            .extracting("name")
            .isEqualTo("Frank")
        
        val youngest = findYoungestUser()
        assertThat(youngest)
            .isNotNull()
            .extracting("name")
            .isEqualTo("Eve")
    }

    @Test
    fun `ソート操作のテスト`() {
        val sortedByAge = sortUsersByAge()
        
        assertThat(sortedByAge)
            .hasSize(6) // Graceは年齢null除外
            .extracting("age")
            .containsExactly(22, 25, 28, 30, 35, 40)
        
        val sortedMultiple = sortUsersByAgeAndName()
        assertThat(sortedMultiple)
            .extracting("name")
            .startsWith("Eve") // 22歳
            .endsWith("Frank") // 40歳
        
        val sortedDesc = sortUsersByAgeDescending()
        assertThat(sortedDesc)
            .extracting("age")
            .containsExactly(40, 35, 30, 28, 25, 22)
    }

    @Test
    fun `重複除去とユニーク操作のテスト`() {
        val uniqueAges = getUniqueAges()
        
        assertThat(uniqueAges)
            .hasSize(6)
            .containsExactlyInAnyOrder(22, 25, 28, 30, 35, 40)
        
        val uniqueNameLengths = getUniqueNameLengths()
        assertThat(uniqueNameLengths)
            .contains(3, 5, 7) // Bob/Eve(3), Alice/Diana/Frank/Grace(5), Charlie(7)
            .isSorted()
    }

    @Test
    fun `パーティション操作のテスト`() {
        val (adults, young) = partitionUsersByAge()
        
        assertThat(adults)
            .hasSize(3) // Bob(30), Charlie(35), Frank(40)
            .allMatch { it.age!! >= 30 }
        
        assertThat(young)
            .hasSize(3) // Alice(25), Diana(28), Eve(22)
            .allMatch { it.age!! < 30 }
    }

    @Test
    fun `フラット化操作のテスト`() {
        val emails = flattenUserEmails()
        
        assertThat(emails)
            .hasSize(7)
            .allMatch { it.contains("@") }
            .contains("alice@example.com", "bob@example.com")
    }

    @Test
    fun `統計操作のテスト`() {
        val stats = getUserStatistics()
        
        assertThat(stats)
            .containsEntry("totalUsers", 7)
            .containsEntry("usersWithAge", 6)
            .containsEntry("averageAge", 30.0)
            .containsEntry("minAge", 22)
            .containsEntry("maxAge", 40)
            .containsEntry("totalAge", 180)
        
        @Suppress("UNCHECKED_CAST")
        val ageGroups = stats["ageGroups"] as List<Int>
        assertThat(ageGroups)
            .containsExactly(2, 3, 4) // 20代、30代、40代
    }

    @Test
    fun `実用的なKotlin Collection操作の例`() {
        val users = getSampleUsers()
        
        // 複雑なチェーン操作の例
        val result = users
            .filter { it.age != null && it.age >= 25 }
            .sortedByDescending { it.age }
            .groupBy { it.age!! / 10 }
            .mapValues { (_, userList) ->
                userList
                    .map { "${it.name}(${it.age})" }
                    .joinToString(", ")
            }
        
        assertThat(result)
            .containsKeys(2, 3, 4)
            .containsEntry(4, "Frank(40)")
            .containsEntry(3, "Charlie(35), Bob(30)")
        
        // 年代別統計
        val ageGroupStats = users
            .filter { it.age != null }
            .groupBy { it.age!! / 10 }
            .mapValues { (_, userList) ->
                mapOf(
                    "count" to userList.size,
                    "averageAge" to userList.mapNotNull { it.age }.average(),
                    "names" to userList.map { it.name }
                )
            }
        
        assertThat(ageGroupStats[2])
            .isNotNull()
            .containsEntry("count", 3)
        
        @Suppress("UNCHECKED_CAST")
        val names20s = (ageGroupStats[2] as Map<String, Any>)["names"] as List<String>
        assertThat(names20s)
            .containsExactlyInAnyOrder("Alice", "Diana", "Eve")
    }
}
