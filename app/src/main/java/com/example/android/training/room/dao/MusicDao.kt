package com.example.android.training.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.android.training.room.model.Playlist
import com.example.android.training.room.model.PlaylistSongJoin
import com.example.android.training.room.model.PlaylistWithSongs
import com.example.android.training.room.model.Song
import com.example.android.training.room.model.SongWithPlaylists

@Dao
interface MusicDao {

    // 歌曲操作
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<Song>)

    @Query("SELECT * FROM songs")
    suspend fun getAllSongs(): List<Song>

    @Query("SELECT * FROM songs WHERE id = :songId")
    suspend fun getSongById(songId: String): Song?

    // 歌单操作
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist): Long

    @Update
    suspend fun updatePlaylist(playlist: Playlist)

    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)

    @Query("SELECT * FROM playlists")
    suspend fun getAllPlaylists(): List<Playlist>

    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    suspend fun getPlaylistById(playlistId: Long): Playlist?

    // 歌单-歌曲关联操作
    @Insert
    suspend fun addSongToPlaylist(playlistSongJoin: PlaylistSongJoin)

    @Query("DELETE FROM playlist_song_join WHERE playlistId = :playlistId AND songId = :songId")
    suspend fun removeSongFromPlaylist(playlistId: Long, songId: String)

    @Query("DELETE FROM playlist_song_join WHERE playlistId = :playlistId")
    suspend fun clearPlaylist(playlistId: Long)

    // 复杂查询
    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    suspend fun getPlaylistWithSongs(playlistId: Long): PlaylistWithSongs?

    @Transaction
    @Query("SELECT * FROM playlists")
    suspend fun getAllPlaylistsWithSongs(): List<PlaylistWithSongs>

    @Transaction
    @Query("SELECT * FROM songs WHERE id = :songId")
    suspend fun getSongWithPlaylists(songId: String): SongWithPlaylists?

    // 获取歌单中的歌曲（带排序）
    @Query("""
        SELECT songs.* FROM songs 
        INNER JOIN playlist_song_join ON songs.id = playlist_song_join.songId 
        WHERE playlist_song_join.playlistId = :playlistId 
        ORDER BY playlist_song_join.orderIndex ASC
    """)
    suspend fun getSongsInPlaylist(playlistId: Long): List<Song>

    // 更新歌单歌曲数量
    @Query("""
        UPDATE playlists 
        SET songCount = (SELECT COUNT(*) FROM playlist_song_join WHERE playlistId = :playlistId) 
        WHERE id = :playlistId
    """)
    suspend fun updatePlaylistSongCount(playlistId: Long)
}