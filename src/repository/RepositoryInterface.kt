package repository

import com.mongodb.client.MongoCollection
import com.ranggacikal.models.Model
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

interface RepositoryInterface<T> {
    var collection: MongoCollection<T>
    fun getById(id: String): T {
        return try {
            collection.findOne(Model::id eq id) ?: throw Exception("No item with that ID exists")
        } catch (t: Throwable) {
            throw  Exception("cannot find item")
        }
    }
    fun getAll(): List<T> {
        return try {
            val res = collection.find()
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("cannot get all items")
        }
    }
    fun delete(id: String): Boolean {
        return try {
            collection.findOneAndDelete(Model::id eq id) ?: throw Exception("No item with that ID")
            true
        } catch (t: Throwable) {
            throw Exception("Cannot Delete Item")
        }
    }
    fun add(entry: T): T {
        return try {
            collection.insertOne(entry)
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot Add Item")
        }
    }
    fun update(entry: Model): T {
        return try {
            collection.updateOne(Model::id eq entry.id, entry)
            collection.findOne(Model::id eq entry.id) ?: throw Exception("No item with that ID")
        } catch (t: Throwable) {
            throw Exception("cannot update item")
        }
    }
}