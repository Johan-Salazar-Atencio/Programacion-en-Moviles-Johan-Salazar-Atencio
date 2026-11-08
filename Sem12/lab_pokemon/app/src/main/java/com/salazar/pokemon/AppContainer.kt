package com.salazar.pokemon

import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.data.repository.ItemRepositoryFalso

class AppContainer {
    val itemRepository: ItemRepository = ItemRepositoryFalso()
}
