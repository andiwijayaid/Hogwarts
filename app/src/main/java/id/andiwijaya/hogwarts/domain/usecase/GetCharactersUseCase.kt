package id.andiwijaya.hogwarts.domain.usecase

import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: HogwartsRepository) {
    operator fun invoke(house: String) = repository.getCharacters(house)
}