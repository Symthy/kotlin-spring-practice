package com.example.kotlinspringpractice.util

import com.example.kotlinspringpractice.model.User
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals as ktAssertEquals
import kotlin.test.assertFalse as ktAssertFalse
import kotlin.test.assertTrue as ktAssertTrue

/**
 * AssertJとKotlin.testの比較デモ
 */
class AssertionComparisonTest {

    @Test
    fun `基本的なアサーション比較`() {
        val name = "John Doe"
        val age = 30
        
        // === Kotlin.test ===
        ktAssertEquals("John Doe", name)
        ktAssertTrue(age > 18)
        
        // === AssertJ ===
        assertThat(name).isEqualTo("John Doe")
        assertThat(age).isGreaterThan(18)
    }

    @Test
    fun `文字列のアサーション比較`() {
        val email = "john.doe@example.com"
        
        // === AssertJ - より表現力豊か ===
        assertThat(email)
            .isNotNull()
            .isNotEmpty()
            .contains("@")
            .startsWith("john")
            .endsWith(".com")
            .hasSize(20)
        
        // === Kotlin.test - シンプル ===
        ktAssertTrue(email.isNotEmpty())
        ktAssertTrue(email.contains("@"))
        ktAssertTrue(email.startsWith("john"))
    }

    @Test
    fun `コレクションのアサーション比較`() {
        val users = listOf(
            User(id = 1, name = "Alice", email = "alice@example.com", age = 25),
            User(id = 2, name = "Bob", email = "bob@example.com", age = 30),
            User(id = 3, name = "Charlie", email = "charlie@example.com", age = 35)
        )
        
        // === AssertJ - 非常に表現力豊か ===
        assertThat(users)
            .isNotEmpty()
            .hasSize(3)
            .extracting("name")
            .containsExactly("Alice", "Bob", "Charlie")
        
        assertThat(users)
            .filteredOn { it.age!! > 30 }
            .hasSize(1)
            .extracting("name")
            .containsOnly("Charlie")
        
        // === Kotlin.test - 手動でチェック ===
        ktAssertEquals(3, users.size)
        ktAssertEquals("Alice", users[0].name)
        ktAssertTrue(users.any { it.name == "Charlie" && it.age!! > 30 })
    }

    @Test
    fun `例外のアサーション比較`() {
        // === Kotlin.test ===
        assertThrows<IllegalArgumentException> {
            validateEmail("invalid-email")
        }
        
        // === AssertJ - より詳細 ===
        assertThatThrownBy { validateEmail("invalid-email") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid email format")
        
        // 例外が発生しないことの確認
        assertThatCode { validateEmail("valid@example.com") }
            .doesNotThrowAnyException()
    }

    @Test
    fun `オブジェクトのアサーション比較`() {
        val user = User(id = 1, name = "John Doe", email = "john@example.com", age = 30)
        
        // === AssertJ - フィールド単位の検証 ===
        assertThat(user)
            .isNotNull()
            .extracting("name", "email", "age")
            .containsExactly("John Doe", "john@example.com", 30)
        
        assertThat(user)
            .hasFieldOrPropertyWithValue("name", "John Doe")
            .hasFieldOrPropertyWithValue("age", 30)
        
        // === Kotlin.test - 個別チェック ===
        ktAssertEquals("John Doe", user.name)
        ktAssertEquals("john@example.com", user.email)
        ktAssertEquals(30, user.age)
    }

    @Test
    fun `条件付きアサーション比較`() {
        val numbers = listOf(2, 4, 6, 8, 10)
        
        // === AssertJ - 全要素の条件チェック ===
        assertThat(numbers)
            .allMatch { it % 2 == 0 }
            .allMatch { it > 0 }
            .noneMatch { it > 15 }
        
        // === Kotlin.test - 手動ループ ===
        ktAssertTrue(numbers.all { it % 2 == 0 })
        ktAssertTrue(numbers.all { it > 0 })
        ktAssertFalse(numbers.any { it > 15 })
    }

    @Test
    fun `カスタムアサーション - AssertJの真価`() {
        val user = User(id = 1, name = "John Doe", email = "john@example.com", age = 30)
        
        // === AssertJ - 個別アサーション ===
        assertThat(user.name).isNotEmpty
        assertThat(user.email).contains("@")
        assertThat(user.age).isBetween(18, 65)
        
        // 複数の条件を組み合わせ
        assertThat(user)
            .matches({ it.name.isNotEmpty() }, "name should not be empty")
            .matches({ it.email.contains("@") }, "email should contain @")
            .matches({ it.age!! in 18..65 }, "age should be between 18 and 65")
    }

    @Test
    fun `エラーメッセージの比較`() {
        val users = listOf<User>()
        
        // === AssertJ - 詳細で分かりやすいエラーメッセージ ===
        try {
            assertThat(users)
                .`as`("ユーザーリストは空ではないはず")
                .isNotEmpty()
        } catch (e: AssertionError) {
            println("AssertJ エラー: ${e.message}")
        }
        
        // === Kotlin.test - シンプルなエラーメッセージ ===
        try {
            ktAssertTrue(users.isNotEmpty(), "ユーザーリストは空ではないはず")
        } catch (e: AssertionError) {
            println("Kotlin.test エラー: ${e.message}")
        }
    }

    // validateEmailを模擬（実際の実装と異なる場合があります）
    private fun validateEmail(email: String): Boolean {
        if (!email.contains("@")) {
            throw IllegalArgumentException("Invalid email format")
        }
        return true
    }
}
