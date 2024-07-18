package com.paws.di

import com.paws.ui.component.dogBreedImages.DogBreedDetailsViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface DogBreedDetailsViewModelAssistedFactory {
    fun create(dogBreed: String): DogBreedDetailsViewModel
}