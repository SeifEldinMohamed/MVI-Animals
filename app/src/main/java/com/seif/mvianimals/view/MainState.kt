package com.seif.mvianimals.view

import com.seif.mvianimals.model.Animal

sealed class MainState {
    object Idle : MainIntent()
    object Loading: MainIntent()
    data class Animals(val animals: List<Animal>): MainIntent()
    data class Error(val error: String): MainIntent()
}