package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val lessonId: String,
    val grade: Int,
    val title: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey val lessonId: String,
    val grade: Int,
    val completed: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_notes")
data class UserNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lessonId: String,
    val lessonTitle: String,
    val grade: Int,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_scores")
data class QuizScoreEntity(
    @PrimaryKey val lessonId: String,
    val grade: Int,
    val score: Int,
    val total: Int,
    val timestamp: Long = System.currentTimeMillis()
)
