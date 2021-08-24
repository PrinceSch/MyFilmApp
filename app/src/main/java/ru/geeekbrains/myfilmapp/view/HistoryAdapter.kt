package ru.geeekbrains.myfilmapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.android.synthetic.main.recycler_film_item.view.*
import ru.geeekbrains.myfilmapp.R
import ru.geeekbrains.myfilmapp.databinding.RecyclerFilmItemBinding
import ru.geeekbrains.myfilmapp.model.data.Film

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<Film> = arrayListOf()

    fun setData (data: List<Film>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_history, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Film) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.item_film_title.text = data.title
                Picasso.get()
                    .load(data.poster_path)
                    .fit()
                    .centerCrop()
                    .into(itemView.item_poster)
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${data.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}
