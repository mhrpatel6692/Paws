package com.paws.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DogBreedImages(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "image_urls") val imageUrls: List<String>,
    @ColumnInfo(name = "breed_name") val breedName: String
)
