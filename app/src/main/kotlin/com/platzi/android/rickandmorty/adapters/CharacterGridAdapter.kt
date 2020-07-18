package com.platzi.android.rickandmorty.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.platzi.android.rickandmorty.R
import com.platzi.android.rickandmorty.api.CharacterServer
import com.platzi.android.rickandmorty.databinding.ItemGridCharacterBinding
import com.platzi.android.rickandmorty.utils.bindImageUrl
import com.platzi.android.rickandmorty.utils.bindingInflate
import kotlinx.android.synthetic.main.item_grid_character.view.*


class CharacterGridAdapter(
    private val listener: (CharacterServer) -> Unit
): RecyclerView.Adapter<CharacterGridAdapter.CharacterGridViewHolder>() {

    private val characterList: MutableList<CharacterServer> = mutableListOf()

    fun addData(newData: List<CharacterServer>) {
        characterList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterGridViewHolder(
            parent.bindingInflate(R.layout.item_grid_character, false),
            listener
        )

    override fun getItemCount() = characterList.size

    override fun getItemId(position: Int): Long = characterList[position].id.toLong()

    override fun onBindViewHolder(holder: CharacterGridViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    class CharacterGridViewHolder(
        private val dataBinding: ItemGridCharacterBinding,
        private val listener: (CharacterServer) -> Unit
    ): RecyclerView.ViewHolder(dataBinding.root) {

        //region Public Methods
        fun bind(item: CharacterServer){
            dataBinding.character = item
            itemView.character_image.bindImageUrl(
                url = item.image,
                placeholder = R.drawable.ic_camera_alt_black,
                errorPlaceholder = R.drawable.ic_broken_image_black
            )
            itemView.setOnClickListener { listener(item) }
        }

    }
}
