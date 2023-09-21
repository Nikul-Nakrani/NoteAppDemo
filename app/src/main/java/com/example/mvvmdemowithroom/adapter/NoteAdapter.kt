package com.example.mvvmdemowithroom.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemowithroom.R
import com.example.mvvmdemowithroom.database.Note


class NoteAdapter(
    private val noteClickDeleteInterface: NoteClickDeleteInterface,
    private val noteClickInterface: NoteClickInterface
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNote = itemView.findViewById<TextView>(R.id.tv_note_title)!!
        val tvDate = itemView.findViewById<TextView>(R.id.tv_note_date)!!
        val ivDelete = itemView.findViewById<AppCompatImageView>(R.id.iv_delete)!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_rv, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNote.text = allNotes[position].title
        holder.tvDate.text = "Last Updated : " + allNotes.get(position).timeStamp

        holder.ivDelete.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }
    }

    // below method is use to update our list of notes.
    fun updatedList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}

interface NoteClickDeleteInterface {
    // action on delete image view.
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    // on recycler view item for updating it.
    fun onNoteClick(note: Note)

}