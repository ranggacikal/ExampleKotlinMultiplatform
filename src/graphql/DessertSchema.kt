package graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.apurebase.kgraphql.schema.dsl.types.InputTypeDSL
import com.ranggacikal.models.Dessert
import com.ranggacikal.models.DessertInput
import repository.DessertRepository

fun SchemaBuilder.dessertSchema() {
    val repository = DessertRepository()

    inputType<DessertInput> {
        description = "The Input of the dessert without the identifier"
    }

    type<Dessert> {
        description = "Dessert object with atribute of name, description, imageUrl"
    }

    query("dessert"){
        resolver { dessertId: String ->
            try {
                repository.getById(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("desserts") {
        resolver { ->
            try {
                repository.getAll()
            } catch (e: Exception) {
                emptyList<Dessert>()
            }
        }
    }

    mutation("createDessert") {
        description = "create a new dessert"
        resolver { dessertInput: DessertInput ->
            try {
                val uid = java.util.UUID.randomUUID().toString()
                val dessert = Dessert(uid, dessertInput.name, dessertInput.description, dessertInput.imageUrl)
                repository.add(dessert)
                dessert
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateDessert") {
        resolver { dessertId: String, dessertInput: DessertInput ->
            try {
                val dessert = Dessert(dessertId, dessertInput.name, dessertInput.description, dessertInput.imageUrl)
                repository.update(dessert)
                dessert
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteDessert") {
        resolver { dessertId: String ->
            try {
                repository.delete(dessertId)
                true
            } catch (e: Exception) {
                null
            }
        }
    }
}