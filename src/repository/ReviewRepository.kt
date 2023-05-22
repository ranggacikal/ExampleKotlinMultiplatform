package repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.ranggacikal.models.Review
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class ReviewRepository(client: MongoClient) : RepositoryInterface<Review> {

    override lateinit var collection: MongoCollection<Review>

    init {
        val database = client.getDatabase("test")
        collection = database.getCollection<Review>("Review")
    }

    //getReviewByDessertId
    fun getReviewsByDessertId(dessertId: String): List<Review> {
        return try {
            val res = collection.find(Review::dessertId eq dessertId)
                ?: throw Exception("No review with that dessert ID exists")
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot find reviews")
        }
    }
}