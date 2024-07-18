package com.paws.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DogBreedDetailsViewModelFactory(
    private val breedName: String?,
    private val assistedFactory: DogBreedDetailsViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(breedName!!) as T
    }
}