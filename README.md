# 本検索アプリ(bookshelf_searcher)

- [本検索アプリ(bookshelf_searcher)](#本検索アプリbookshelf_searcher)
  - [どんなアプリ？](#どんなアプリ)
  - [初期処理](#初期処理)
  - [機能](#機能)
    - [前提](#前提)
    - [1. 検索機能](#1-検索機能)
        - [概要](#概要)
        - [処理内容](#処理内容)
    - [2. 登録機能](#2-登録機能)
        - [概要](#概要-1)
        - [処理内容](#処理内容-1)
    - [3. 書籍一覧表示画面](#3-書籍一覧表示画面)
        - [概要](#概要-2)
      - [3-1. 更新機能](#3-1-更新機能)
        - [概要](#概要-3)
        - [処理内容](#処理内容-2)
      - [3-2. 削除機能](#3-2-削除機能)
        - [概要](#概要-4)
        - [処理内容](#処理内容-3)
## どんなアプリ？
書籍を管理するアプリ。

基本的には書籍と著者の2情報をメインとし、検索・更新・登録をできる様にする。

## 初期処理
1. `bookshelf_searcher`直下で`docker-compose up -d`を実行し、mysqlコンテナを起動してください。
2. アプリケーションを起動します。
3. 下記URLで各ページに遷移します。
- `[ホスト]/book`:検索画面
- `[ホスト]/book/list`:一覧画面
- `[ホスト]/book/new`:新規作成画面


## 機能
### 前提
書籍情報は書籍テーブルにて管理する。

書籍の管理テーブルは以下の項目を持つ。

- 書籍ID(primary)   : 書籍のID(自動付与)
- UUID(primary)    : 自動生成のUUID(36文字)
- 書籍名    : 書籍の名前(~255文字)
- 著者名    : 著者の名前(~60文字)
- 作成者    : 作成者名(~60文字)
- 更新者    : 更新者名(~60文字)
- 作成日時  : 作成日時
- 更新日時  : 更新日時
- 削除フラグ    : [0:無効],[1:有効]
```
※文字制限の意図
60文字    : 姓名が30+30=60文字で採用されるサイトが多いため
255文字   : 1バイトが256文字のため、有効数として一番大きい値の255を採用
```

### 1. 検索機能
##### 概要
- 著者名を元に検索を行う。
- 検索窓は著者の名前のみ入力させ、「検索」ボタン押下でGETで[authorName]というパラメータを渡して検索処理を実行。
##### 処理内容
- 検索する著者名は完全一致での検索とする。
- 検索結果と件数を表示する。
- 検索結果が存在しない場合は「検索結果:0件」のみ表示される
- 空文字は許容し、検索時にエラーが起きた時も0件となる

### 2. 登録機能
##### 概要
- 本の登録を行う。
- 登録用ページでは下記を入力し、「登録」ボタンの押下で登録処理をPOSTで行う。
   - 書籍名(255文字まで)
   - 著者名(60文字まで)
※この際、DBの文字コードがutf8mb4のため禁則文字は考慮しない。文字数がどちらか空または文字数オーバーだった場合のみ登録させない
##### 処理内容
- 現状ではユーザー管理を行わないため、作成者と更新者は「create_user」固定値で更新する。

- 登録処理では下記の値で書籍テーブルへの登録を行う
```
書籍ID    : 自動付与
書籍名    : 入力した名前(~255文字)
著者名    : 入力した名前(~60文字)
作成者    : create_user
更新者    : create_user
作成日時  : 現在時刻
更新日時  : 現在時刻
削除フラグ    : 0:無効
```
- 登録後は一覧画面に遷移し、「登録が完了しました」のメッセージを出す

### 3. 書籍一覧表示画面
##### 概要
- 登録されている本の一覧を表示する。
- 本の各項目には「更新」「削除」ボタンがあり、押下することでそれぞれに対応した処理を行う。
  - 「更新」押下時は更新画面に遷移する。
  - 「削除」押下時は削除処理を行なって一覧画面に遷移する。
- 基本的には下記項目を取得し、表示する。
```
条件：
  削除フラグが0のデータ

項目：
  書籍名
  著者名
```

#### 3-1. 更新機能
##### 概要
- 本の情報更新を行う。
- 「更新」ボタンの押下で登録処理をPOSTで行う。
   - 書籍名(255文字まで)
   - 著者名(60文字まで)
※この際、DBの文字コードがutf8mb4のため禁則文字は考慮しない。文字数がどちらか空または文字数オーバーだった場合のみ登録させない
##### 処理内容
- 一覧画面から「更新」を押した際に「update/1」の様な形で更新画面に遷移し、該当するIDのデータをDBから取得する。
- 取得したデータは下記項目にセットする
   - 書籍名
   - 著者名
- 初期表示時の取得に失敗した場合はエラーメッセージを表示して一覧画面に遷移する
- 更新が失敗した場合は項目を保持したままエラーメッセージを表示する
- 更新処理では下記の値で書籍テーブルへの更新を行う
```
書籍名    : 入力した名前(~255文字)
著者名    : 入力した名前(~60文字)
更新者    : update_user
更新日時  : 現在時刻
```
- 更新後は一覧画面に遷移し、「更新が完了しました」のメッセージを出す

#### 3-2. 削除機能
##### 概要
- 本の削除を行う。
- 一覧画面の各項目にある「削除」ボタンの押下でIDを渡し、削除処理をPOSTで行う。
##### 処理内容
- まず画面からUUIDを受け取る。
- 渡されたIDに応じたレコードの削除フラグを[1:有効]に更新する
- 削除が失敗した場合はエラーメッセージを渡して一覧画面に戻る
- 削除が成功した場合は一覧画面に遷移して「削除が完了しました」メッセージを出す