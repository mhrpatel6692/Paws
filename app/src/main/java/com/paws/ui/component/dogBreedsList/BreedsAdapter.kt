package com.paws.ui.component.dogBreedsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.paws.R
import com.paws.data.remote.service.DogBreedsApi
import com.paws.databinding.ItemDogBreeadBinding
import com.paws.domain.model.DogBreed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BreedsAdapter @Inject constructor(
    private val requestManager: RequestManager,
    private val dogBreedsApi: DogBreedsApi
) :
    ListAdapter<DogBreed, BreedsAdapter.Holder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    private var onFavoriteClick: ((String, Boolean) -> Unit)? = null
    private var onRootClick: ((String) -> Unit)? = null

    fun setOnFavoriteClick(onClick: (String, Boolean) -> Unit) {
        this.onFavoriteClick = onClick
    }

    fun setOnRootClick(onClick: (String) -> Unit) {
        this.onRootClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemDogBreeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dogBreed = getItem(position)
        holder.bind(dogBreed)
    }

    override fun getItemId(position: Int) = getItem(position).hashCode().toLong()

    inner class Holder(private val itemDogBreeadBinding: ItemDogBreeadBinding) :
        RecyclerView.ViewHolder(itemDogBreeadBinding.root) {
        fun bind(item: DogBreed) {
            itemDogBreeadBinding.textBreed.text = item.name
            itemDogBreeadBinding.textSubBreed.text = item.subBreeds
            itemDogBreeadBinding.imageFavorite.setImageResource(
                if (item.isFavourite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )
            if (item.imageUrl.isEmpty()) {
                val ignore = dogBreedsApi.fetchDogBreedSingleImage(item.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ imageResponse ->
                        item.imageUrl = imageResponse.message
                        requestManager.load(item.imageUrl).into(itemDogBreeadBinding.imageDog)
                    }, {
                        // Handle error case
                        requestManager.load(R.drawable.ic_app_launcher)
                            .into(itemDogBreeadBinding.imageDog)
                    })
            } else {
                requestManager.load(item.imageUrl).into(itemDogBreeadBinding.imageDog)
            }
            itemDogBreeadBinding.imageFavorite.setOnClickListener {
                onFavoriteClick?.invoke(item.name, !item.isFavourite)
            }
            itemDogBreeadBinding.root.setOnClickListener {
                onRootClick?.invoke(item.name)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<DogBreed>() {
            override fun areItemsTheSame(old: DogBreed, new: DogBreed): Boolean {
                return old.name == new.name
            }

            override fun areContentsTheSame(old: DogBreed, new: DogBreed): Boolean {
                return old.isFavourite == new.isFavourite
            }
        }
    }
}
