package com.platzi.android.rickandmorty.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "character_id") var id: Int,
    @ColumnInfo(name = "character_name") var name: String,
    @ColumnInfo(name = "character_image") var image: String?,
    @ColumnInfo(name = "character_gender") var gender: String,
    @ColumnInfo(name = "character_species") var species: String,
    @ColumnInfo(name = "character_status") var status: String,
    @Embedded var origin: OriginEntity,
    @Embedded var location: LocationEntity,
    @ColumnInfo(name = "character_episode_list") var episodeList: List<String>
)

data class LocationEntity(
    var locationName: String,
    var locationUrl: String
)

data class OriginEntity(
    var originName: String,
    var originUrl: String
)
