package com.library.bookshelf_searcher.app.controller

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.model.FormBook
import com.library.bookshelf_searcher.domain.model.Message
import com.library.bookshelf_searcher.domain.service.BookshelfService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Bookshelfコントローラクラス.
 */
@Controller
class BookshelfController(private val bookshelfService: BookshelfService) {

    /** 検索画面. */
    @GetMapping("/book/search")
    fun getSearchPage(
        @RequestParam(required = false) authorName: String?, @ModelAttribute formBook: FormBook, model: Model
    ): String {
        // パラメータ指定時のみ検索処理実行
        if (authorName != null) {
            // 検索処理を実行
            val books: List<Book> = bookshelfService.findByAuthor(authorName)
            model.addAttribute("books", books)
        }

        // 検索画面に遷移
        model.addAttribute("formBook", formBook)
        return "bookshelf/index"
    }

    /** 書籍一覧表示画面. */
    @GetMapping("/book/list")
    fun getBookList(model: Model): String {
        // 書籍一覧を取得
        val books: List<Book> = bookshelfService.findAll()
        
        // 一覧画面に遷移
        model.addAttribute("books", books)
        return "bookshelf/bookList"
    }

    /** 書籍登録画面. */
    @GetMapping("/book/new")
    fun getBookNew(@ModelAttribute formBook: FormBook, model: Model): String {
        // 登録画面に遷移
        model.addAttribute("formBook", formBook)
        return "bookshelf/bookNew"
    }

    /** 書籍登録処理. */
    @PostMapping("/book/new")
    fun postBookNew(@ModelAttribute @Validated formBook: FormBook, bindingResult: BindingResult, model: Model): String {
        // バリデーションエラーを確認
        if (bindingResult.hasErrors()) {
            var errorMsg = bindingResult.allErrors.map { it.defaultMessage }.joinToString(separator = "\n")
            model.addAttribute("message", Message(type = "error", message = errorMsg))
            model.addAttribute("formBook", formBook)
            return "bookshelf/bookNew"
        }

        // 登録処理
        if (bookshelfService.save(formBook) == 0) {
            // 登録件数が0だった場合は登録画面に遷移
            model.addAttribute("message", Message(type = "error", message = "書籍情報の登録に失敗しました。"))
            model.addAttribute("formBook", formBook)
            return "bookshelf/bookNew"
        }

        // 一覧画面に遷移
        model.addAttribute("message", Message(type = "info", message = "書籍情報を登録しました。"))
        return getBookList(model)
    }

    /** 書籍更新画面. */
    @GetMapping("/book/update/{uuid}")
    fun getBookUpdate(@PathVariable uuid: String, model: Model): String {
        // 書籍情報を取得. 失敗した場合は一覧画面に遷移.
        val book: Book? = bookshelfService.findByUuid(uuid)

        // 結果チェック
        book?.let {
            // 書籍が取得できた場合は更新画面に遷移
            model.addAttribute("book", book)
            return "bookshelf/bookUpdate"
        } ?: run {
            // 書籍が取得できない場合は一覧画面に遷移する
            model.addAttribute("message", Message(type = "error", message = "書籍情報の取得に失敗しました。"))
            return getBookList(model)
        }


    }

    /** 書籍更新処理. */
    @PostMapping("/book/update")
    fun postBookUpdate(@ModelAttribute @Validated book: Book, bindingResult: BindingResult, model: Model): String {
        // バリデーションエラーを確認
        if (bindingResult.hasErrors()) {
            var errorMsg = bindingResult.allErrors.map { it.defaultMessage }.joinToString(separator = "\n")
            model.addAttribute("message", Message(type = "error", message = errorMsg))
            model.addAttribute("book", book)
            return "bookshelf/bookUpdate"
        }

        // 更新処理
        if (bookshelfService.update(book) == 0) {
            // 更新件数が0だった場合は更新画面に遷移
            model.addAttribute("message", Message(type = "error", message = "書籍情報の更新に失敗しました。"))
            model.addAttribute("book", book)
            return "bookshelf/bookUpdate"
        }

        // 更新が成功したら一覧画面に遷移
        model.addAttribute("message", Message(type = "info", message = "書籍情報を更新しました。"))
        return getBookList(model)
    }

    /** 書籍削除処理 */
    @PostMapping("/book/delete/{uuid}")
    fun postBookDelete(@PathVariable uuid: String, model: Model): String {
        // 削除処理
        if (bookshelfService.delete(uuid) == 0) {
            // 削除に失敗した場合
            model.addAttribute("message", Message(type = "error", message = "書籍情報の削除に失敗しました。"))
        } else {
            // 削除に成功した場合
            model.addAttribute("message", Message(type = "info", message = "書籍情報を削除しました。"))
        }

        // 削除が成功したら一覧画面に遷移
        return getBookList(model)
    }

}
