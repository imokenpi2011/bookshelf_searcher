package com.library.bookshelf_searcher.app.controller

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.model.FormBook
import com.library.bookshelf_searcher.domain.model.Message
import com.library.bookshelf_searcher.domain.service.BookshelfService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(BookshelfController::class)
internal class BookshelfControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var bookshelfService: BookshelfService

    class BookshelfControllerTest {
        companion object {
            /** ページ名 */
            // 検索
            const val PAGE_SEARCH = "bookshelf/index"

            // 一覧
            const val PAGE_LIST = "bookshelf/bookList"

            // 新規
            const val PAGE_NEW = "bookshelf/bookNew"

            // 更新
            const val PAGE_UPDATE = "bookshelf/bookUpdate"

            /** メッセージ */
            // 登録系
            const val SAVE_SUCCESS_MESSAGE = "書籍情報を登録しました。"

            const val SAVE_FAILED_MESSAGE = "書籍情報の登録に失敗しました。"

            // 更新系
            const val GETBOOK_FAILED_MESSAGE = "書籍情報の取得に失敗しました。"

            const val UPDATE_SUCCESS_MESSAGE = "書籍情報を更新しました。"

            const val UPDATE_FAILED_MESSAGE = "書籍情報の更新に失敗しました。"

            // 削除系
            const val DELETE_SUCCESS_MESSAGE = "書籍情報を削除しました。"

            const val DELETE_FAILED_MESSAGE = "書籍情報の削除に失敗しました。"

            /** バリデーション用 */
            // UUID
            const val VALID_UUID = "testUuid"

            // 書籍名
            const val VALID_BOOK = "test_book"

            // 著者名
            const val VALID_AUTHOR = "test_author"

            /** バリデーション用 */
            // 空文字
            const val EMPTY = ""

            // 256文字
            const val OVER255 =
                "1111111111222222222211111111112222222222111111111122222222221111111111222222222211111111112222222222111111111122222222221111111111222222222211111111112222222222111111111122222222221111111111222222222211111111112222222222111111111122222222221111111111222222"

            // 61文字
            const val OVER60 = "1111111111222222222211111111112222222222111111111122222222221"
        }
    }

    /** 試験用インスタンス */
    // 試験用のBookインスタンス
    private val testBook = Book(
        uuid = BookshelfControllerTest.VALID_UUID,
        bookName = BookshelfControllerTest.VALID_BOOK,
        authorName = BookshelfControllerTest.VALID_AUTHOR
    )

    // 試験用のBookの配列
    private val testBookList = listOf(testBook, testBook)

    // 試験用のformBookインスタンス
    private val testFormBook = FormBook(
        bookName = BookshelfControllerTest.VALID_BOOK,
        authorName = BookshelfControllerTest.VALID_AUTHOR
    )


    /**
     * 検索処理のテスト.
     */
    @Test
    fun getSearch_画面表示() {
        // 画面表示のみ
        mockMvc.perform(get("/book/search"))
            .andExpect(status().isOk)
            .andExpect(view().name(BookshelfControllerTest.PAGE_SEARCH))
    }

    @Test
    fun getSearch_検索あり() {
        // 戻り値の定義
        given(bookshelfService.findByAuthor(BookshelfControllerTest.VALID_AUTHOR))
            .willReturn(testBookList)

        // 検索処理あり
        mockMvc.perform(get("/book/search?authorName=" + BookshelfControllerTest.VALID_AUTHOR))
            .andExpect(status().isOk)
            .andExpect(model().attribute("books", testBookList))
            .andExpect(view().name(BookshelfControllerTest.PAGE_SEARCH))
    }


    /**
     * 一覧表示のテスト.
     */
    @Test
    fun getBookList_画面表示() {
        // 戻り値の定義
        given(bookshelfService.findAll())
            .willReturn(testBookList)

        // 一覧表示のテスト
        mockMvc.perform(get("/book/list"))
            .andExpect(status().isOk)
            .andExpect(model().attribute("books", testBookList))
            .andExpect(view().name(BookshelfControllerTest.PAGE_LIST))
    }

    /**
     * 新規登録のテスト.
     */
    @Test
    fun getBookNew_画面表示() {
        // 登録画面表示のテスト
        mockMvc.perform(get("/book/new"))
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(bookName = null, authorName = null)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))
    }

    @Test
    fun postBookNew_正常系() {
        // 戻り値の定義(登録成功)
        given(bookshelfService.save(testFormBook))
            .willReturn(1)

        // 登録画面表示のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "message",
                    Message(type = "info", message = BookshelfControllerTest.SAVE_SUCCESS_MESSAGE)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_LIST))
    }

    @Test
    fun postBookNew_異常系_登録失敗() {
        // 戻り値の定義(登録失敗)
        given(bookshelfService.save(testFormBook))
            .willReturn(0)

        // 件数0の時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "message",
                    Message(type = "error", message = BookshelfControllerTest.SAVE_FAILED_MESSAGE)
                )
            )
            .andExpect(model().attribute("formBook", testFormBook))
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))
    }

    @Test
    fun postBookNew_異常系_バリデーション() {
        // 書籍名が空、著者名が正常の時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.EMPTY)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(
                        bookName = BookshelfControllerTest.EMPTY,
                        authorName = BookshelfControllerTest.VALID_AUTHOR
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))

        // 書籍名が正常、著者名が空の時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.EMPTY)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(bookName = BookshelfControllerTest.VALID_BOOK, authorName = BookshelfControllerTest.EMPTY)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))

        // 書籍名、著者名が空の時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.EMPTY)
                .param("authorName", BookshelfControllerTest.EMPTY)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(bookName = BookshelfControllerTest.EMPTY, authorName = BookshelfControllerTest.EMPTY)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))

        // 書籍名が文字オーバー、著者名が正常の時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.OVER255)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(
                        bookName = BookshelfControllerTest.OVER255,
                        authorName = BookshelfControllerTest.VALID_AUTHOR
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))

        // 書籍名が正常、著者名が文字オーバーの時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.OVER60)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(bookName = BookshelfControllerTest.VALID_BOOK, authorName = BookshelfControllerTest.OVER60)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))

        // 書籍名、著者名が文字オーバーの時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.OVER255)
                .param("authorName", BookshelfControllerTest.OVER60)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(bookName = BookshelfControllerTest.OVER255, authorName = BookshelfControllerTest.OVER60)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))

        // 書籍名が空、著者名が文字オーバーの時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.EMPTY)
                .param("authorName", BookshelfControllerTest.OVER60)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(bookName = BookshelfControllerTest.EMPTY, authorName = BookshelfControllerTest.OVER60)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))

        // 書籍名が文字オーバー、著者名が空の時のテスト
        mockMvc.perform(
            post("/book/new").param("bookName", BookshelfControllerTest.OVER255)
                .param("authorName", BookshelfControllerTest.EMPTY)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "formBook",
                    FormBook(bookName = BookshelfControllerTest.OVER255, authorName = BookshelfControllerTest.EMPTY)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_NEW))
    }

    /**
     * 更新処理のテスト.
     */
    @Test
    fun getBookUpdate_正常系() {
        // 戻り値の定義(登録成功)
        given(bookshelfService.findByUuid(BookshelfControllerTest.VALID_UUID))
            .willReturn(testBook)

        // 件数0の時のテスト
        mockMvc.perform(get("/book/update/" + BookshelfControllerTest.VALID_UUID))
            .andExpect(status().isOk)
            .andExpect(model().attribute("book", testBook))
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))
    }

    @Test
    fun getBookUpdate_異常系_取得失敗() {
        // 戻り値の定義(登録成功)
        given(bookshelfService.findByUuid(BookshelfControllerTest.VALID_UUID))
            .willReturn(null)

        // 件数0の時のテスト
        mockMvc.perform(get("/book/update/" + BookshelfControllerTest.VALID_UUID))
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "message",
                    Message(type = "error", message = BookshelfControllerTest.GETBOOK_FAILED_MESSAGE)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_LIST))
    }

    @Test
    fun postBookUpdate_正常系() {
        // 戻り値の定義(登録成功)
        given(bookshelfService.update(testBook))
            .willReturn(1)

        // 更新成功時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "message",
                    Message(type = "info", message = BookshelfControllerTest.UPDATE_SUCCESS_MESSAGE)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_LIST))
    }

    @Test
    fun postBookUpdate_異常系_登録失敗() {
        // 戻り値の定義(登録失敗)
        given(bookshelfService.update(testBook))
            .willReturn(0)

        // 更新失敗時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "message",
                    Message(type = "error", message = BookshelfControllerTest.UPDATE_FAILED_MESSAGE)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))
    }

    @Test
    fun postBookUpdate_異常系_バリデーション() {
        // 書籍名が空、著者名が正常の時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.EMPTY)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.EMPTY,
                        authorName = BookshelfControllerTest.VALID_AUTHOR
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))

        // 書籍名が正常、著者名が空の時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.EMPTY)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.VALID_BOOK,
                        authorName = BookshelfControllerTest.EMPTY
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))

        // 書籍名、著者名が空の時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.EMPTY)
                .param("authorName", BookshelfControllerTest.EMPTY)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.EMPTY,
                        authorName = BookshelfControllerTest.EMPTY
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))

        // 書籍名が文字オーバー、著者名が正常の時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.OVER255)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.OVER255,
                        authorName = BookshelfControllerTest.VALID_AUTHOR
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))

        // 書籍名が正常、著者名が文字オーバーの時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.OVER60)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.VALID_BOOK,
                        authorName = BookshelfControllerTest.OVER60
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))

        // 書籍名、著者名が文字オーバーの時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.OVER60)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.VALID_BOOK,
                        authorName = BookshelfControllerTest.OVER60
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))

        // 書籍名が空、著者名が文字オーバーの時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.EMPTY)
                .param("authorName", BookshelfControllerTest.OVER60)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.EMPTY,
                        authorName = BookshelfControllerTest.OVER60
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))

        // 書籍名が文字オーバー、著者名が空の時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.VALID_UUID)
                .param("bookName", BookshelfControllerTest.OVER255)
                .param("authorName", BookshelfControllerTest.EMPTY)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.VALID_UUID,
                        bookName = BookshelfControllerTest.OVER255,
                        authorName = BookshelfControllerTest.EMPTY
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))


        // UUIDが空の時のテスト
        mockMvc.perform(
            post("/book/update").param("uuid", BookshelfControllerTest.EMPTY)
                .param("bookName", BookshelfControllerTest.VALID_BOOK)
                .param("authorName", BookshelfControllerTest.VALID_AUTHOR)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "book",
                    Book(
                        uuid = BookshelfControllerTest.EMPTY,
                        bookName = BookshelfControllerTest.VALID_BOOK,
                        authorName = BookshelfControllerTest.VALID_AUTHOR
                    )
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_UPDATE))
    }

    /**
     * 削除処理のテスト.
     */
    @Test
    fun postBookDelete_正常系() {
        // 戻り値の定義(登録成功)
        given(bookshelfService.delete(BookshelfControllerTest.VALID_UUID))
            .willReturn(1)

        // 件数0の時のテスト
        mockMvc.perform(
            post("/book/delete/" + BookshelfControllerTest.VALID_UUID)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "message",
                    Message(type = "info", message = BookshelfControllerTest.DELETE_SUCCESS_MESSAGE)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_LIST))
    }

    @Test
    fun postBookDelete_異常系_登録失敗() {
        // 戻り値の定義(登録成功)
        given(bookshelfService.delete(BookshelfControllerTest.VALID_UUID))
            .willReturn(0)

        // 件数0の時のテスト
        mockMvc.perform(
            post("/book/delete/" + BookshelfControllerTest.VALID_UUID)
        )
            .andExpect(status().isOk)
            .andExpect(
                model().attribute(
                    "message",
                    Message(type = "error", message = BookshelfControllerTest.DELETE_FAILED_MESSAGE)
                )
            )
            .andExpect(view().name(BookshelfControllerTest.PAGE_LIST))
    }
}
