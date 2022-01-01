package com.library.bookshelf_searcher.domain.service

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.repository.BookshelfRepository
import org.springframework.stereotype.Service

/**
 * bookshelfサービスクラス.
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
     * @args id 書籍ID
     * @return book 書籍情報
     */
    fun findById(id: Int) = bookshelfRepository.findById(id).orElse(null)

    /**
     * 書籍情報を新規作成する.
     *
     * @args book 書籍情報
     * @return status ステータス
     */
    fun save(book: Book) = bookshelfRepository.save()

    /**
     * 書籍情報を更新する.
     *
     * @args book 書籍情報
     * @return status ステータス
     */
    fun update(book: Book) = bookshelfRepository.update()

    /**
     * 書籍情報を削除する.
     *
     * @args id 書籍ID
     * @return status ステータス
     */
    fun delete(id: Int) = bookshelfRepository.deleteById(id)
}