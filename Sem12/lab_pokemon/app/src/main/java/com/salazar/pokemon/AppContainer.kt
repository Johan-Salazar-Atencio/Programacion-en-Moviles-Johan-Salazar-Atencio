package com.salazar.pokemon

import com.salazar.pokemon.data.remote.RetrofitClient
import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.data.repository.ItemRepositoryImpl

class AppContainer {
    val itemRepository: ItemRepository = ItemRepositoryImpl(RetrofitClient.api)
}
