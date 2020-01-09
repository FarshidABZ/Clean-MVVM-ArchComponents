package com.k0d4black.theforce.viewmodels

import com.google.common.truth.Truth
import com.k0d4black.theforce.BaseViewModelTest
import com.k0d4black.theforce.data.usecases.GetCharacterBasicInfoUseCase
import com.k0d4black.theforce.data.usecases.GetCharacterFilmsUseCase
import com.k0d4black.theforce.data.usecases.GetCharacterPlanetUseCase
import com.k0d4black.theforce.data.usecases.GetCharacterSpeciesUseCase
import com.k0d4black.theforce.features.character_details.CharacterDetailViewModel
import com.k0d4black.theforce.mappers.toPresentation
import com.k0d4black.theforce.utils.SampleData
import com.k0d4black.theforce.utils.observeOnce
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class CharacterDetailViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var getCharacterBasicInfoUseCase: GetCharacterBasicInfoUseCase
    @Mock
    lateinit var getCharacterFilmsUseCase: GetCharacterFilmsUseCase
    @Mock
    lateinit var getCharacterPlanetUseCase: GetCharacterPlanetUseCase
    @Mock
    lateinit var getCharacterSpeciesUseCase: GetCharacterSpeciesUseCase

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    private val characterIdParams = 1

    @Before
    fun setup() {
        characterDetailViewModel = CharacterDetailViewModel(
            getCharacterBasicInfoUseCase,
            getCharacterSpeciesUseCase,
            getCharacterPlanetUseCase,
            getCharacterFilmsUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldGetCharacterDetails() {
        runBlockingTest {
            setMockAnswers()

            characterDetailViewModel.getCharacterDetails(characterIdParams)

            characterDetailViewModel.characterDetail.observeOnce {
                Truth.assertThat(it).isEqualTo(SampleData.characterDomainModel.toPresentation())
            }
            characterDetailViewModel.characterCharacterSpecies.observeOnce {
                Truth.assertThat(it)
                    .isEqualTo(SampleData.speciesDomainModel.map { it.toPresentation() })
            }
            characterDetailViewModel.characterFilms.observeOnce {
                Truth.assertThat(it)
                    .isEqualTo(SampleData.characterFilms.map { it.toPresentation() })
            }
            characterDetailViewModel.characterCharacterPlanet.observeOnce {
                Truth.assertThat(it).isEqualTo(SampleData.planetDomainModel.toPresentation())
            }
        }


    }

    private suspend fun setMockAnswers() {
        given(getCharacterSpeciesUseCase.execute(characterIdParams)).willReturn(flow {
            emit(SampleData.speciesDomainModel)
        })
        given(getCharacterBasicInfoUseCase.execute(characterIdParams)).willReturn(flow {
            emit(SampleData.characterDomainModel)
        })
        given(getCharacterFilmsUseCase.execute(characterIdParams)).willReturn(flow {
            emit(SampleData.characterFilms)
        })
        given(getCharacterPlanetUseCase.execute(characterIdParams)).willReturn(flow {
            emit(SampleData.planetDomainModel)
        })
    }

}