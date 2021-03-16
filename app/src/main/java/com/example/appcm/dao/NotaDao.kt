package com.example.appcm.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.appcm.dataclasses.Nota

@Dao
interface NotaDao{
    @Query("SELECT * from Nota ORDER BY title ASC")
    fun getAlphabetizedTitles(): LiveData<List<Nota>>


    @Query("DELETE FROM Nota")
    suspend fun deleteAll()

}