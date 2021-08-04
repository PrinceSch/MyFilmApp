package ru.geeekbrains.myfilmapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.geeekbrains.myfilmapp.databinding.RecyclerFilmItemBinding
import ru.geeekbrains.myfilmapp.model.data.Film

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var filmData: List<Film> = listOf()
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener? = null

    fun setFilm (data: List<Film>){
        filmData = data
        notifyDataSetChanged()
    }

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.OnItemViewClickListener){
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener(){
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = RecyclerFilmItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(filmData[position])
    }

    inner class MainViewHolder(val binding: RecyclerFilmItemBinding ) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(film: Film){
                binding.itemFilmTitle.text = film.title
                binding.root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(film)
                }
            }
        }

    override fun getItemCount(): Int {
        return filmData.size
    }
}