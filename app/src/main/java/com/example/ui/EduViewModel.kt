package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.database.BookmarkEntity
import com.example.data.database.ProgressEntity
import com.example.data.database.QuizScoreEntity
import com.example.data.database.UserNoteEntity
import com.example.data.model.*
import com.example.data.repository.EduRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EduViewModel(private val repository: EduRepository) : ViewModel() {

    // Static structures
    val grade11Lessons: List<Lesson> = repository.getGrade11Lessons()
    val grade12Lessons: List<Lesson> = repository.getGrade12Lessons()
    val smartCourses: List<SmartTutorialCourse> = repository.getSmartCourses()
    val announcements: List<CourseAnnouncement> = repository.getAnnouncements()
    val testimonials: List<Testimonial> = repository.getTestimonials()

    // Searching operations State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    val filteredLessons = searchQuery.map { query ->
        val all = grade11Lessons + grade12Lessons
        if (query.isBlank()) {
            emptyList()
        } else {
            all.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.chapterTitle.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Database reactive streams
    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val progress: StateFlow<List<ProgressEntity>> = repository.allProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userNotes: StateFlow<List<UserNoteEntity>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val quizScores: StateFlow<List<QuizScoreEntity>> = repository.allQuizScores
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun isLessonBookmarked(lessonId: String): Flow<Boolean> {
        return repository.isBookmarked(lessonId)
    }

    fun toggleBookmark(lesson: Lesson, shouldBookmark: Boolean) {
        viewModelScope.launch {
            repository.toggleBookmark(
                lessonId = lesson.id,
                grade = lesson.grade,
                title = lesson.title,
                shouldBookmark = shouldBookmark
            )
        }
    }

    fun markLessonCompleted(lesson: Lesson, completed: Boolean) {
        viewModelScope.launch {
            repository.setLessonProgress(lesson.id, lesson.grade, completed)
        }
    }

    fun saveNote(lessonId: String, title: String, content: String) {
        viewModelScope.launch {
            val lesson = repository.getLessonById(lessonId)
            val lessonTitle = lesson?.title ?: "Web Development Notes"
            val grade = lesson?.grade ?: 11
            repository.addNote(lessonId, lessonTitle, grade, title, content)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun saveQuizScore(lessonId: String, grade: Int, score: Int, total: Int) {
        viewModelScope.launch {
            repository.saveQuizScore(lessonId, grade, score, total)
        }
    }

    fun getLessonById(id: String): Lesson? {
        return repository.getLessonById(id)
    }

    // Companion injection factory
    companion object {
        fun provideFactory(repository: EduRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EduViewModel(repository) as T
                }
            }
    }
}
