package com.example.kotstart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout

class MainActivity : : AppCompatActivity(), View.OnClickListener {

    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var adapter: NoteAdapter
    lateinit var notes: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        notes = loadNotes(this)
        adapter = NoteAdapter(notes, this)

        notes.add(Note("Note 1", "Blablabla"))
        notes.add(Note("Mémo Bob", "Grand joueur de basket"))
        notes.add(Note("Mémo Bobette", "Future championne de la NBA"))
        notes.add(Note("Pourquoi Kotlin ?", "Parce-que Java !"))

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        coordinatorLayout = findViewById(R.id.coordinator_layout) as CoordinatorLayout
        findViewById(R.id.create_note_fab).setOnClickListener(this)

        val recyclerView = findViewById(R.id.notes_recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }
        when (requestCode) {
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            startDetailActivity(view.tag as Int)
        } else {
            when (view.id) {
                R.id.create_note_fab -> createNewNote()
            }
        }
    }

    fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)

        when(data.action) {
            NoteDetailActivity.ACTION_SAVE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note, noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE -> deleteNote(noteIndex)
        }
    }

    fun saveNote(note: Note, noteIndex: Int) {
        persistNote(this, note)
        if (noteIndex < 0) {
            notes.add(0, note)
        } else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    fun deleteNote(noteIndex: Int) {
        if (noteIndex < 0) {
            return
        }
        val note = notes.removeAt(noteIndex)
        deleteNote(this, note)
        adapter.notifyDataSetChanged()

        Snackbar.make(coordinatorLayout, "${note.title} supprimé", Snackbar.LENGTH_SHORT)
            .show()
    }

    fun createNewNote() {
        startDetailActivity(-1)
    }

    fun startDetailActivity(noteIndex: Int) {
        val note = if (noteIndex < 0) Note() else notes[noteIndex]

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)
    }

}
