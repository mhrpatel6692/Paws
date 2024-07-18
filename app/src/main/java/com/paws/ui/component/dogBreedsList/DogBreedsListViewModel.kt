package com.paws.ui.component.dogBreedsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paws.data.remote.service.DogBreedsApi
import com.paws.domain.model.DogBreed
import com.paws.domain.usecase.dogBreeds.DogBreedsUseCase
import com.paws.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.paws.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogBreedsListViewModel @Inject constructor(
    private val dogBreedsUseCase: DogBreedsUseCase,
    private val favouriteDogBreedsUseCase: FavouriteDogBreedsUseCase
) :
    BaseViewModel() {

    private val _pawState = MutableLiveData<PawState>()

    val pawState: LiveData<PawState>
        get() = _pawState

    init {
        addSubscription(fetchDogBreeds())
    }

    fun refresh() {
        fetchDogBreeds()
    }

    private fun fetchDogBreeds(): Disposable =
        dogBreedsUseCase.getDogBreeds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _pawState.value = PawState.Success(it) },
                { _pawState.value = PawState.Error }
            )

    fun addToFavourites(dogName: String, isFavourite: Boolean) {
        viewModelScope.launch {
            favouriteDogBreedsUseCase.addToFavourites(name = dogName, isFavourite = isFavourite)
        }
    }

    sealed interface PawState {
        data class Success(val breeds: List<DogBreed>) : PawState
        object Error : PawState
    }
}