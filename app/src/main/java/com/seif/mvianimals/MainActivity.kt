package com.seif.mvianimals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seif.mvianimals.api.AnimalApi
import com.seif.mvianimals.api.AnimalService
import com.seif.mvianimals.databinding.ActivityMainBinding
import com.seif.mvianimals.view.AnimalAdapter
import com.seif.mvianimals.view.MainIntent
import com.seif.mvianimals.view.MainState
import com.seif.mvianimals.view.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    private val animalAdapter by lazy { AnimalAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        setUpObservables()
    }

    private fun setUpUI() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(AnimalService.api))[MainViewModel::class.java]
        binding.rvAnimals.layoutManager = LinearLayoutManager(this)
        binding.rvAnimals.run {
            addItemDecoration(
                DividerItemDecoration(
                    binding.rvAnimals.context,
                    (binding.rvAnimals.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        binding.rvAnimals.adapter = animalAdapter
        binding.btnFetchAnimals.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchAnimals)
            }
        }
    }

    private fun setUpObservables() {
        lifecycleScope.launch {
            mainViewModel.state.collect { collector ->
                when (collector) {
                    is MainState.Idle -> {}
                    is MainState.Loading -> {
                        binding.btnFetchAnimals.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Animals -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnFetchAnimals.visibility = View.GONE
                        binding.rvAnimals.visibility = View.VISIBLE
                        collector.animals.let {
                            animalAdapter.addAnimalsList(it)
                        }
                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnFetchAnimals.visibility = View.GONE
                        Toast.makeText(this@MainActivity, collector.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}