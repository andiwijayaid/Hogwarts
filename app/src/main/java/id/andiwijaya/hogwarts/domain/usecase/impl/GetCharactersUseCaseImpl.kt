package id.andiwijaya.hogwarts.domain.usecase.impl

import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import id.andiwijaya.hogwarts.domain.usecase.GetCharactersUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: HogwartsRepository
) : GetCharactersUseCase {
    override operator fun invoke(keyword: String, isSearch: Boolean) = repository.getCharacters(
        keyword, isSearch
    )
}