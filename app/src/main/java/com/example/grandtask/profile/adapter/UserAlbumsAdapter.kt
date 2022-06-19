package com.example.grandtask.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grandtask.databinding.ItemUserAlbumBinding
import com.example.grandtask.profile.model.AlbumsModel
import kotlinx.android.synthetic.main.item_user_album.view.*

class UserAlbumsAdapter(
    private val onAlbumClickCallback: (albumId: String) -> Unit,
) : RecyclerView.Adapter<UserAlbumsAdapter.HistoryVieHolder>() {

    private val albumsList: ArrayList<AlbumsModel> = ArrayList()
    private lateinit var binding: ItemUserAlbumBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVieHolder {
        binding =
            ItemUserAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryVieHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryVieHolder, position: Int) {
        var usersListItem = albumsList[position]

        holder.itemView.txtAlbumName.text = usersListItem.title

        holder.itemView.setOnClickListener {
            usersListItem = albumsList[position]
            onAlbumClickCallback.invoke(usersListItem.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    inner class HistoryVieHolder(itemView: ItemUserAlbumBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val txtAlbumName = itemView.txtAlbumName
    }

    fun updateData(data: List<AlbumsModel>) {
        albumsList.clear()
        albumsList.addAll(data)
        notifyDataSetChanged()
    }
}