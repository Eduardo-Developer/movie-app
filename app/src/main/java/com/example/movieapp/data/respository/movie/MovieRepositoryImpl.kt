package com.example.movieapp.data.respository.movie

import com.example.movieapp.data.api.ServiceApi
import com.example.movieapp.data.model.GenresResponse
import com.example.movieapp.data.model.MovieResponse
import com.example.movieapp.domain.repository.movie.MovieRepository
import com.example.movieapp.util.Constants
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : MovieRepository {
    override suspend fun getGenres(apiKey: String, language: String?): GenresResponse {
        return serviceApi.getGenres(
            apiKey = apiKey,
            language = language
        )
    }

    override suspend fun getMovieByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieResponse> {
        return serviceApi.getMoviesByGenre(
            apiKey = apiKey,
            language = language,
            genreId = genreId
        ).results ?: emptyList()
    }
}