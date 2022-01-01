package com.library.bookshelf_searcher.domain.model

/**
 * Bookクラス.
 * */
data class Book (
    val id: Int?,   // ID
    val uuid: String?,  // UUID
    val bookName: String?,  // タイトル
    val authorName: String?  // 著者名
    )