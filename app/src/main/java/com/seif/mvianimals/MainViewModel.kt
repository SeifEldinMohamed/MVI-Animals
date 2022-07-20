package com.seif.mvianimals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.mvianimals.api.AnimalRepository
import com.seif.mvianimals.view.MainIntent
import com.seif.mvianimals.view.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(val repo: AnimalRepository): ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    init {
        processIntent()
    }
    private fun processIntent(){
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect{ collector ->
                when(collector){
                    is MainIntent.FetchAnimals -> fetchAnimals()
                }
            }
        }
    }

    private fun fetchAnimals() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            try {
                val animals =   repo.getAnimals()
                _state.value = MainState.Animals(animals)
            }catch (e: Exception){
                _state.value = MainState.Error(e.message!!)
            }
        }
    }
}