package com.example.android.training.room.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey
    val id: String, // 可以使用文件路径或媒体库ID
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long, // 时长(毫秒)
    val filePath: String,
    val albumArt: String? = null, // 专辑封面路径
    val size: Long, // 文件大小
    val dateAdded: Long // 添加时间
)

@Entity(tableName = "playlists")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val cover: String? = null, // 封面图片路径
    val description: String? = null,
    val createTime: Long = System.currentTimeMillis(),
    val songCount: Int = 0
)

@Entity(
    tableName = "playlist_song_join",
    primaryKeys = ["playlistId", "songId"],
    foreignKeys = [
        ForeignKey(
            entity = Playlist::class,
            parentColumns = ["id"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Song::class,
            parentColumns = ["id"],
            childColumns = ["songId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaylistSongJoin(
    val playlistId: Long,
    val songId: String,
    val addTime: Long = System.currentTimeMillis(),
    val orderIndex: Int = 0 // 排序索引
)

// 歌单及其包含的歌曲
data class PlaylistWithSongs(
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PlaylistSongJoin::class,
            parentColumn = "playlistId",
            entityColumn = "songId"
        )
    )
    val songs: List<Song>
)

// 歌曲及其所在的歌单
data class SongWithPlaylists(
    @Embedded val song: Song,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PlaylistSongJoin::class,
            parentColumn = "songId",
            entityColumn = "playlistId"
        )
    )
    val playlists: List<Playlist>
)