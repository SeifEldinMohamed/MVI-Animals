package com.seif.mvianimals.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seif.mvianimals.api.AnimalService
import com.seif.mvianimals.databinding.AnimalsItemRowBinding
import com.seif.mvianimals.model.Animal

class AnimalAdapter(): RecyclerView.Adapter<AnimalAdapter.MyViewHolder>() {
    private var animals = emptyList<Animal>()
    class MyViewHolder(private val binding: AnimalsItemRowBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(animals: List<Animal>, position: Int){
            binding.tvAnimal.text = animals[position].name
            val url = AnimalService.BASE_URL + animals[position].image
            Glide.with(binding.imgAnimal.context)
                .load(url)
                .into(binding.imgAnimal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            AnimalsItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(animals, position)
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    fun addAnimalsList(animalsList:List<Animal>){
        this.animals = animalsList
        notifyDataSetChanged()
    }

}