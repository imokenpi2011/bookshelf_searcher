package com.library.bookshelf_searcher.domain.model

/**
 * Messageクラス.
 * */
data class Message(
    var type: String,  // メッセージタイプ。[info=通常, error=エラー]

    val message: String,  // メッセージ内容

)
