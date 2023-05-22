package repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.ranggacikal.models.Dessert
import com.ranggacikal.models.DessertsPage
import com.ranggacikal.models.PagingInfo
import org.litote.kmongo.getCollection

class DessertRepository(client: MongoClient): RepositoryInterface<Dessert> {
    override lateinit var collection: MongoCollection<Dessert>
    init {
        val database = client.getDatabase("test")
        collection = database.getCollection<Dessert>("Dessert")
    }

    fun getDessertPage(page: Int, size: Int): DessertsPage {
        try {
            val skips = page * size
            val res = collection.find().skip(skips).limit(size)
            val results = res.asIterable().map { it }
            val totalDesserts = collection.estimatedDocumentCount()
            val totalPages = (totalDesserts / size) + 1
            val next = if(results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(totalDesserts.toInt(), totalPages.toInt(), next, prev)
            return DessertsPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot Get Dessert Page")
        }
    }
}