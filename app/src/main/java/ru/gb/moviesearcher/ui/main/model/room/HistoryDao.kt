package ru.gb.moviesearcher.ui.main.model.room

import androidx.room.*

@Dao
interface  HistoryDao {

    @Query("SELECT * FROM HistoryEntity ORDER BY timestamp DESC")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE movie_Id LIKE :movieId")
    fun getDataByWord(movieId: String): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE movie_Id LIKE :movieId")
    fun getDataByMovieId(movieId: Long): HistoryEntity


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Query("UPDATE HistoryEntity SET note  = :note WHERE movie_id = :movie_id")
    fun updateCurrent(note: String, movie_id: Long )

    @Update(onConflict = OnConflictStrategy.REPLACE )
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

}