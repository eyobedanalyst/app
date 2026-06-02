package com.example.data.model

data class SmartTutorialCourse(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val rating: Float,
    val reviewsCount: Int,
    val level: String,
    val price: String,
    val durationHours: Int,
    val lessonsCount: Int,
    val features: List<String>,
    val imageUrl: String? = null
)

data class CourseAnnouncement(
    val id: String,
    val title: String,
    val content: String,
    val date: String,
    val category: String
)

data class Testimonial(
    val name: String,
    val role: String,
    val quote: String,
    val rating: Int
)
