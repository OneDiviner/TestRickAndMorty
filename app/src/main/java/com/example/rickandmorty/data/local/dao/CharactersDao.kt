package com.example.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(charactersList: List<CharacterEntity>)

    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("""
        SELECT * FROM characters
        WHERE
            (:nameQuery IS NULL OR name LIKE '%' || :nameQuery || '%') AND
            (:status IS NULL OR status = :status) AND
            (:species IS NULL OR species = :species) AND
            (:type IS NULL OR type = :type) AND
            (:gender IS NULL OR gender = :gender)
        ORDER BY id ASC
    """)
    fun getFilteredCharacters(
        nameQuery: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): Flow<List<CharacterEntity>>

    @Query("DELETE FROM characters")
    suspend fun clearAllCharacters()
}