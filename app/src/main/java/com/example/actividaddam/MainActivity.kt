package com.example.actividaddam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.actividaddam.ui.theme.ActividadDAMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActividadDAMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MyTasksScreen()
                    MyTasksApp()
                }
            }
        }
    }
}


data class Task(
    val title: String,
    val done: Boolean = false
)


private val LightColors = lightColorScheme(
    primary = Color(0xFFE05B6B),
    onPrimary = Color.White,
    background = Color(0xFFF5F5F7),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    onSurfaceVariant = Color(0xFF6B6B6B),
    secondaryContainer = Color(0xFFD6E4FF),
    onSecondaryContainer = Color(0xFF2455C9)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFE87C89),
    onPrimary = Color(0xFF3A0410),
    background = Color(0xFF121214),
    surface = Color(0xFF1E1E20),
    onSurface = Color(0xFFEDEDED),
    onSurfaceVariant = Color(0xFFA0A0A0),
    secondaryContainer = Color(0xFF33415C),
    onSecondaryContainer = Color(0xFFC6D8FF)
)

@Composable
fun MyTasksTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}


@Composable
fun MyTasksApp() {
    var isDarkTheme by remember { mutableStateOf(false) }

    MyTasksTheme(darkTheme = isDarkTheme) {
        MyTasksScreen(
            isDarkTheme = isDarkTheme,
            onToggleTheme = { isDarkTheme = !isDarkTheme }
        )
    }
}

@Composable
fun MyTasksScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All Tasks") }

    val todayTasks = remember {
        mutableStateListOf(
            Task("Review product design specs", done = true),
            Task("Prepare slides for sync meeting"),
            Task("Review pull requests")
        )
    }

    val upcomingTasks = remember {
        mutableStateListOf(
            Task("Update documentation guidelines"),
            Task("Schedule quarterly feedback loops")
        )
    }


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Puntos de quiebre simples: teléfono / tablet.
        val isWideScreen = maxWidth >= 600.dp
        val horizontalPadding = if (isWideScreen) 32.dp else 16.dp

        val contentMaxWidth = if (isWideScreen) 700.dp else maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = contentMaxWidth)
                .align(Alignment.TopCenter)
        ) {

            HeaderSection(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )

            FilterChipsRow(
                selected = selectedFilter,
                onSelect = { selectedFilter = it },
                horizontalPadding = horizontalPadding
            )

            if (isWideScreen) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = horizontalPadding, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    TaskColumn(
                        modifier = Modifier.weight(1f),
                        label = "TODAY",
                        tasks = todayTasks,
                        onToggle = { task ->
                            val i = todayTasks.indexOf(task)
                            todayTasks[i] = task.copy(done = !task.done)
                        }
                    )
                    TaskColumn(
                        modifier = Modifier.weight(1f),
                        label = "UPCOMING",
                        tasks = upcomingTasks,
                        onToggle = { task ->
                            val i = upcomingTasks.indexOf(task)
                            upcomingTasks[i] = task.copy(done = !task.done)
                        }
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item { SectionLabel("TODAY") }
                    items(todayTasks) { task ->
                        TaskRow(
                            task = task,
                            onToggle = {
                                val i = todayTasks.indexOf(task)
                                todayTasks[i] = task.copy(done = !task.done)
                            }
                        )
                    }

                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    item { SectionLabel("UPCOMING") }
                    items(upcomingTasks) { task ->
                        TaskRow(
                            task = task,
                            onToggle = {
                                val i = upcomingTasks.indexOf(task)
                                upcomingTasks[i] = task.copy(done = !task.done)
                            }
                        )
                    }
                }
            }
        }

        // ---------- FAB flotante ----------
        FloatingActionButton(
            onClick = { /* acción de agregar tarea */ },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
        }
    }
}


@Composable
private fun TaskColumn(
    modifier: Modifier = Modifier,
    label: String,
    tasks: List<Task>,
    onToggle: (Task) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionLabel(label)
        tasks.forEach { task ->
            TaskRow(task = task, onToggle = { onToggle(task) })
        }
    }
}


@Composable
private fun HeaderSection(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Tasks",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón para alternar modo claro/oscuro
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = if (isDarkTheme) "Cambiar a modo claro" else "Cambiar a modo oscuro",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun FilterChipsRow(
    selected: String,
    onSelect: (String) -> Unit,
    horizontalPadding: androidx.compose.ui.unit.Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("All Tasks", "Today", "Upcoming").forEach { label ->
            FilterChipItem(
                label = label,
                isSelected = label == selected,
                onClick = { onSelect(label) }
            )
        }
    }
}

@Composable
private fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected)
                    MaterialTheme.colorScheme.secondaryContainer
                else
                    MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (isSelected)
                MaterialTheme.colorScheme.onSecondaryContainer
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}


@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggle() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 15.sp,
                    color = if (task.done)
                        MaterialTheme.colorScheme.onSurfaceVariant
                    else
                        MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                )
            }

            Icon(
                imageVector = Icons.Default.DragHandle,
                contentDescription = "Reordenar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(name = "Teléfono - claro", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PreviewPhoneLight() {
    var dark by remember { mutableStateOf(false) }
    MyTasksTheme(darkTheme = dark) {
        MyTasksScreen(isDarkTheme = dark, onToggleTheme = { dark = !dark })
    }
}

@Preview(name = "Teléfono - oscuro", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PreviewPhoneDark() {
    var dark by remember { mutableStateOf(true) }
    MyTasksTheme(darkTheme = dark) {
        MyTasksScreen(isDarkTheme = dark, onToggleTheme = { dark = !dark })
    }
}

@Preview(name = "Tablet - claro", showBackground = true, widthDp = 800, heightDp = 700)
@Composable
private fun PreviewTablet() {
    var dark by remember { mutableStateOf(false) }
    MyTasksTheme(darkTheme = dark) {
        MyTasksScreen(isDarkTheme = dark, onToggleTheme = { dark = !dark })
    }
}