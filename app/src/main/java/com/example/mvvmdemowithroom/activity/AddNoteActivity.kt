package com.example.mvvmdemowithroom.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdemowithroom.R
import com.example.mvvmdemowithroom.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {
    lateinit var edtNoteName: EditText
    lateinit var edtNoteDescription: EditText
    lateinit var btnSave: Button
    lateinit var viewModel: NoteViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        initView()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId", -1)
            btnSave.text = "Update Note"
            edtNoteName.setText(noteTitle)
            edtNoteDescription.setText(noteDescription)
        } else {
            btnSave.setText(R.string.str_save_data)
        }

        btnSave.setOnClickListener {
            val noteTitle = edtNoteName.text.toString()
            val noteDescription = edtNoteDescription.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM,yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updateNote = com.example.mvvmdemowithroom.database.Note(
                        0,
                        noteTitle,
                        noteDescription,
                        currentDateAndTime
                    )
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM,yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    // if the string is not empty we are calling a
                    // add note method to add data to our room database.
                    viewModel.addNote(
                        com.example.mvvmdemowithroom.database.Note(
                            0,
                            noteTitle,
                            noteDescription,
                            currentDateAndTime
                        )
                    )
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }

            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }

    }

    private fun initView() {
        edtNoteName = findViewById(R.id.edt_noteName)
        edtNoteDescription = findViewById(R.id.edt_noteDescription)
        btnSave = findViewById(R.id.btn_save)

    }


}