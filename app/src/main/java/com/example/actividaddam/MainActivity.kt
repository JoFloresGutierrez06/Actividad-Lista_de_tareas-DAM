package com.example.actividaddam

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DragHandle
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
                    MyTasksScreen()
                }
            }
        }
    }
}


// ---------- MODELO DE DATOS ----------

data class Task(
    val title: String,
    val done: Boolean = false
)

// ---------- COLORES ----------

private val HeaderRed = Color(0xFFE05B6B)
private val ScreenBg = Color(0xFFF5F5F7)
private val CardBg = Color.White
private val TextGray = Color(0xFF6B6B6B)

// ---------- PANTALLA PRINCIPAL ----------
// Box raíz: apila el contenido (Column) y el FAB flotante encima.

@Composable
fun MyTasksScreen() {

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

    // ---------- BOX RAÍZ ----------
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBg)
    ) {

        // ---------- COLUMN PRINCIPAL (contenido vertical) ----------
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // ---- Header (Row dentro de un Box para el fondo rojo) ----
            HeaderSection()

            // ---- Row de filtros (chips) ----
            FilterChipsRow(
                selected = selectedFilter,
                onSelect = { selectedFilter = it }
            )

            // ---- Lista de tareas agrupada por sección ----
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item { SectionLabel("TODAY") }
                items(todayTasks) { task ->
                    TaskRow(
                        task = task,
                        onToggle = {
                            val index = todayTasks.indexOf(task)
                            todayTasks[index] = task.copy(done = !task.done)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }
                item { SectionLabel("UPCOMING") }
                items(upcomingTasks) { task ->
                    TaskRow(
                        task = task,
                        onToggle = {
                            val index = upcomingTasks.indexOf(task)
                            upcomingTasks[index] = task.copy(done = !task.done)
                        }
                    )
                }
            }
        }

        // ---------- FAB flotante (posicionado dentro del Box raíz) ----------
        FloatingActionButton(
            onClick = { /* acción de agregar tarea */ },
            containerColor = HeaderRed,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
        }
    }
}

// ---------- HEADER ----------
// Box para poder controlar altura + color de fondo, con un Row interno
// para alinear el texto verticalmente (por si luego agregas un ícono).

@Composable
private fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderRed)
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Tasks",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ---------- ROW DE FILTROS ----------
// Row horizontal con 3 chips seleccionables.

@Composable
private fun FilterChipsRow(
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
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

// Cada chip es un Box con texto centrado (Row interno para simetría de padding).

@Composable
private fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) Color(0xFFD6E4FF) else CardBg,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (isSelected) Color(0xFF2455C9) else TextGray,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// ---------- LABEL DE SECCIÓN ----------

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = TextGray,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}

// ---------- FILA DE TAREA ----------
// Row horizontal: Checkbox | Column (texto) ocupando el espacio restante | drag handle.
// Todo envuelto en un Box con fondo blanco y esquinas redondeadas simulando la tarjeta.

@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBg, shape = RoundedCornerShape(12.dp))
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

            // Column con el texto de la tarea (permite agregar subtítulo/fecha después)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 15.sp,
                    color = if (task.done) TextGray else Color.Black,
                    textDecoration = if (task.done) TextDecoration.LineThrough else TextDecoration.None
                )
            }

            Icon(
                imageVector = Icons.Default.DragHandle,
                contentDescription = "Reordenar",
                tint = Color(0xFFBFBFBF)
            )
        }
    }
}

// ---------- PREVIEW ----------

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MyTasksScreenPreview() {
    MaterialTheme {
        MyTasksScreen()
    }
}
