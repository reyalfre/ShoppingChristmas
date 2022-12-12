package edu.programacion.shoppingchristmas

import android.app.Application
import androidx.room.Room
import edu.programacion.shoppingchristmas.database.ShoppingDatabase

class ShoppingApplication : Application() {

    companion object{
        lateinit var  database: ShoppingDatabase
    }

    override fun onCreate() {
        super.onCreate()

        //contruimos la database
        database = Room.databaseBuilder(this,
            ShoppingDatabase::class.java,
            "ShoppingDatabase")
            .build()
    }
}