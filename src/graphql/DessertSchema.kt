package graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.apurebase.kgraphql.schema.dsl.types.InputTypeDSL
import com.ranggacikal.models.Dessert
import com.ranggacikal.models.DessertInput
import repository.DessertRepository
import services.DessertService

fun SchemaBuilder.dessertSchema(dessertService: DessertService) {

    inputType<DessertInput> {
        description = "The Input of the dessert without the identifier"
    }

    type<Dessert> {
        description = "Dessert object with atribute of name, description, imageUrl"
    }

    query("dessert"){
        resolver { dessertId: String ->
            try {
                dessertService.getDessert(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("desserts"){
        resolver { page: Int?, size: Int? ->
            try {
                dessertService.getDessertPage(page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createDessert") {
        description = "create a new dessert"
        resolver { dessertInput: DessertInput ->
            try {
                val userId = "abc"
                dessertService.createDessert(dessertInput, userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateDessert") {
        resolver { dessertId: String, dessertInput: DessertInput ->
            try {
               val userId = "abc"
               dessertService.updateDessert(userId, dessertId, dessertInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteDessert") {
        resolver { dessertId: String ->
            try {
                val userId = "abc"
                dessertService.deleteDessert(userId, dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }
}