package com.paws.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.paws.R
import com.paws.databinding.ActivityDogBreedDetailsBinding
import com.paws.ui.component.dogBreedImages.BreedImagesAdapter
import com.paws.ui.component.dogBreedImages.DogBreedDetailsViewModel
import com.paws.di.DogBreedDetailsViewModelAssistedFactory
import com.paws.di.DogBreedDetailsViewModelFactory
import com.paws.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DogBreedDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var dogBreedDetailViewModelFactory: DogBreedDetailsViewModelAssistedFactory

    @Inject
    internal lateinit var adapter: BreedImagesAdapter
    private lateinit var binding: ActivityDogBreedDetailsBinding
    private lateinit var dogBreedDetailsViewModel: DogBreedDetailsViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogBreedDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.listDogImages.layoutManager = gridLayoutManager
        binding.listDogImages.adapter = adapter
        adapter.setOnDownloadClick { url ->
            Glide.with(this).asBitmap().load(url).listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    val imgBitmapPath: String = MediaStore.Images.Media.insertImage(
                        contentResolver, resource,
                        "IMG:${System.currentTimeMillis()}", null
                    )
                    val imgBitmapUri = Uri.parse(imgBitmapPath)
                    // share Intent
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri)
                    shareIntent.type = "image/png"
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_powered_by))
                    // Open the chooser dialog box
                    startActivity(Intent.createChooser(shareIntent, "Share with"))
                    return true
                }
            }).submit()
        }
        val breedName = intent.extras?.getString(Constants.KEY_BREED_NAME)

        dogBreedDetailsViewModel = ViewModelProvider(
            this, DogBreedDetailsViewModelFactory(breedName, dogBreedDetailViewModelFactory)
        )[DogBreedDetailsViewModel::class.java]
        dogBreedDetailsViewModel.pawState.observe(this, ::updateState)
        binding.textBreedName.text = breedName
    }

    private fun updateState(pawState: DogBreedDetailsViewModel.DetailPawState) {
        when (pawState) {
            is DogBreedDetailsViewModel.DetailPawState.Success -> displayResults(pawState.images)
            DogBreedDetailsViewModel.DetailPawState.Error -> displayError()
        }
    }

    private fun displayResults(results: List<String>) {
        if (results.isNullOrEmpty()) {
            handlePlaceholders(View.VISIBLE)
        } else {
            handlePlaceholders(View.GONE)
            adapter.submitList(results)
        }
    }

    private fun displayError() {
        handlePlaceholders(View.VISIBLE)
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show()
    }

    private fun handlePlaceholders(visibility: Int) {
        binding.imageNoData.visibility = visibility
    }
}