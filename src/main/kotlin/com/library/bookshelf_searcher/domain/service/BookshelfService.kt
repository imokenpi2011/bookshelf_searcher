package com.library.bookshelf_searcher.domain.service

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.model.FormBook
import com.library.bookshelf_searcher.domain.repository.BookshelfRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * Bookshelfサービスクラス.
 */
@Service
class BookshelfService(private val bookshelfRepository: BookshelfRepository) {

    /**
     * 全件取得する.
     *
     * ＠return bookList 書籍情報の配列
     * */
    fun findAll(): List<Book> {
        val bookList = bookshelfRepository.findAll()
        return bookList.map {
            Book(
                uuid = it.uuid,
                bookName = it.bookName,
                authorName = it.authorName
            )
        }
    }

    /**
     * IDに応じた書籍を取得する.
     *
     * @args uuid UUID
     * @return book 書籍情報
     */
    fun findByUuid(uuid: String): Book {
        val book = bookshelfRepository.findByUuid(uuid)
        return book.let {
            Book(
                uuid = it.uuid,
                bookName = it.bookName,
                authorName = it.authorName
            )
        }
    }

    /**
     * 著者名に応じた書籍を取得する.
     *
     * @args authorName 著者名
     * @return bookList 書籍情報の配列
     */
    fun findByAuthor(authorName: String): List<Book> {
        val bookList = bookshelfRepository.findByAuthor(authorName)
        return bookList.map {
            Book(
                uuid = it.uuid,
                bookName = it.bookName,
                authorName = it.authorName
            )
        }
    }

    /**
     * 書籍情報を新規作成する.
     *
     * @args book 書籍情報
     * @return Int 更新件数
     */
    fun save(formBook: FormBook): Int {
        val bookshelf = formBook.let {
            BookshelfRepository.ToBookshelf(
                uuid = UUID.randomUUID().toString(),
                bookName = it.bookName.toString(),
                authorName = it.authorName.toString()
            )
        }
        return bookshelfRepository.save(bookshelf)
    }

    /**
     * 書籍情報を更新する.
     *
     * @args book 書籍情報
     * @return Int 更新件数
     */
    fun update(book: Book): Int {
        val bookshelf = book.let {
            BookshelfRepository.ToBookshelf(
                uuid = it.uuid,
                bookName = it.bookName,
                authorName = it.authorName
            )
        }
        return bookshelfRepository.update(bookshelf)
    }

    /**
     * 書籍情報を削除する.
     *
     * @args uuid UUID
     * @return Int 更新件数
     */
    fun delete(uuid: String) = bookshelfRepository.deleteByUuid(uuid)
}
