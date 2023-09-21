package com.example.mvvmdemowithroom.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemowithroom.R
import com.example.mvvmdemowithroom.adapter.NoteAdapter
import com.example.mvvmdemowithroom.adapter.NoteClickDeleteInterface
import com.example.mvvmdemowithroom.adapter.NoteClickInterface
import com.example.mvvmdemowithroom.database.Note
import com.example.mvvmdemowithroom.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
    private lateinit var viewModel: NoteViewModel
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var addBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        clickListener()

        //set  Adapter
        val noteAdapter = NoteAdapter(this,this)
        noteRecyclerView.adapter = noteAdapter

        // initializing our view modal.
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        // from our view modal class to observer the changes on list.
        viewModel.allNotes.observe(this, Observer { list ->
            noteAdapter.updatedList(list)
        })
    }

    private fun initView() {
        noteRecyclerView = findViewById(R.id.recyclerView_note)
        addBtn = findViewById(R.id.floatingBtn)
    }

    private fun clickListener() {
        addBtn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
            finish()


        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.description)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        finish()
    }

    override fun onDeleteIconClick(note: Note) {
        // in on note click method we are calling delete
        // method from our view modal to delete our not.
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.title} Deleted", Toast.LENGTH_SHORT).show()
    }


}