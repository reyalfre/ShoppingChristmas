package edu.programacion.shoppingchristmas.adapter

import edu.programacion.shoppingchristmas.model.Shopping

interface ShoppingOnClickListener {

    fun onCompleatedShopping(shopping: Shopping)
    fun onClickShopping(shopping: Shopping)

}