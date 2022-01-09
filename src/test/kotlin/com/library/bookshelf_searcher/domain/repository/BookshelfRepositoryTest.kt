package com.library.bookshelf_searcher.domain.repository

import com.library.bookshelf_searcher.domain.repository.db.tables.pojos.Bookshelf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class BookshelfRepositoryTest {

    @Autowired
    lateinit var bookshelfRepository: BookshelfRepository

    @Test
    fun save_findAll() {
        val savedCount = bookshelfRepository.save(
            Bookshelf(
                uuid = "5f5a9e67-babf-4c63-b561-7d978de8923e",
                bookName = "book",
                authorName = "author"
            )
        )
        /** 新規作成件数が1件であることの確認. */
        assertEquals(savedCount, 1)

        /** 全件取得で1件だけ取れることの確認. */
        val bookshelfList = bookshelfRepository.findAll()
        assertEquals(bookshelfList.size, 1)

        /** 新規作成の中身確認 */
        val savedBookshelf = bookshelfList[0]
        assertEquals(savedBookshelf.uuid, "5f5a9e67-babf-4c63-b561-7d978de8923e")
        assertEquals(savedBookshelf.bookName, "book")
        assertEquals(savedBookshelf.authorName, "author")
        assertEquals(savedBookshelf.createdAt.toString().isNotEmpty(), true)
        assertEquals(savedBookshelf.updatedAt.toString().isNotEmpty(), true)
        assertEquals(savedBookshelf.createdBy, "create_user")
        assertEquals(savedBookshelf.updatedBy, "create_user")
        assertEquals(savedBookshelf.deleteStatus, 0)
    }

    @Test
    fun findByAuthor_findAll() {
        // 1件目
        bookshelfRepository.save(
            Bookshelf(
                uuid = "e95b13db-b91f-4f50-abe2-5062d63b7ee5",
                bookName = "book1",
                authorName = "first_author"
            )
        )

        // 2件目
        bookshelfRepository.save(
            Bookshelf(
                uuid = "76bd06f1-bf2b-44aa-9e67-0368e8822c9a",
                bookName = "book2",
                authorName = "first_author"
            )
        )

        // 3件目
        bookshelfRepository.save(
            Bookshelf(
                uuid = "8c0c76f5-88ca-4a2c-ab45-2ee76bd8443f",
                bookName = "book3",
                authorName = "second_author"
            )
        )

        /** 3件登録されていることの確認. */
        assertEquals(bookshelfRepository.findAll(), 3)

        /** 著者名「first_author」で検索し、2剣のみ取れることの確認. */
        val authorBookshelfList = bookshelfRepository.findByAuthor("first_author")
        assertEquals(authorBookshelfList.size, 2)

        /** 検索内容確認(1件目). */
        assertEquals(authorBookshelfList[0].uuid, "e95b13db-b91f-4f50-abe2-5062d63b7ee5")
        assertEquals(authorBookshelfList[0].bookName, "book1")
        assertEquals(authorBookshelfList[0].authorName, "first_author")

        /** 検索内容確認(2件目). */
        assertEquals(authorBookshelfList[1].uuid, "76bd06f1-bf2b-44aa-9e67-0368e8822c9a")
        assertEquals(authorBookshelfList[1].bookName, "book2")
        assertEquals(authorBookshelfList[1].authorName, "first_author")

    }

    @Test
    fun findByUuid_update() {
        // レコードの作成
        bookshelfRepository.save(
            Bookshelf(
                uuid = "7bdacbaf-b247-42c0-bcd1-87117c6c6ad5",
                bookName = "book",
                authorName = "author"
            )
        )

        /** uuidで検索した際に取得できることの確認. */
        val beforeUpdateBook = bookshelfRepository.findByUuid("7bdacbaf-b247-42c0-bcd1-87117c6c6ad5")
        assertEquals(beforeUpdateBook?.uuid, "7bdacbaf-b247-42c0-bcd1-87117c6c6ad5")
        assertEquals(beforeUpdateBook?.bookName, "book")
        assertEquals(beforeUpdateBook?.authorName, "author")

        // レコードの更新
        val update_count = bookshelfRepository.update(
            Bookshelf(
                uuid = "7bdacbaf-b247-42c0-bcd1-87117c6c6ad5",
                bookName = "book_updated",
                authorName = "author_updated"
            )
        )

        /** 更新件数の確認. */
        assertEquals(update_count, 1)

        /** 更新されていることの確認. */
        val afterUpdateBook = bookshelfRepository.findByUuid("7bdacbaf-b247-42c0-bcd1-87117c6c6ad5")
        assertEquals(afterUpdateBook?.uuid, "7bdacbaf-b247-42c0-bcd1-87117c6c6ad5")
        assertEquals(afterUpdateBook?.bookName, "book_updated")
        assertEquals(afterUpdateBook?.authorName, "author_updated")
        assertEquals(afterUpdateBook?.updatedBy, "update_user")
        assertEquals(beforeUpdateBook?.updatedAt != afterUpdateBook?.updatedAt, true)
    }

    @Test
    fun deleteByUuid() {
        // レコードの作成
        bookshelfRepository.save(
            Bookshelf(
                uuid = "a9d4e608-61ff-4533-8f28-27e687f16992",
                bookName = "book",
                authorName = "author"
            )
        )

        /** 1件取得できることの確認 */
        assertEquals(bookshelfRepository.findAll().size, 1)

        /** 1件削除されることの確認 */
        assertEquals(bookshelfRepository.deleteByUuid("a9d4e608-61ff-4533-8f28-27e687f16992"), 1)

        /** 1件も取得できないことの確認 */
        assertEquals(bookshelfRepository.findAll().size, 0)
    }
}
