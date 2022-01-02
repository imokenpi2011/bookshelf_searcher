package com.library.bookshelf_searcher.domain.repository

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.repository.db.Tables.BOOKSHELF
import com.library.bookshelf_searcher.domain.repository.db.tables.Bookshelf
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


/**
 * Bookshelfリポジトリクラス.
 */
@Repository
@Transactional
class BookshelfRepository(private val dsl: DSLContext) {

    /**
     * 全件取得する.
     *
     * ＠return books 書籍情報の配列
     * */
    fun findAll(): List<Bookshelf> = dsl.select()
        .from(BOOKSHELF)
        .fetch()
        .into(Bookshelf::class.java)

    /**
     * IDに応じた書籍を取得する.
     *
     * @args uuid UUID
     * @return book 書籍情報
     */
    fun findByUuid(uuid: String): Bookshelf? = dsl.select()
        .from(BOOKSHELF)
        .where(BOOKSHELF.UUID.eq(uuid))
        .limit(1)
        .fetch()
        .into(Bookshelf::class.java)
        .firstOrNull()

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
