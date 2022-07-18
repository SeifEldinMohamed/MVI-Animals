package com.seif.mvianimals.api

import com.seif.mvianimals.model.Animal
import retrofit2.http.GET

interface AnimalApi {
    @GET("animals.json")
    suspend fun getAnimals():List<Animal>
}