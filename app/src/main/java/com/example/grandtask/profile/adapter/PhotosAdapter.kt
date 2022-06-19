package com.example.grandtask.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.grandtask.databinding.ItemPhotosBinding
import com.example.grandtask.profile.model.PhotosModel
import com.squareup.picasso.Picasso

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>(), Filterable {

    private lateinit var binding: ItemPhotosBinding
    private var photosList = ArrayList<PhotosModel>(5)
    private var photosFilteredList = photosList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photosFilteredList[position]
        Picasso.get().load(photo.url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return photosFilteredList.size
    }

    inner class ViewHolder(itemView: ItemPhotosBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val image = itemView.image
    }

    fun setData(photos: ArrayList<PhotosModel>) {
        this.photosList = photos
        photosFilteredList = photos
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                photosFilteredList = if (charSearch.isEmpty()) {
                    photosList
                } else {
                    val resultList = ArrayList<PhotosModel>()
                    for (row in photosList) {
                        if (row.title.trim().contains(charSearch.trim())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = photosFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                photosFilteredList = results?.values as ArrayList<PhotosModel>
                notifyDataSetChanged()
            }
        }
    }


}