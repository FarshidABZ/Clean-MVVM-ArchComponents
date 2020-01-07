package com.k0d4black.theforce.data.repository

import com.k0d4black.theforce.data.source.CharacterDetailsDataSource
import com.k0d4black.theforce.domain.CharacterDetailsDomainModel
import com.k0d4black.theforce.domain.FilmDomainModel
import com.k0d4black.theforce.domain.PlanetDomainModel
import com.k0d4black.theforce.domain.SpeciesDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Co-ordinates data sources exposing character details
 */
class CharacterDetailsRepository @Inject constructor(
    private val characterDetailsDataSource: CharacterDetailsDataSource
) {
    suspend fun getCharacterDetails(characterId: Int): Flow<CharacterDetailsDomainModel> {
        return characterDetailsDataSource.getCharacterBasicDetails(characterId)
    }

    suspend fun getCharacterPlanet(characterId: Int): Flow<PlanetDomainModel> {
        return characterDetailsDataSource.getCharacterPlanet(characterId)
    }

    suspend fun getCharacterSpecies(characterId: Int): Flow<List<SpeciesDomainModel>> {
        return characterDetailsDataSource.getCharacterSpecies(characterId)
    }

    suspend fun getCharacterFilms(characterId: Int): Flow<List<FilmDomainModel>> {
        return characterDetailsDataSource.getCharacterFilms(characterId)
    }

}