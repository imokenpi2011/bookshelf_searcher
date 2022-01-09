-- bookshelfの初期構築
SET CHARSET UTF8;

DROP SCHEMA IF EXISTS book;
CREATE SCHEMA book DEFAULT CHARACTER SET utf8;;
USE book;

DROP TABLE IF EXISTS bookshelf;

-- テーブルの作成
CREATE TABLE IF NOT EXISTS bookshelf (
    id     int auto_increment,  -- ID
    uuid	char(36) not null,	-- UUID
    book_name   varchar(137) not null,   -- 書籍名
    author_name   varchar(60) not null,   -- 著者名
    created_at  datetime default current_timestamp, -- 作成日時
    updated_at  datetime default current_timestamp, -- 更新日時
    created_by   varchar(60) not null,   -- 作成者
    updated_by   varchar(60) not null,   -- 更新者
    delete_status   int(1) default 0,   -- 論理削除
    PRIMARY KEY (id)
);
