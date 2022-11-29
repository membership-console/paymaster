# Paymaster

![CI](https://github.com/membership-console/paymaster/workflows/CI/badge.svg)
![Build](https://github.com/membership-console/paymaster/workflows/Build/badge.svg)
![version](https://img.shields.io/badge/version-1.0.0--SNAPSHOT-blue.svg)

## 概要

本プロジェクトはMembership Consoleの会計システムです。

## 開発

### 開発環境

- Java OpenJDK 17
- Kotlin 1.6.10
- Micronaut 3.7.2
- PostgreSQL 13
- docker-compose

### ビルド方法

ビルドに成功すると、`app/build/libs`直下に`.jar`ファイルが生成されます。

```sh
$ ./gradlew build -x test
```

### 起動方法

まず、Docker から PostgreSQL を起動します。

```
$ docker compose up -d
# 5432 db: ローカル用データベース
# 55432 db-test: テスト用データベース
```

デフォルトで使用されるポート番号は`8080`です。${PORT}を設定することで変更できます。

```shell
# 1. run .jar file
$ java -jar paymaster-<version>-all.jar
```

もしくは

```shell
$ ./gradlew run
```
