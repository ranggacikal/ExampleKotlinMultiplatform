package repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.ranggacikal.models.Dessert
import org.litote.kmongo.getCollection

class DessertRepository(client: MongoClient): RepositoryInterface<Dessert> {
    override lateinit var collection: MongoCollection<Dessert>
    init {
        val database = client.getDatabase("test")
        collection = database.getCollection<Dessert>("Dessert")
    }
}