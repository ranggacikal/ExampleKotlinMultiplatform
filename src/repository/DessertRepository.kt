package repository

import com.ranggacikal.models.Dessert
import data.desserts
import org.litote.kmongo.util.idValue

class DessertRepository: RepositoryInterface<Dessert> {
    override fun getById(id: String): Dessert {
        return try {
            desserts.find { it.id == id } ?: throw Exception("no dessert with that ID exists")
        } catch (e: Exception) {
            throw Exception("Cannot find dessert")
        }
    }

    override fun getAll(): List<Dessert> {
        return desserts
    }

    override fun delete(id: String): Boolean {
        return try {
            val dessert = desserts.find { it.id == id } ?: throw Exception("no dessert with that ID Exists")
            desserts.remove(dessert)
            true
        } catch (e: Exception) {
            throw Exception("Cannot find dessert")
        }
    }

    override fun add(entry: Dessert): Dessert {
        desserts.add(entry)
        return entry
    }

    override fun update(entry: Dessert): Dessert {
        return try {
            val dessert = desserts.find { it.id == entry.id }?.apply {
                name = entry.name
                description = entry.description
                imageUrl = entry.imageUrl
            }?: throw Exception("No desserts with that ID exists")
            dessert
        } catch (e: Exception) {
            throw Exception("Cannot find dessert")
        }
    }
}