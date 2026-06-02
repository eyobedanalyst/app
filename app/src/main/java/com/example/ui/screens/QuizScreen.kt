package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.EduViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: EduViewModel,
    lessonId: String,
    onNavigateBack: () -> Unit
) {
    val lesson = remember(lessonId) { viewModel.getLessonById(lessonId) }

    if (lesson == null || lesson.quiz.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No Exam Quizzes Found", style = MaterialTheme.typography.titleLarge)
        }
        return
    }

    val quizList = lesson.quiz
    val totalQuestions = quizList.size

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var runningScore by remember { mutableStateOf(0) }
    var isQuizFinished by remember { mutableStateOf(false) }

    val activeQuestion = quizList[currentQuestionIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chapter Challenge Unit", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Exit Quiz")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isQuizFinished) {
                // Main diagnostic panel
                // Question progress meter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Running Score: $runningScore",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { (currentQuestionIndex + 1).toFloat() / totalQuestions.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Question card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = activeQuestion.question,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, lineHeight = 22.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Answer Options clickable container
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    activeQuestion.options.forEachIndexed { idx, option ->
                        val isSelected = selectedOptionIndex == idx
                        val isCorrectOption = activeQuestion.correctAnswerIndex == idx

                        val cardColor = when {
                            isAnswerSubmitted && isCorrectOption -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.85f)
                            isAnswerSubmitted && isSelected && !isCorrectOption -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.85f)
                            isSelected -> MaterialTheme.colorScheme.primaryContainer
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val borderColor = when {
                            isAnswerSubmitted && isCorrectOption -> MaterialTheme.colorScheme.tertiary
                            isAnswerSubmitted && isSelected && !isCorrectOption -> MaterialTheme.colorScheme.error
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.outlineVariant
                        }

                        val contentColor = when {
                            isAnswerSubmitted && isCorrectOption -> MaterialTheme.colorScheme.onTertiaryContainer
                            isAnswerSubmitted && isSelected && !isCorrectOption -> MaterialTheme.colorScheme.onErrorContainer
                            else -> MaterialTheme.colorScheme.onSurface
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(cardColor)
                                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isAnswerSubmitted) {
                                    selectedOptionIndex = idx
                                }
                                .padding(16.dp)
                                .testTag("option_$idx"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A'.code + idx).toChar().toString(),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = contentColor,
                                modifier = Modifier.weight(1f)
                            )

                            if (isAnswerSubmitted) {
                                if (isCorrectOption) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Correct",
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                } else if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Wrong",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }

                // Interactive Submission & Explanations Layer
                AnimatedVisibility(
                    visible = isAnswerSubmitted,
                    enter = expandVertically() + fadeIn()
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedOptionIndex == activeQuestion.correctAnswerIndex)
                                MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.15f)
                            else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (selectedOptionIndex == activeQuestion.correctAnswerIndex)
                                        Icons.Default.Mood
                                    else Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = if (selectedOptionIndex == activeQuestion.correctAnswerIndex)
                                        MaterialTheme.colorScheme.tertiary
                                    else MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (selectedOptionIndex == activeQuestion.correctAnswerIndex)
                                        "EXCELLENT JOB!"
                                    else "EXAM CONCEPTS CLARIFIED",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                                    color = if (selectedOptionIndex == activeQuestion.correctAnswerIndex)
                                        MaterialTheme.colorScheme.tertiary
                                    else MaterialTheme.colorScheme.error
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = activeQuestion.explanation,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Validation navigation controls button
                Button(
                    onClick = {
                        if (!isAnswerSubmitted) {
                            if (selectedOptionIndex != null) {
                                isAnswerSubmitted = true
                                if (selectedOptionIndex == activeQuestion.correctAnswerIndex) {
                                    runningScore++
                                }
                            }
                        } else {
                            if (currentQuestionIndex < totalQuestions - 1) {
                                currentQuestionIndex++
                                selectedOptionIndex = null
                                isAnswerSubmitted = false
                            } else {
                                // Save finished results to global Room databases!
                                viewModel.saveQuizScore(lesson.id, lesson.grade, runningScore, totalQuestions)
                                isQuizFinished = true
                            }
                        }
                    },
                    enabled = selectedOptionIndex != null,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("quiz_action_button")
                ) {
                    Text(
                        text = when {
                            !isAnswerSubmitted -> "Verify Answer"
                            currentQuestionIndex < totalQuestions - 1 -> "Next Challenge"
                            else -> "View Results"
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            } else {
                // Completed Results Summary layout
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Success Trophy",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(54.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Challenge Completed!",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(10.dp))

                val performancePercent = (runningScore.toFloat() / totalQuestions.toFloat() * 100).toInt()

                Text(
                    text = "You scored $runningScore out of $totalQuestions correctly ($performancePercent%). Results stored safely inside study profiles.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Custom celebratory review card based on performance
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = when {
                                performancePercent == 100 -> "Perfect Score! 🌟"
                                performancePercent >= 75 -> "Excellent Mastery! 🚀"
                                else -> "Keep Practicing! 📖"
                            },
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = when {
                                performancePercent == 100 -> "Incredible job! You completely understand Grade ${lesson.grade} concepts perfectly. Continue your learning path with certified courses!"
                                performancePercent >= 75 -> "Outstanding work. You've grabbed the core concepts securely. Rewatch slide definitions to master remaining answers."
                                else -> "Don't fret. ICT requires patience. Reread the HTML/CSS slide annotations and try the diagnostics once again!"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            // Reset state for retry
                            currentQuestionIndex = 0
                            selectedOptionIndex = null
                            isAnswerSubmitted = false
                            runningScore = 0
                            isQuizFinished = false
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("Retry Unit", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onNavigateBack,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("Exit Academy", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
