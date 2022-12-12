package edu.programacion.shoppingchristmas.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Shopping")
data class Shopping(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var gift: String,
    var description: String,
    var money: Long,
    var date: String,
    var iscompleated: Boolean = false
): Parcelable
