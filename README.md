# Kotlin Spring Boot Practice

Kotlin + Spring Boot + Gradle (Kotlin DSL) を使用した練習プロジェクトです。

## 技術スタック

- **Kotlin**: 1.9.20
- **Spring Boot**: 3.2.0
- **Gradle**: 8.5 (Kotlin DSL)
- **Java**: 17
- **データベース**: H2 (インメモリ)

## プロジェクト構成

```
src/
├── main/
│   ├── kotlin/
│   │   └── com/example/kotlinspringpractice/
│   │       ├── KotlinSpringPracticeApplication.kt  # メインアプリケーション
│   │       ├── controller/
│   │       │   ├── HelloController.kt              # サンプルコントローラー
│   │       │   └── UserController.kt               # ユーザー管理API
│   │       ├── model/
│   │       │   └── User.kt                         # エンティティ
│   │       ├── repository/
│   │       │   └── UserRepository.kt               # リポジトリ
│   │       └── service/
│   │           └── UserService.kt                  # サービス層
│   └── resources/
│       └── application.yml                         # 設定ファイル
└── test/
    └── kotlin/
        └── com/example/kotlinspringpractice/
            ├── KotlinSpringPracticeApplicationTests.kt
            └── controller/
                └── HelloControllerTest.kt
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

### 3. 動作確認

アプリケーションが起動したら、以下のエンドポイントにアクセスできます：

- **ヘルスチェック**: http://localhost:8080/api/health
- **Hello API**: http://localhost:8080/api/hello
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

### 4. User API エンドポイント

- `GET /api/users` - 全ユーザー取得
- `GET /api/users/{id}` - 特定ユーザー取得
- `GET /api/users/search?name={name}` - 名前でユーザー検索
- `POST /api/users` - ユーザー作成
- `PUT /api/users/{id}` - ユーザー更新
- `DELETE /api/users/{id}` - ユーザー削除

#### ユーザー作成例

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "田中太郎",
    "email": "tanaka@example.com",
    "age": 30
  }'
```

## テストの実行

```bash
./gradlew test
```

## 開発での注意点

- Java 17 以上が必要です
- H2 データベースはインメモリなので、アプリケーション再起動時にデータは消えます
- 開発時は `spring-boot-devtools` が有効になっているため、コード変更時に自動でリロードされます

## IDE 設定

IntelliJ IDEA または VS Code での開発を推奨します。Kotlin プラグインが必要です。
