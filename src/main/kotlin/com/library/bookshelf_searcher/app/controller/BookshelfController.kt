package com.library.bookshelf_searcher.app.controller

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.model.FormBook
import com.library.bookshelf_searcher.domain.service.BookshelfService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

/**
 * Bookshelfコントローラクラス.
 */
@Controller
class BookshelfController(private val bookshelfService: BookshelfService) {

    /** 検索画面. */
    @GetMapping("/book/search")
    fun getSearchPage(@ModelAttribute formBook: FormBook, model: Model): String {
        model.addAttribute("formBook", formBook)
        return "bookshelf/index"
    }

    /** 検索処理. */
    @PostMapping("/book/search")
    fun postSearchPage(@ModelAttribute formBook: FormBook, model: Model): String {
        val authorName: String = formBook.authorName.toString()
        val books: List<Book> = bookshelfService.findByAuthor(authorName)
        model.addAttribute("books", books)
        return "bookshelf/index"
    }

    /** 書籍一覧表示画面. */
    @GetMapping("/book/list")
    fun getBookList(model: Model): String {
        val books: List<Book> = bookshelfService.findAll()
        model.addAttribute("books", books)
        return "bookshelf/bookList"
    }

    /** 書籍登録画面. */
    @GetMapping("/book/new")
    fun getBookNew(@ModelAttribute formBook: FormBook, model: Model): String {
        model.addAttribute("formBook", formBook)
        return "bookshelf/bookNew"
    }

    /** 書籍登録処理. */
    @PostMapping("/book/new")
    fun postBookNew(@ModelAttribute @Validated formBook: FormBook, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            println(bindingResult.allErrors.map { it.defaultMessage }.joinToString(separator = "\n"))
            model.addAttribute("formBook", formBook)
            return "bookshelf/bookNew"
        }
        bookshelfService.save(formBook)
        return "redirect:/book/list"
    }

    /** 書籍更新画面. */
    @GetMapping("/book/update/{uuid}")
    fun getBookUpdate(@PathVariable uuid: String, model: Model): String {
        val book: Book? = bookshelfService.findByUuid(uuid)
        model.addAttribute("book", book)
        return "bookshelf/bookUpdate"
    }

    /** 書籍更新処理. */
    @PostMapping("/book/update")
    fun postBookUpdate(@ModelAttribute @Validated book: Book, bindingResult: BindingResult, model: Model): String {
        return getBookList(model)
    }

    /** 書籍削除処理 */
    @PostMapping("/book/delete/{uuid}")
    fun postBookDelete(@PathVariable uuid: String, model: Model): String {
        bookshelfService.delete(uuid)
        return getBookList(model)
    }

}
