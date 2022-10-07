package pl.recordit.examples.techlif.composeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import pl.recordit.examples.techlif.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    private val peopleList = mutableStateListOf<Person>(
        Person("Karol", 25, true),
        Person("Ewa", 53, false),
        Person("Adam", 42, true)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        PeopleListView(peopleList)
                        Button(
                            onClick = {
                                peopleList.add(Person("Beata", 27, false))
                            },
                            shape = CircleShape
                        ) {
                            Column {
                                Text("Dodaj")
                                Text("Osobę")
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Person(val name: String, val age: Int, var isActive: Boolean)

@Composable
fun PeopleListView(list: List<Person>) {
    LazyColumn {
        items(list) { person ->
            PersonDetails(person = person)
        }
    }
}

@Composable
fun PersonDetails(person: Person = Person("no name", 0, false)) {
    val isClicked = remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(all = 2.dp)
            .clickable {
                isClicked.value != isClicked.value
                Log.i("APP", "Cliked " + isClicked.value)
            }
    ) {
        Text(
            text = person.name,
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.width(120.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        val isChecked = remember { mutableStateOf(false) }
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            enabled = true
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(person.age.toString())
            Text(
                "lat",
                color = if (isClicked.value) Color(0xFFCCFFAA) else Color.Red
            )
        }
    }
}