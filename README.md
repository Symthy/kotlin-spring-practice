# Kotlin Spring Boot Practice

Kotlin + Spring Boot + Gradle (Kotlin DSL) を使用した練習プロジェクトです。

Kotlin の感触を知るために、AI に作らせました。

## 技術スタック

- **Kotlin**: 2.0.20 (K2 compiler)
- **Spring Boot**: 3.2.0
- **Gradle**: 8.5 (Kotlin DSL)
- **Java**: 21
- **データベース**: H2 (インメモリ)
- **テストフレームワーク**: JUnit 5, Kotlin.test, AssertJ 3.24.2
- **コード品質**: KtLint 12.1.0, Spotless 6.25.0

## プロジェクト構成

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/kotlinspringpractice/
│   │       ├── KotlinSpringPracticeApplication.kt  # メインアプリケーション
│   │       ├── TestMain.kt                         # Kotlin機能テスト用
│   │       ├── controller/
│   │       │   ├── HelloController.kt              # サンプルコントローラー
│   │       │   └── UserController.kt               # ユーザー管理API
│   │       ├── model/
│   │       │   └── User.kt                         # ユーザーエンティティ
│   │       ├── repository/
│   │       │   └── UserRepository.kt               # データアクセス層
│   │       ├── service/
│   │       │   └── UserService.kt                  # ビジネスロジック層
│   │       └── util/
│   │           ├── UserUtils.kt                    # トップレベル関数とエクステンション
│   │           └── StreamOperations.kt             # Kotlin Collection操作の例
│   └── resources/
│       └── application.yml                         # 設定ファイル
└── test/
    └── kotlin/
        └── com/example/kotlinspringpractice/
            ├── KotlinSpringPracticeApplicationTests.kt
            ├── controller/
            │   ├── HelloControllerTest.kt
            │   └── UserControllerTest.kt
            └── util/
                ├── UserUtilsTest.kt                # Kotlin.testを使用
                ├── UserUtilsAssertJTest.kt         # AssertJを使用
                ├── AssertionComparisonTest.kt      # テストライブラリ比較
                └── StreamOperationsTest.kt         # Collection操作テスト
```

## 実行方法

### 1. プロジェクトのビルド

```bash
./gradlew build
```

### 2. アプリケーションの起動

```bash
./gradlew bootRun
```

### 2. 動作確認

アプリケーションが起動したら、以下のエンドポイントにアクセスできます：

- **ヘルスチェック**: http://localhost:8080/api/health
- **Hello API**: http://localhost:8080/api/hello
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

### 3. User API エンドポイント

#### 基本的な CRUD 操作

- `GET /api/users` - 全ユーザー取得
- `GET /api/users/{id}` - 特定ユーザー取得
- `POST /api/users` - ユーザー作成
- `PUT /api/users/{id}` - ユーザー更新
- `DELETE /api/users/{id}` - ユーザー削除

#### 検索・フィルタリング

- `GET /api/users/search?name={name}` - 名前でユーザー検索
- `GET /api/users/by-age-category` - 年代別ユーザー分類

#### 統計・分析

- `GET /api/users/statistics` - ユーザー統計情報
- `GET /api/users/welcome-messages` - ウェルカムメッセージ一覧

#### 特殊な作成方法

- `POST /api/users/with-names?firstName={firstName}&lastName={lastName}&email={email}&age={age}` - 名前分割でユーザー作成

#### 使用例

**基本的なユーザー作成:**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "田中太郎",
    "email": "tanaka@example.com",
    "age": 30
  }'
```

**名前分割でユーザー作成:**

```bash
curl -X POST "http://localhost:8080/api/users/with-names?firstName=太郎&lastName=田中&email=tanaka@example.com&age=30"
```

**統計情報取得:**

```bash
curl http://localhost:8080/api/users/statistics
```

**年代別ユーザー分類:**

```bash
curl http://localhost:8080/api/users/by-age-category
```

## テストの実行

```bash
./gradlew test
```

### テスト構成

このプロジェクトでは複数のテストライブラリを使用しています：

- **JUnit 5**: テストフレームワーク
- **Kotlin.test**: Kotlin ネイティブなアサーション
- **AssertJ**: 流暢なアサーション
- **Spring Boot Test**: 統合テスト

テスト実行時に 30 個以上のテストが実行され、様々な Kotlin 機能の動作を確認できます。

## Kotlin 機能デモ

### 1. トップレベル関数 (`UserUtils.kt`)

Kotlin ではクラス外にグローバル関数を定義できます：

```kotlin
// 使用例
val isValid = validateEmail("test@example.com")
val formatted = formatUserName("john doe")
val display = user.getDisplayName() // エクステンション関数
```

### 2. Collection 操作 (`StreamOperations.kt`)

Java Stream の Kotlin 版操作例：

```kotlin
// フィルタリングとマッピング
val adultNames = users
    .filter { it.age >= 18 }
    .map { it.name }

// グループ化
val ageGroups = users.groupBy { it.age / 10 }

// 遅延評価（Sequence）
val result = users.asSequence()
    .filter { someExpensiveOperation(it) }
    .map { anotherExpensiveOperation(it) }
    .toList()
```

### 3. 主な Kotlin 言語機能

- **Null 安全性**: `user?.name ?: "Unknown"`
- **データクラス**: `data class User(...)`
- **エクステンション関数**: `fun User.getDisplayName()`
- **高階関数**: `users.filter { condition }`
- **分割代入**: `val (adults, children) = users.partition { it.age >= 18 }`
- **スマートキャスト**: 型の自動キャスト

## 開発環境

### 開発コンテナを使用した開発（推奨）

このプロジェクトは VS Code の Dev Containers 機能に対応しています。

#### 前提条件

- VS Code
- Docker Desktop
- Remote-Containers 拡張機能

#### セットアップ手順

1. VS Code でプロジェクトを開く
2. コマンドパレット（`Ctrl+Shift+P`）で「Dev Containers: Reopen in Container」を実行
3. コンテナが構築され、Java 21 環境で開発可能

#### 利用可能な設定

**基本設定（devcontainer.json）**

- Java 21 がプリインストール
- 必要な VS Code 拡張機能が自動インストール
- ポート 8080 が自動転送

**Docker Compose 設定（devcontainer-compose.json）**

- Java 21 + PostgreSQL 環境
- データベース付きで本格的な開発が可能
- ポート 8080（アプリ）、5432（DB）が利用可能

### ローカル開発

#### 前提条件

- Java 21 以上
- Docker（任意、データベースコンテナ用）

#### セットアップ

```bash
# プロジェクトをクローン
git clone <repository-url>
cd kotlin-spring-practice

# 実行権限を付与（Linux/Mac）
chmod +x gradlew

# ビルド
./gradlew build

# アプリケーション起動
./gradlew bootRun
```

### 開発用タスク

VS Code で以下のタスクが利用可能：

- **Spring Boot: Run** - アプリケーションを起動
- **Gradle: Build** - プロジェクトをビルド
- **Gradle: Test** - テストを実行
- **Gradle: Spotless Apply** - コードフォーマットを適用
- **Gradle: KtLint Check** - コード品質チェック

## 開発での注意点

- Java 21 以上が必要です（従来の Java 17 から変更）
- H2 データベースはインメモリなので、アプリケーション再起動時にデータは消えます
- 開発時は `spring-boot-devtools` が有効になっているため、コード変更時に自動でリロードされます
- Kotlin 2.0.20 の K2 コンパイラを使用しているため、コンパイルが高速です
- Spotless により KtLint の指摘が自動修正されます

## 学習ポイント

このプロジェクトで学べる Kotlin の特徴：

1. **Spring Boot と Kotlin の統合**
2. **データクラスと JPA エンティティ**
3. **Null 安全性と Optional 不要の設計**
4. **エクステンション関数による機能拡張**
5. **Collection 操作（Java Stream より簡潔）**
6. **複数のテストライブラリの比較**
7. **Kotlin DSL（Gradle 設定）**

## 参考リンク

- [Kotlin 公式ドキュメント](https://kotlinlang.org/docs/)
- [Spring Boot + Kotlin](https://spring.io/guides/tutorials/spring-boot-kotlin/)
- [Kotlin Collection API](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/)
