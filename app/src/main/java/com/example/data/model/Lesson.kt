package com.example.data.model

data class Slide(
    val title: String,
    val text: String,
    val codeExample: String? = null,
    val codeLanguage: String? = "html",
    val tip: String? = null
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

data class Lesson(
    val id: String,
    val grade: Int, // 11 or 12
    val chapterNumber: Int,
    val chapterTitle: String,
    val title: String,
    val iconName: String,
    val description: String,
    val durationMin: Int,
    val slides: List<Slide>,
    val quiz: List<QuizQuestion>
)
