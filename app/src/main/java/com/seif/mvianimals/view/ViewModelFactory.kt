package com.seif.mvianimals.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seif.mvianimals.MainViewModel
import com.seif.mvianimals.api.AnimalApi
import com.seif.mvianimals.api.AnimalRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val api:AnimalApi): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(AnimalRepository(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}