package com.paws.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paws.R
import com.paws.databinding.ActivityMainBinding
import com.paws.domain.model.DogBreed
import com.paws.ui.base.BaseActivity
import com.paws.ui.component.dogBreedsList.BreedsAdapter
import com.paws.ui.component.dogBreedsList.DogBreedsListViewModel
import com.paws.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    internal lateinit var adapter: BreedsAdapter

    private lateinit var binding: ActivityMainBinding
    private val dogBreedListViewModel by viewModels<DogBreedsListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = linearLayoutManager
        binding.recycler.adapter = adapter
        adapter.setOnFavoriteClick { breedName, isFavorite ->
            dogBreedListViewModel.addToFavourites(breedName, isFavorite)
        }
        adapter.setOnRootClick { breedName ->
            val intent = Intent(this, DogBreedDetailsActivity::class.java)
            intent.putExtra(Constants.KEY_BREED_NAME, breedName)
            startActivity(intent)
        }

        dogBreedListViewModel.pawState.observe(this, ::updateState)
    }

    private fun updateState(pawState: DogBreedsListViewModel.PawState) {
        when (pawState) {
            is DogBreedsListViewModel.PawState.Success -> displayResults(pawState.breeds)
            DogBreedsListViewModel.PawState.Error -> displayError()
        }
    }

    private fun displayResults(results: List<DogBreed>) {
        adapter.submitList(results)
    }

    private fun displayError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show()
    }
}