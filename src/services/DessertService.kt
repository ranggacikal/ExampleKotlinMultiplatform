package services


import com.mongodb.client.MongoClient
import com.ranggacikal.models.Dessert
import com.ranggacikal.models.DessertInput
import com.ranggacikal.models.DessertsPage
import org.koin.core.KoinComponent
import org.koin.core.inject
import repository.DessertRepository
import java.util.UUID

class DessertService: KoinComponent {
    private val client: MongoClient by inject()
    private val repo: DessertRepository = DessertRepository(client)

    fun getDessertPage(page: Int, size: Int): DessertsPage {
        return repo.getDessertPage(page, size)
    }

    fun getDessert(id: String): Dessert {
        return repo.getById(id)
    }

    fun createDessert(dessertInput: DessertInput, userId: String): Dessert {
        val uid = UUID.randomUUID().toString()
        val dessert = Dessert(
            id = uid,
            userId = userId,
            name = dessertInput.name,
            description = dessertInput.description,
            imageUrl = dessertInput.imageUrl
        )
        return repo.add(dessert)
    }

    fun updateDessert(userId: String, dessertId: String, dessertInput: DessertInput): Dessert {
        val dessert = repo.getById(dessertId)
        if (dessert.userId == userId) {
            val updateDessert = Dessert(
                id = dessertId,
                userId = userId,
                name = dessertInput.name,
                description = dessertInput.description,
                imageUrl = dessertInput.imageUrl
            )
            return repo.update(updateDessert)
        }
        error("Cannot Update Dessert")
    }

    fun deleteDessert(userId: String, dessertId: String): Boolean {
        val dessert = repo.getById(dessertId)
        if (dessert.userId == userId) return repo.delete(dessertId)
        error("Cannot Delete dessert")
    }
}