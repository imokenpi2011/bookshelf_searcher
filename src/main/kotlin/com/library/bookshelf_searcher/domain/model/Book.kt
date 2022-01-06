package com.library.bookshelf_searcher.domain.model

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * Bookクラス.
 * */
data class Book(
    @field:[NotBlank]
    var uuid: String,  // uuid

    @field:[NotBlank Size(max = 255)]
    val bookName: String,  // タイトル

    @field:[NotBlank Size(max = 60)]
    val authorName: String  // 著者名
)

/**
 * FormBookクラス.
 * */
data class FormBook(
    @field:[NotBlank Size(max = 255)]
    val bookName: String?,  // タイトル

    @field:[NotBlank Size(max = 60)]
    val authorName: String?  // 著者名
)
