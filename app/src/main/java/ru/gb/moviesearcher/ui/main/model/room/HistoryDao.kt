package ru.gb.moviesearcher.ui.main.model.room

import androidx.room.*

@Dao
interface  HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE movieName LIKE :movie")
    fun getDataByWord(movie: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Query("UPDATE HistoryEntity SET note  = :note WHERE movie_id = :movie_id")
    fun updateCurrent(note: String, movie_id: Long )

    @Update(onConflict = OnConflictStrategy.REPLACE )
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

}