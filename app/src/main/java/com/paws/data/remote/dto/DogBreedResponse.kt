package com.paws.data.remote.dto

data class DogBreedResponse(
    val status: String,
    val message: Map<String, List<String>>,
    val code: Int,
) {
    companion object {
        const val SUCCESS_STATUS = "success"
    }
}
