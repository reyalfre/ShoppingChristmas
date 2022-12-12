package edu.programacion.shoppingchristmas.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.programacion.shoppingchristmas.model.Shopping

@Dao
interface ShoppingDao  {

    @Query("SELECT * FROM Shopping")
    suspend fun getAllShopping(): MutableList<Shopping>

    @Insert
    suspend fun insertShopping(shopping: Shopping)

    @Query("DELETE FROM Shopping WHERE id = :id")
    suspend fun deleteShopping(id: Int)

    @Update
    suspend fun updateShopping(shopping: Shopping)

    @Query("SELECT * FROM Shopping WHERE iscompleated")
    suspend fun getCompleatedShopping(): MutableList<Shopping>

    @Query("SELECT * FROM Shopping WHERE NOT iscompleated")
    suspend fun getPendingShopping(): MutableList<Shopping>


}