// Ejemplo Gemini
/* package com.example.actividaddam.ui.theme

//package com.example.actividaddam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.actividaddam.ui.theme.ActividadDAMTheme
import com.example.actividaddam.ui.theme.FilterSelectedBg
import com.example.actividaddam.ui.theme.FilterUnselectedBorder
import com.example.actividaddam.ui.theme.HeaderCoral
import com.example.actividaddam.ui.theme.ScreenBackground
import com.example.actividaddam.ui.theme.SectionTitleColor
import com.example.actividaddam.ui.theme.TaskCardBorder
import com.example.actividaddam.ui.theme.TaskCompletedText
import com.example.actividaddam.ui.theme.TaskNormalText

data class TaskItemData(
    val id: String,
    val title: String,
    val isCompleted: Boolean = false,
)

val sampleTodayTasks = listOf(
    TaskItemData("1", "Review product design specs", isCompleted = true),
    TaskItemData("2", "Prepare slides for sync meeting", isCompleted = false),
    TaskItemData("3", "Review pull requests", isCompleted = false),
)

val sampleUpcomingTasks = listOf(
    TaskItemData("4", "Update documentation guidelines", isCompleted = false),
    TaskItemData("5", "Schedule quarterly feedback loops", isCompleted = false),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActividadDAMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainPage()
                }
            }
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.my_tasks),
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = HeaderCoral,
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
        )
    }
}

@Composable
fun FilterChipItem(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = if (isSelected) FilterSelectedBg else Color.White,
        border = if (isSelected) null else BorderStroke(1.dp, FilterUnselectedBorder),
    ) {
        Text(
            text = text,
            color = TaskNormalText,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
        )
    }
}

@Composable
fun RowFiltros(
    modifier: Modifier = Modifier,
    selectedFilter: String = stringResource(id = R.string.all_tasks),
    onFilterSelected: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        val allTasksText = stringResource(id = R.string.all_tasks)
        val todayText = stringResource(id = R.string.today_chip)
        val upcomingText = stringResource(id = R.string.upcoming_chip)

        FilterChipItem(
            text = allTasksText,
            isSelected = selectedFilter == allTasksText,
        ) { onFilterSelected(allTasksText) }

        FilterChipItem(
            text = todayText,
            isSelected = selectedFilter == todayText,
        ) { onFilterSelected(todayText) }

        FilterChipItem(
            text = upcomingText,
            isSelected = selectedFilter == upcomingText,
        ) { onFilterSelected(upcomingText) }
    }
}

@Composable
fun Task(
    task: TaskItemData,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, TaskCardBorder),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (task.isCompleted) Color(0xFFF1F5F9) else Color.Transparent)
                    .border(
                        width = 1.5.dp,
                        color = Color(0xFFCBD5E1),
                        shape = RoundedCornerShape(6.dp),
                    )
                    .clickable { onCheckedChange(!task.isCompleted) },
                contentAlignment = Alignment.Center,
            ) {
                if (task.isCompleted) {
                    Canvas(modifier = Modifier.size(12.dp)) {
                        val path = Path().apply {
                            moveTo(size.width * 0.15f, size.height * 0.5f)
                            lineTo(size.width * 0.4f, size.height * 0.8f)
                            lineTo(size.width * 0.85f, size.height * 0.2f)
                        }
                        drawPath(
                            path = path,
                            color = TaskNormalText,
                            style = Stroke(
                                width = 2.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round,
                            ),
                        )
                    }
                }
            }

            Text(
                text = task.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (task.isCompleted) TaskCompletedText else TaskNormalText,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_drag_handle),
                contentDescription = stringResource(id = R.string.drag_handle_desc),
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
fun Task(
    task: String,
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Task(
        task = TaskItemData(id = task, title = task, isCompleted = isCompleted),
        modifier = modifier,
        onCheckedChange = onCheckedChange,
    )
}

@Composable
fun ColumnTodayTasks(
    modifier: Modifier = Modifier,
    tasks: List<TaskItemData> = sampleTodayTasks,
    onTaskCheckedChange: (String, Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.today),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = SectionTitleColor,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(bottom = 2.dp),
        )
        tasks.forEach { task ->
            Task(
                task = task,
            ) { isChecked -> onTaskCheckedChange(task.id, isChecked) }
        }
    }
}

@Composable
fun ColumnUpcomingTasks(
    modifier: Modifier = Modifier,
    tasks: List<TaskItemData> = sampleUpcomingTasks,
    onTaskCheckedChange: (String, Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.upcoming),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = SectionTitleColor,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(bottom = 2.dp),
        )
        tasks.forEach { task ->
            Task(
                task = task,
            ) { isChecked -> onTaskCheckedChange(task.id, isChecked) }
        }
    }
}

@Composable
fun AddTaskButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(56.dp),
        shape = CircleShape,
        containerColor = HeaderCoral,
        contentColor = Color.White,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_circle),
            contentDescription = stringResource(id = R.string.add_task_desc),
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    todayTasks: List<TaskItemData> = sampleTodayTasks,
    upcomingTasks: List<TaskItemData> = sampleUpcomingTasks,
    onAddTaskClicked: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = ScreenBackground,
        topBar = {
            TopBar()
        },
        floatingActionButton = {
            AddTaskButton(
                onClick = onAddTaskClicked,
                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            RowFiltros()
            ColumnTodayTasks(tasks = todayTasks)
            ColumnUpcomingTasks(tasks = upcomingTasks)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    ActividadDAMTheme {
        MainPage()
    }
} */

// Intento de nosotros
/*
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                    MainPage()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBar("Mis tareas")
        RowFiltros()
        ColumnTodayTasks()
        ColumnUpcomingTasks()
        AddTaskButton()

    }
}

@Composable
fun TopBar(title: String) {
    Text(
        text = "$title"
    )
    Text(
        text = "$title",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Task(task: String) { //Agregar checkbox y delete
    Text(
        text = "$task"
    )
}

@Composable
fun RowFiltros() {
    Button(
        onClick = {

        }
    ) {
        Text("All task")
    }
    Button(
        onClick = {

        }
    ) {
        Text("Today")
    }
    Button(
        onClick = {

        }
    ) {
        Text("Upcoming")
    }
}

@Composable
fun ColumnTodayTasks() {
    Column {
        Text("TODAY")
        LazyColumn {
            // Agregarle las Task
        }
    }
}

@Composable
fun ColumnUpcomingTasks() {
    Column {
        Text("UPCOMING")
        LazyColumn {
            // Agregarle las Task
        }
    }
}

@Composable
fun AddTaskButton() {
    Button(
        onClick = {

        }
    ) {
        Text("+")
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ActividadDAMTheme {
        TopBar("Android")
    }
}*/



 */