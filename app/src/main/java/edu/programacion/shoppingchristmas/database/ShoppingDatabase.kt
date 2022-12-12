package edu.programacion.shoppingchristmas.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.programacion.shoppingchristmas.dao.ShoppingDao
import edu.programacion.shoppingchristmas.model.Shopping

@Database(entities = [Shopping::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase(){

    abstract  fun shoppingDao() : ShoppingDao
}