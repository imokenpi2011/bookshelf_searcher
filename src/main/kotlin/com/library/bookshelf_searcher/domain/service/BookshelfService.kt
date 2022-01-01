package com.library.bookshelf_searcher.domain.service

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.repository.BookshelfRepository
import org.springframework.stereotype.Service

/**
 * Bookshelfサービスクラス.
 */
@Service
class BookshelfService(private val bookshelfRepository: BookshelfRepository) {

    /**
     * 全件取得する.
     *
     * ＠return books 書籍情報の配列
     * */
    fun findAll() = bookshelfRepository.findAll()

    /**
     * IDに応じた書籍を取得する.
     *
     * @args uuid UUID
     * @return book 書籍情報
     */
    fun findByUuid(uuid: String) = bookshelfRepository.findByUuid(uuid)

    /**
     * 書籍情報を新規作成する.
     *
     * @args book 書籍情報
     * @return bookRes レスポンスクラス
     */
    fun save(book: Book) = bookshelfRepository.save(book)

    /**
     * 書籍情報を更新する.
     *
     * @args book 書籍情報
     * @return bookRes レスポンスクラス
     */
    fun update(book: Book) = bookshelfRepository.update(book)

    /**
     * 書籍情報を削除する.
     *
     * @args uuid UUID
     * @return bookRes レスポンスクラス
     */
    fun delete(uuid: String) = bookshelfRepository.deleteByUuid(uuid)
}