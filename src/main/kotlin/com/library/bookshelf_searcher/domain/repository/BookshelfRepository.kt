package com.library.bookshelf_searcher.domain.repository

import com.library.bookshelf_searcher.domain.model.Book
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Bookshelfリポジトリクラス.
 */
@Repository
@Transactional
class BookshelfRepository {

    /**
     * 全件取得する.
     *
     * ＠return books 書籍情報の配列
     * */
    fun findAll() {}

    /**
     * IDに応じた書籍を取得する.
     *
     * @args uuid UUID
     * @return book 書籍情報
     */
    fun findByUuid(uuid: String) {}

    /**
     * 書籍情報を新規作成する.
     *
     * @args book 書籍情報
     * @return bookRes レスポンスクラス
     */
    fun save(book: Book) {}

    /**
     * 書籍情報を更新する.
     *
     * @args book 書籍情報
     * @return bookRes レスポンスクラス
     */
    fun update(book: Book) {}

    /**
     * 書籍情報を削除する.
     *
     * @args uuid UUID
     * @return bookRes レスポンスクラス
     */
    fun deleteByUuid(uuid: String) {}

}