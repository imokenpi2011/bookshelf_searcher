package com.library.bookshelf_searcher.app.controller

import com.library.bookshelf_searcher.domain.model.Book
import com.library.bookshelf_searcher.domain.model.FormBook
import com.library.bookshelf_searcher.domain.service.BookshelfService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

/**
 * Bookshelfコントローラクラス.
 */
@Controller
class BookshelfController(private val bookshelfService: BookshelfService) {

    // 検索画面
    @GetMapping("/book/search")
    fun getSearchPage(model: Model): String {
        model.addAttribute("formBook", FormBook(uuid = "", bookName = "", authorName = ""))
        return "bookshelf/index"
    }

    // 検索画面
    @PostMapping("/book/search")
    fun postSearchPage(@ModelAttribute formBook: FormBook, model: Model): String {
        println("著者名" + formBook.authorName)
        val authorName: String = formBook.authorName.toString()
        val books: List<Book> = bookshelfService.findByAuthor(authorName)
        model.addAttribute("books", books)
        return "bookshelf/index"
    }

    // 書籍一覧表示画面
    @GetMapping("/book/list")
    fun getBookList(model: Model): String {
        val books: List<Book> = bookshelfService.findAll()
        model.addAttribute("books", books)
        return "bookshelf/bookList"
    }

}
