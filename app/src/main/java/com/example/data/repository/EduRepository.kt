package com.example.data.repository

import com.example.data.database.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class EduRepository(private val eduDao: EduDao) {

    // Access to static course lists
    fun getGrade11Lessons(): List<Lesson> = WebDevContent.grade11Lessons
    fun getGrade12Lessons(): List<Lesson> = WebDevContent.grade12Lessons

    fun getLessonById(id: String): Lesson? {
        return (WebDevContent.grade11Lessons + WebDevContent.grade12Lessons).find { it.id == id }
    }

    fun getSmartCourses(): List<SmartTutorialCourse> = WebDevContent.smartCourses
    fun getCourseById(id: String): SmartTutorialCourse? {
        return WebDevContent.smartCourses.find { it.id == id }
    }

    fun getAnnouncements(): List<CourseAnnouncement> = WebDevContent.announcements
    fun getTestimonials(): List<Testimonial> = WebDevContent.testimonials

    // Bookmarks Flow
    val allBookmarks: Flow<List<BookmarkEntity>> = eduDao.getAllBookmarks()
    fun isBookmarked(lessonId: String): Flow<Boolean> = eduDao.isBookmarked(lessonId)

    suspend fun toggleBookmark(lessonId: String, grade: Int, title: String, shouldBookmark: Boolean) {
        if (shouldBookmark) {
            eduDao.insertBookmark(BookmarkEntity(lessonId, grade, title))
        } else {
            eduDao.deleteBookmark(lessonId)
        }
    }

    // Progress Flow
    val allProgress: Flow<List<ProgressEntity>> = eduDao.getAllProgress()

    suspend fun setLessonProgress(lessonId: String, grade: Int, completed: Boolean) {
        eduDao.insertProgress(ProgressEntity(lessonId, grade, completed))
    }

    // Custom study notes
    val allNotes: Flow<List<UserNoteEntity>> = eduDao.getAllNotes()

    suspend fun addNote(lessonId: String, lessonTitle: String, grade: Int, title: String, content: String) {
        eduDao.insertNote(UserNoteEntity(lessonId = lessonId, lessonTitle = lessonTitle, grade = grade, title = title, content = content))
    }

    suspend fun deleteNote(id: Int) {
        eduDao.deleteNote(id)
    }

    // Quiz score history
    val allQuizScores: Flow<List<QuizScoreEntity>> = eduDao.getAllQuizScores()

    suspend fun saveQuizScore(lessonId: String, grade: Int, score: Int, total: Int) {
        eduDao.insertQuizScore(QuizScoreEntity(lessonId, grade, score, total))
    }
}
