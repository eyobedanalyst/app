package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Lesson
import com.example.ui.EduViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeScreens(
    viewModel: EduViewModel,
    initialTab: Int = 0,
    onNavigateToLesson: (String) -> Unit,
    onNavigateToQuiz: (String) -> Unit,
    onNavigateToPromo: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(initialTab) }
    val bookmarks by viewModel.bookmarks.collectAsState()
    val progress by viewModel.progress.collectAsState()

    val grade11Lessons = viewModel.grade11Lessons
    val grade12Lessons = viewModel.grade12Lessons

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Web Development Academy",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Academy Tab Bar
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Grade 11 Web", fontWeight = FontWeight.SemiBold, fontSize = 15.sp) },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Grade 11 Icon") },
                    modifier = Modifier.testTag("tab_grade11")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Grade 12 Web", fontWeight = FontWeight.SemiBold, fontSize = 15.sp) },
                    icon = { Icon(Icons.Default.Code, contentDescription = "Grade 12 Icon") },
                    modifier = Modifier.testTag("tab_grade12")
                )
            }

            // Progression Summary Box
            val activeLessons = if (selectedTab == 0) grade11Lessons else grade12Lessons
            val activeGrade = if (selectedTab == 0) 11 else 12
            val completedCount = activeLessons.count { lesson ->
                progress.any { it.lessonId == lesson.id && it.completed }
            }
            val progressPercentage = if (activeLessons.isNotEmpty()) {
                (completedCount.toFloat() / activeLessons.size.toFloat() * 100).toInt()
            } else 0

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "My Study Progress (Grade $activeGrade)",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "$completedCount / ${activeLessons.size} Done",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { progressPercentage.toFloat() / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "You've mastered $progressPercentage% of the Grade $activeGrade curriculum topics! Access slides & complete quick tests.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // Lessons List
            LazyColumn(
                contentPadding = PaddingValues(bottom = 90.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Group lessons by Chapter inside current syllabus
                val chapters = activeLessons.groupBy { it.chapterNumber to it.chapterTitle }

                chapters.forEach { (chapterKey, lessonsInChapter) ->
                    val (num, chTitle) = chapterKey

                    item {
                        ChapterHeaderRow(
                            chapterNumber = num,
                            chapterTitle = chTitle,
                            onTakeQuiz = {
                                // Navigate to diagnostic test of the first lesson or mock chapter quiz identifier
                                onNavigateToQuiz(lessonsInChapter.first().id)
                            }
                        )
                    }

                    items(lessonsInChapter) { lesson ->
                        val isBookmarked = bookmarks.any { it.lessonId == lesson.id }
                        val isCompleted = progress.any { it.lessonId == lesson.id && it.completed }

                        AcademicLessonCard(
                            lesson = lesson,
                            isBookmarked = isBookmarked,
                            isCompleted = isCompleted,
                            onClick = { onNavigateToLesson(lesson.id) }
                        )
                    }
                }

                // Promotional CTA block for SmartTutorial.com courses at the footer
                item {
                    PromoFooterCard(
                        onExplore = onNavigateToPromo
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterHeaderRow(
    chapterNumber: Int,
    chapterTitle: String,
    onTakeQuiz: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "CHAPTER $chapterNumber",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, letterSpacing = 1.2.sp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = chapterTitle,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(
            onClick = onTakeQuiz,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.height(34.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Quiz,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Quiz", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AcademicLessonCard(
    lesson: Lesson,
    isBookmarked: Boolean,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .testTag("lesson_${lesson.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Status Icon representing visual content type
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (isCompleted) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f)
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.Check else Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = if (isCompleted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Text Core Information Description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = lesson.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Action Indicator Flags
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isBookmarked) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Saved Bookmark",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
fun PromoFooterCard(
    onExplore: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.RocketLaunch,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Take It To The Next Level!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Looking for certified Python, Advanced SQL, React, or Flutter courses? Explore SmartTutorial's complete curriculum and professional learning paths.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 8.dp),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onExplore,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("footer_promo_cta")
            ) {
                Text("Explore SmartTutorial Courses", fontWeight = FontWeight.Bold)
            }
        }
    }
}
