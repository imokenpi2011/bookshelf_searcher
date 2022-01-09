package com.library.bookshelf_searcher.domain.service

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.model.FormBook
import com.library.bookshelf_searcher.domain.repository.BookshelfRepository
import com.library.bookshelf_searcher.domain.repository.db.tables.pojos.Bookshelf
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever


@ExtendWith(MockitoExtension::class)
internal class BookshelfServiceTest {

    @Mock
    lateinit var bookshelfRepository: BookshelfRepository

    @InjectMocks
    lateinit var bookshelfService: BookshelfService

    class BookshelfServiceTest {
        companion object {
            /** バリデーション用 */
            // UUID
            const val VALID_UUID = "testUuid"

            // 書籍名
            const val VALID_BOOK = "test_book"

            // 著者名
            const val VALID_AUTHOR = "test_author"

        }
    }

    /** 試験用インスタンス */    // 試験用のBookインスタンス
    private val testFormBook = FormBook(
        bookName = BookshelfServiceTest.VALID_BOOK,
        authorName = BookshelfServiceTest.VALID_AUTHOR
    )

    // 試験用のBookインスタンス
    private val testBook = Book(
        uuid = BookshelfServiceTest.VALID_UUID,
        bookName = BookshelfServiceTest.VALID_BOOK,
        authorName = BookshelfServiceTest.VALID_AUTHOR
    )

    // 試験用のBookの配列
    private val testBookList = listOf(testBook, testBook)

    // 試験用のBookshelf
    private val testBookshelf = Bookshelf(
        uuid = BookshelfServiceTest.VALID_UUID,
        bookName = BookshelfServiceTest.VALID_BOOK,
        authorName = BookshelfServiceTest.VALID_AUTHOR,
    )

    // 試験用のBookshelfの配列
    private val testBookshelfList = listOf(testBookshelf, testBookshelf)

    /**
     * 全件取得処理のテスト.
     */
    @Test
    fun findAll_正常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.findAll()).thenReturn(testBookshelfList)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.findAll(), testBookList)
    }

    @Test
    fun findAll_異常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.findAll()).thenReturn(listOf<Bookshelf>())
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.findAll(), listOf<Book>())
    }

    /**
     * UUIDに紐づく取得処理のテスト.
     */
    @Test
    fun findByUuid_正常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.findByUuid(BookshelfServiceTest.VALID_UUID)).thenReturn(testBookshelf)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.findByUuid(BookshelfServiceTest.VALID_UUID), testBook)
    }

    @Test
    fun findByUuid_異常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.findByUuid(BookshelfServiceTest.VALID_UUID)).thenReturn(null)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.findByUuid(BookshelfServiceTest.VALID_UUID), null)
    }

    /**
     * 著者名に紐づく取得処理のテスト.
     */
    @Test
    fun findByAuthor_正常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.findByAuthor(BookshelfServiceTest.VALID_AUTHOR)).thenReturn(testBookshelfList)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.findByAuthor(BookshelfServiceTest.VALID_AUTHOR), testBookList)
    }

    @Test
    fun findByAuthor_異常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.findByAuthor(BookshelfServiceTest.VALID_AUTHOR)).thenReturn(listOf<Bookshelf>())
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.findByAuthor(BookshelfServiceTest.VALID_AUTHOR), listOf<Book>())
    }

    /**
     * 新規作成処理のテスト.
     */
    @Test
    fun save_正常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.save(any())).thenReturn(1)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.save(testFormBook), 1)
    }

    @Test
    fun save_異常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.save(any())).thenReturn(0)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.save(testFormBook), 0)
    }

    /**
     * 更新処理のテスト.
     */
    @Test
    fun update_正常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.update(any())).thenReturn(1)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.update(testBook), 1)
    }

    @Test
    fun update_異常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.update(any())).thenReturn(0)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.update(testBook), 0)
    }

    /**
     * 削除処理のテスト.
     */
    @Test
    fun delete_正常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.deleteByUuid(BookshelfServiceTest.VALID_UUID)).thenReturn(1)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.delete(BookshelfServiceTest.VALID_UUID), 1)
    }

    @Test
    fun delete_異常系() {
        // 返却値を定義
        whenever(this.bookshelfRepository.deleteByUuid(BookshelfServiceTest.VALID_UUID)).thenReturn(0)
        // 試験実施
        Assertions.assertEquals(this.bookshelfService.delete(BookshelfServiceTest.VALID_UUID), 0)
    }
}
