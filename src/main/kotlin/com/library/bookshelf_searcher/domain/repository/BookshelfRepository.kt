package com.library.bookshelf_searcher.domain.repository

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.repository.db.Tables.BOOKSHELF
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
     * ＠return List<ToBookshelf> 書籍情報の配列
     * */
    fun findAll(): List<ToBookshelf> = dsl.selectFrom(BOOKSHELF)
        .fetchInto(ToBookshelf::class.java)

    /**
     * IDに応じた書籍を取得する.
     *
     * @args uuid UUID
     * @return ToBookshelf 書籍情報
     */
    fun findByUuid(uuid: String): ToBookshelf = dsl.selectFrom(BOOKSHELF)
        .where(BOOKSHELF.UUID.eq(uuid))
        .limit(1)
        .fetchInto(ToBookshelf::class.java)
        .first()

    /**
     * 著者名に応じた書籍を取得する.
     *
     * @args authorName 著者名
     * @return List<ToBookshelf> 書籍情報の配列
     */
    fun findByAuthor(authorName: String): List<ToBookshelf> = dsl.selectFrom(BOOKSHELF)
        .where(BOOKSHELF.AUTHOR_NAME.eq(authorName))
        .and(BOOKSHELF.DELETE_STATUS.eq(0))
        .fetchInto(ToBookshelf::class.java)

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

    /**
     * ToBookshelfクラス.
     * */
    data class ToBookshelf(
        val id: Int,  // ID
        val uuid: String,  // UUID
        val bookName: String,  // タイトル
        val authorName: String  // 著者名
    )
}
