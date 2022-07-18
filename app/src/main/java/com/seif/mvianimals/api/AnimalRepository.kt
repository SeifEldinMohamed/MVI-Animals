package com.seif.mvianimals.api

class AnimalRepository(private val api: AnimalApi) {
    suspend fun getAnimals() = api.getAnimals()
}