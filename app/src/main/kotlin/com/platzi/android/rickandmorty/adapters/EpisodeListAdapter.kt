package com.platzi.android.rickandmorty.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.api.EpisodeServer
import com.platzi.android.rickandmorty.databinding.ItemListEpisodeBinding
import com.platzi.android.rickandmorty.utils.bindingInflate


class EpisodeListAdapter(
    private val listener: (EpisodeServer) -> Unit
): RecyclerView.Adapter<EpisodeListAdapter.EpisodeListViewHolder>() {

    private val episodeList: MutableList<EpisodeServer> = mutableListOf()

    fun updateData(newData: List<EpisodeServer>) {
        episodeList.clear()
        episodeList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EpisodeListViewHolder(
            parent.bindingInflate(R.layout.item_list_episode, false),
            listener
        )

    override fun getItemCount() = episodeList.size

    override fun onBindViewHolder(holder: EpisodeListViewHolder, position: Int) {
        holder.bind(episodeList[position])
    }

    class EpisodeListViewHolder(
        private val dataBinding: ItemListEpisodeBinding,
        private val listener: (EpisodeServer) -> Unit
    ): RecyclerView.ViewHolder(dataBinding.root) {

        //region Public Methods
        fun bind(item: EpisodeServer){
            dataBinding.episode = item
            itemView.setOnClickListener { listener(item) }
        }

    }
}
