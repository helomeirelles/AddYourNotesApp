package com.addyournotes.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.addyournotes.R
import com.addyournotes.components.NoteButton
import com.addyournotes.components.NoteInputText
import com.addyournotes.data.NotesDataSource
import com.addyournotes.model.Note
import com.addyournotes.utils.formatDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = { Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "") },
            backgroundColor = Color(0xFFDDEEFC)
        )

        //Content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if(it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title = it
                })
            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = description,
                label = "Add a note",
                onTextChange = {if(it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    }) description = it})
            NoteButton(text = "Save", onClick = {
                if(title.isNotEmpty() && description.isNotEmpty()) {
                    onAddNote(Note(title = title, description = description))
                    title = ""
                    description = ""
                    Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                }
            })
        }
        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn {
            items(notes) { note ->
                NoteRow(note = note, onNoteClicked = { onRemoveNote(note) })
            }
        }
    }
}


@Composable
fun NoteRow(modifier: Modifier = Modifier,
            note: Note,
            onNoteClicked: (Note) -> Unit){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp),
        shape = RoundedCornerShape(topEnd = 33.dp),
        backgroundColor = Color(0xFFDDEEFC)
    ) {
        Column(modifier = modifier
            .padding(8.dp)
            .clickable { onNoteClicked(note) }
        ) {
            Text(text = note.title,
                style = MaterialTheme.typography.body2
            )
            Text(text = note.description,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold)
            
            Text(text = note.entryDate?.time?.let { formatDate(it) }.toString(),
                style = MaterialTheme.typography.caption)

        }


    }

}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    NoteScreen(notes = NotesDataSource().loadNotes(), onAddNote = {}, onRemoveNote = {})
}