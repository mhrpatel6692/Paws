package com.paws.ui.component.dogBreedImages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.paws.databinding.ItemDogImageBinding
import javax.inject.Inject

class BreedImagesAdapter @Inject constructor(
    private val requestManager: RequestManager
) :
    ListAdapter<String, BreedImagesAdapter.Holder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    private var onFavoriteClick: ((String, Boolean) -> Unit)? = null
    private var onSaveClick: ((String) -> Unit)? = null

    fun setOnFavoriteClick(onClick: (String, Boolean) -> Unit) {
        this.onFavoriteClick = onClick
    }

    fun setOnDownloadClick(onClick: (String) -> Unit) {
        this.onSaveClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemDogImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dogBreed = getItem(position)
        holder.bind(dogBreed)
    }

    override fun getItemId(position: Int) = getItem(position).hashCode().toLong()

    inner class Holder(private val itemDogBreeadBinding: ItemDogImageBinding) :
        RecyclerView.ViewHolder(itemDogBreeadBinding.root) {
        fun bind(item: String) {
            requestManager.load(item).into(itemDogBreeadBinding.imageDog)
            itemDogBreeadBinding.imageSave.setOnClickListener {
                onSaveClick?.invoke(item)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(old: String, new: String) = old == new
            override fun areContentsTheSame(old: String, new: String) = old == new
        }
    }
}
