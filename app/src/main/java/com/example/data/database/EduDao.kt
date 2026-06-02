package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EduDao {

    // Bookmarks operations
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE lessonId = :lessonId")
    suspend fun deleteBookmark(lessonId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE lessonId = :lessonId)")
    fun isBookmarked(lessonId: String): Flow<Boolean>


    // Progress operations
    @Query("SELECT * FROM progress")
    fun getAllProgress(): Flow<List<ProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ProgressEntity)

    @Query("DELETE FROM progress WHERE lessonId = :lessonId")
    suspend fun deleteProgress(lessonId: String)


    // Study Notes operations
    @Query("SELECT * FROM user_notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<UserNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: UserNoteEntity)

    @Query("DELETE FROM user_notes WHERE id = :id")
    suspend fun deleteNote(id: Int)


    // Quiz Scores operations
    @Query("SELECT * FROM quiz_scores")
    fun getAllQuizScores(): Flow<List<QuizScoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizScore(score: QuizScoreEntity)
}
