package com.library.bookshelf_searcher.domain.repository

import com.library.bookshelf_searcher.domain.repository.db.Tables.BOOKSHELF
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
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
@Transactional
class BookshelfRepository(private val dsl: DSLContext) {

    /**
     * 全件取得する.
     *
     * ＠return List<ToBookshelf> 書籍情報の配列
     * */
    fun findAll(): List<ToBookshelf> = dsl.selectFrom(BOOKSHELF)
        .where(BOOKSHELF.DELETE_STATUS.eq(NOT_DELETED))
        .fetchInto(ToBookshelf::class.java)

    /**
     * IDに応じた書籍を取得する.
     *
     * @args uuid UUID
     * @return ToBookshelf 書籍情報
     */
    fun findByUuid(uuid: String): ToBookshelf = dsl.selectFrom(BOOKSHELF)
        .where(BOOKSHELF.UUID.eq(uuid))
        .and(BOOKSHELF.DELETE_STATUS.eq(NOT_DELETED))
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
        .and(BOOKSHELF.DELETE_STATUS.eq(NOT_DELETED))
        .fetchInto(ToBookshelf::class.java)

    /**
     * 書籍情報を新規作成する.
     *
     * @args book 書籍情報
     * @return Int 登録件数
     */
    fun save(book: ToBookshelf): Int = dsl.insertInto(BOOKSHELF)
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
    fun update(book: ToBookshelf): Int = dsl.update(BOOKSHELF)
        .set(BOOKSHELF.BOOK_NAME, book.bookName)
        .set(BOOKSHELF.AUTHOR_NAME, book.authorName)
        .set(BOOKSHELF.UPDATED_BY, UPDATE_USER)
        .set(BOOKSHELF.UPDATED_AT, LocalDateTime.now())
        .where(BOOKSHELF.UUID.eq(book.uuid))
        .and(BOOKSHELF.DELETE_STATUS.eq(NOT_DELETED))
        .limit(1)
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
        .limit(1)
        .execute()

    /**
     * ToBookshelfクラス.
     * */
    data class ToBookshelf(
        val uuid: String,  // UUID
        val bookName: String,  // タイトル
        val authorName: String  // 著者名
    )
}
