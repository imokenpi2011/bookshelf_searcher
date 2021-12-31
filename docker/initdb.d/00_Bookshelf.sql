-- bookshelfの初期構築

DROP SCHEMA IF EXISTS book;
CREATE SCHEMA book;
USE book;


DROP TABLE IF EXISTS bookshelf;

-- テーブルの作成
CREATE TABLE IF NOT EXISTS bookshelf (
    id     int auto_increment,  -- ID
    book_name   varchar(137) not null,   -- 書籍名
    author_name   varchar(60) not null,   -- 著者名
    created_at  datetime default current_timestamp, -- 作成日時
    updated_at  datetime default current_timestamp, -- 更新日時
    created_by   varchar(60) not null,   -- 作成者
    updated_by   varchar(60) not null,   -- 更新者
    delete_status   int(1) default 0,   -- 論理削除
    PRIMARY KEY (id)
);

-- テストデータの作成
BEGIN;
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('あいのちから', 'たろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('いしあたまたろう', '二郎', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('うけみのごくい', 'さぶろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('エクボがすごい', 'にゃん', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('鬼に金棒', 'ごろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('カニカニ合戦', '新島 さぶろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('キリンが来た', 'アレキサンダー・ジョン', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('クールなきつね', '太郎じろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('けむしむしむし', '炎の人', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('こばらがすいた猫', 'ぽちぽち', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('ささみパラダイス', '7太郎', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('しつけのプロ', 'situke tarou', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('ススキの生態', 'ススキマスター', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('セサミパンの作り方', 'セサミストリート', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('そいそいそいそーす', '大豆マン', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('たつじんの帯', 'エビワラー', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('author innovator', 'john', 'bookuser', 'bookuser');
INSERT INTO bookshelf (book_name, author_name, created_by, updated_by) VALUES ('1,2,3 GO！！', '！！！', 'bookuser', 'bookuser');
COMMIT;