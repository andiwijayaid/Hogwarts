package id.andiwijaya.hogwarts.domain.model

sealed class CharacterListState {
    object Loading : CharacterListState()
    object Error : CharacterListState()
    object NoDataFound : CharacterListState()
    object Success : CharacterListState()
}