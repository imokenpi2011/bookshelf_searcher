USE book;

SET CHARACTER_SET_CLIENT = utf8;
SET CHARACTER_SET_CONNECTION = utf8;

-- テストデータの作成
BEGIN;
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c221fd', 'あいのちから', 'たろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c222fd', 'いしあたまたろう', '二郎', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c223fd', 'うけみのごくい', 'さぶろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c224fd', 'エクボがすごい', 'にゃん', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c225fd', '鬼に金棒', 'ごろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c226fd', 'カニカニ合戦', '新島 さぶろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c227fd', 'キリンが来た', 'アレキサンダー・ジョン', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c228fd', 'クールなきつね', '太郎じろう', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c229fd', 'けむしむしむし', '炎の人', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c230fd', 'こばらがすいた猫', 'ぽちぽち', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c231fd', 'ささみパラダイス', '7太郎', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c232fd', 'しつけのプロ', 'situke tarou', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c233fd', 'ススキの生態', 'ススキマスター', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c234fd', 'セサミパンの作り方', 'セサミストリート', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c235fd', 'そいそいそいそーす', '大豆マン', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c236fd', 'たつじんの帯', 'エビワラー', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c237fd', 'author innovator', 'john', 'bookuser', 'bookuser');
INSERT INTO bookshelf (uuid, book_name, author_name, created_by, updated_by) VALUES ('9107ffb3-2457-4dc9-8f20-12c645c238fd', '1,2,3 GO！！', '！！！', 'bookuser', 'bookuser');
COMMIT;
