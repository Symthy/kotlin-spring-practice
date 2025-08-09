package com.example.kotlinspringpractice.util

import com.example.kotlinspringpractice.model.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * AssertJを使った実用的なテスト例
 */
class UserUtilsAssertJTest {
    @Test
    fun `validateEmail with AssertJ - 成功ケース`() {
        val validEmails =
            listOf(
                "test@example.com",
                "user.name@domain.co.jp",
                "user+tag@example.org",
            )

        validEmails.forEach { email ->
            assertThat(validateEmail(email))
                .`as`("Email '$email' should be valid")
                .isTrue()
        }
    }

    @Test
    fun `validateEmail with AssertJ - 失敗ケース`() {
        val invalidEmails =
            mapOf(
                "invalid.email" to "ドメインなし",
                "@example.com" to "ローカル部なし",
                "test@" to "ドメイン部なし",
                "" to "空文字",
            )

        invalidEmails.forEach { (email, description) ->
            assertThat(validateEmail(email))
                .`as`("$description: '$email' should be invalid")
                .isFalse()
        }
    }

    @Test
    fun `formatUserName with AssertJ - 様々なケース`() {
        val testCases =
            mapOf(
                Pair("john", "doe") to "John Doe",
                Pair("ALICE", "smith") to "Alice Smith",
                Pair("bob", "JOHNSON") to "Bob Johnson",
                Pair("mary-jane", "o'connor") to "Mary-jane O'connor",
            )

        testCases.forEach { (input, expected) ->
            val result = formatUserName(input.first, input.second)
            assertThat(result)
                .`as`("Formatting '${input.first}' and '${input.second}'")
                .isEqualTo(expected)
                .matches("^[A-Z][a-z\\-]* [A-Z][a-z'\\-]*$") // 正規表現でフォーマットチェック
        }
    }

    @Test
    fun `processUsers with AssertJ - 高階関数のテスト`() {
        val users =
            listOf(
                User(id = 1, name = "Alice", email = "alice@example.com", age = 25),
                User(id = 2, name = "Bob", email = "bob@example.com", age = 30),
                User(id = 3, name = "Charlie", email = "charlie@example.com", age = 35),
            )

        val welcomeMessages =
            processUsers(users) { user ->
                createWelcomeMessage(user.name, "Hi")
            }

        assertThat(welcomeMessages)
            .hasSize(3)
            .allMatch { it.startsWith("Hi,") }
            .allMatch { it.endsWith("! Welcome to our service.") }
            .containsExactly(
                "Hi, Alice! Welcome to our service.",
                "Hi, Bob! Welcome to our service.",
                "Hi, Charlie! Welcome to our service.",
            )
    }

    @Test
    fun `classifyUserByAge with AssertJ - 分岐テスト`() {
        val ageTestCases =
            mapOf(
                null to "Unknown",
                15 to "Minor",
                17 to "Minor",
                18 to "Adult",
                30 to "Adult",
                64 to "Adult",
                65 to "Senior",
                80 to "Senior",
            )

        ageTestCases.forEach { (age, expectedCategory) ->
            assertThat(classifyUserByAge(age))
                .`as`("Age $age should be classified as $expectedCategory")
                .isEqualTo(expectedCategory)
        }

        // 境界値のより詳細なテスト
        assertThat(classifyUserByAge(17))
            .isNotEqualTo("Adult")
            .isEqualTo("Minor")

        assertThat(classifyUserByAge(18))
            .isNotEqualTo("Minor")
            .isEqualTo("Adult")

        assertThat(classifyUserByAge(65))
            .isNotEqualTo("Adult")
            .isEqualTo("Senior")
    }

    @Test
    fun `User extension function with AssertJ`() {
        val users =
            listOf(
                User(id = 1, name = "Alice", email = "alice@example.com", age = 25),
                User(id = 2, name = "Bob", email = "bob@example.com", age = null),
            )

        // 拡張関数のテスト
        val displayNames = users.map { it.getDisplayName() }
        assertThat(displayNames)
            .containsExactly(
                "Alice (alice@example.com)",
                "Bob (bob@example.com)",
            )

        // 個別の詳細テスト
        val alice = users[0]
        assertThat(alice.getDisplayName())
            .contains(alice.name)
            .contains(alice.email)
            .contains("(")
            .contains(")")
            .matches("^.+ \\(.+@.+\\)$")
    }

    @Test
    fun `数学的関数のテスト with AssertJ`() {
        // square関数のテスト
        val squareTestCases =
            mapOf(
                0 to 0,
                1 to 1,
                2 to 4,
                5 to 25,
                -3 to 9,
            )

        squareTestCases.forEach { (input, expected) ->
            assertThat(square(input))
                .`as`("$input squared should be $expected")
                .isEqualTo(expected)
                .isGreaterThanOrEqualTo(0) // 平方は常に0以上
        }

        // isEven関数のテスト
        val evenNumbers = listOf(0, 2, 4, 6, 8, 100)
        val oddNumbers = listOf(1, 3, 5, 7, 9, 99)

        assertThat(evenNumbers)
            .allMatch { num: Int -> isEven(num) }
            .`as`("All numbers should be even")

        assertThat(oddNumbers)
            .noneMatch { num: Int -> isEven(num) }
            .`as`("No numbers should be even")
    }

    @Test
    fun `ジェネリック拡張関数のテスト with AssertJ`() {
        // secondOrNull のテスト
        assertThat(listOf("first", "second", "third").secondOrNull())
            .isEqualTo("second")

        assertThat(listOf("only").secondOrNull())
            .isNull()

        assertThat(emptyList<String>().secondOrNull())
            .isNull()

        assertThat(listOf(1, 2).secondOrNull())
            .isEqualTo(2)
    }
}
