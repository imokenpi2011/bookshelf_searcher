package com.library.bookshelf_searcher.domain.repository

import com.library.bookshelf_searcher.domain.repository.db.tables.daos.BookshelfDao
import com.library.bookshelf_searcher.domain.repository.db.tables.pojos.Bookshelf
import com.library.bookshelf_searcher.domain.repository.db.tables.references.BOOKSHELF
import org.jooq.Configuration
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/** 作成者名. */
const val CREATE_USER = "admin"

/** 更新者名. */
const val UPDATE_USER = "admin"

/** 削除ステータス:未削除. */
const val NOT_DELETED = 0

/** 削除ステータス:未削除. */
const val DELETED = 1

/**
 * Bookshelfリポジトリクラス.
 */
@Repository
class BookshelfRepository(private val dsl: DSLContext, configuration: Configuration) {

    /** Daoの生成. */
    val bookshelfDao: BookshelfDao = BookshelfDao(configuration)

    /**
     * 全件取得する.
     *
     * ＠return List<ToBookshelf> 書籍情報の配列
     * */
    fun findAll(): List<Bookshelf> = bookshelfDao.fetchByDeleteStatus(NOT_DELETED)

    /**
     * IDに応じた書籍を取得する.
     *
     * @args uuid UUID
     * @return ToBookshelf 書籍情報
     */
    fun findByUuid(uuid: String): Bookshelf? = bookshelfDao.fetchByUuid(uuid).firstOrNull()

    /**
     * 著者名に応じた書籍を取得する.
     *
     * @args authorName 著者名
     * @return List<ToBookshelf> 書籍情報の配列
     */
    fun findByAuthor(authorName: String): List<Bookshelf> = bookshelfDao.fetchByAuthorName(authorName)

    /**
     * 書籍情報を新規作成する.
     *
     * @args book 書籍情報
     * @return Int 登録件数
     */
    fun save(book: Bookshelf): Int = dsl.insertInto(BOOKSHELF)
        .set(BOOKSHELF.UUID, book.uuid)
        .set(BOOKSHELF.BOOK_NAME, book.bookName)
        .set(BOOKSHELF.AUTHOR_NAME, book.authorName)
        .set(BOOKSHELF.CREATED_AT, LocalDateTime.now())
        .set(BOOKSHELF.UPDATED_AT, LocalDateTime.now())
        .set(BOOKSHELF.CREATED_BY, CREATE_USER)
        .set(BOOKSHELF.UPDATED_BY, UPDATE_USER)
        .set(BOOKSHELF.DELETE_STATUS, NOT_DELETED)
        .execute()

    /**
     * 書籍情報を更新する.
     *
     * @args book 書籍情報
     * @return Int 更新件数
     */
    fun update(book: Bookshelf): Int = dsl.update(BOOKSHELF)
        .set(BOOKSHELF.BOOK_NAME, book.bookName)
        .set(BOOKSHELF.AUTHOR_NAME, book.authorName)
        .set(BOOKSHELF.UPDATED_BY, UPDATE_USER)
        .set(BOOKSHELF.UPDATED_AT, LocalDateTime.now())
        .where(BOOKSHELF.UUID.eq(book.uuid))
        .and(BOOKSHELF.DELETE_STATUS.eq(NOT_DELETED))
        .execute()

    /**
     * 書籍情報を削除する.
     *
     * @args uuid UUID
     * @return Int 削除件数
     */
    fun deleteByUuid(uuid: String): Int = dsl.update(BOOKSHELF)
        .set(BOOKSHELF.DELETE_STATUS, DELETED)
        .where(BOOKSHELF.UUID.eq(uuid))
        .execute()
}
