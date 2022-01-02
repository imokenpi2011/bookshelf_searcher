package com.library.bookshelf_searcher.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BookshelfController {

    // 検索画面
    @GetMapping("book/search")
    fun getTopPage(): String {
        return "bookshelf/index"
    }

}
