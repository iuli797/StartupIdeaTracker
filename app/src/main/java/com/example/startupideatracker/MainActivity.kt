package com.example.startupideatracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StartupIdea(val id: Int, val title: String, val description: String)

//VIEWMODEL-UL
class IdeasViewModel : ViewModel() {
    // Aici stocăm lista de idei. MutableStateFlow notifică interfața când apar schimbări
    private val _ideasList = MutableStateFlow<List<StartupIdea>>(emptyList())
    val ideasList: StateFlow<List<StartupIdea>> = _ideasList.asStateFlow()

    fun addIdea(title: String, description: String) {
        if (title.isNotBlank()) {
            val newIdea = StartupIdea(
                id = (_ideasList.value.maxOfOrNull { it.id } ?: 0) + 1,
                title = title,
                description = description
            )
            // Actualizăm lista adăugând noua idee
            _ideasList.value = _ideasList.value + newIdea
        }
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

//NAVIGAREA (legătura între cele 2 ecrane)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Creăm o singură instanță de ViewModel pe care o dăm ambelor ecrane
    val viewModel: IdeasViewModel = viewModel()

    NavHost(navController = navController, startDestination = "list_screen") {
        composable("list_screen") {
            IdeaListScreen(navController = navController, viewModel = viewModel)
        }
        composable("add_screen") {
            AddIdeaScreen(navController = navController, viewModel = viewModel)
        }
    }
}

//ECRANUL 1: LISTA
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdeaListScreen(navController: NavController, viewModel: IdeasViewModel) {
    // Colectăm starea din ViewModel
    val ideas by viewModel.ideasList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Idei de Startup") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_screen") }) {
                Text("+", fontSize = 24.sp)
            }
        }
    ) { paddingValues ->
        // Componenta LazyColumn este lista propriu-zisă
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (ideas.isEmpty()) {
                item { Text("Nu ai nicio idee momentan. Apasă pe + pentru a adăuga.") }
            }
            items(ideas) { idea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = idea.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = idea.description)
                    }
                }
            }
        }
    }
}

//ECRANUL 2: ADAUGAREA
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIdeaScreen(navController: NavController, viewModel: IdeasViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adaugă o idee nouă") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Numele aplicației / Startup-ului") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrie pe scurt ce face") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.addIdea(title, description)
                    navController.popBackStack() // Ne întoarcem la ecranul anterior
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvează Ideea")
            }
        }
    }
}