# ブログカレンダー

## 開発環境

```
docker-compose up
```

サーバー側のREPLは `:42001` で起動するので接続して `(reload)` で起動する。

## 設定のハマりポイント

Supabaseのデフォルトの `public` スキーマの権限を剥がして使うよりももともと権限がない別スキーマ（`calendar`）を準備して使う。 `public` だとテーブル毎にRLSを設定しないと誰でも見れるのがデフォルト。そのため対象の環境のSupabaseコンソールから下記のSQLを実行してスキーマを新設する。

```sql
create schema if not exists calendar authorization postgres
```
