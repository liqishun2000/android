package com.example.android.training.room.repository

import com.example.android.training.room.dao.MusicDao
import com.example.android.training.room.model.Playlist
import com.example.android.training.room.model.PlaylistSongJoin
import com.example.android.training.room.model.PlaylistWithSongs

class MusicRepository(private val musicDao: MusicDao) {

    // 歌单操作
    suspend fun createPlaylist(name: String, cover: String? = null): Long {
        val playlist = Playlist(name = name, cover = cover)
        return musicDao.insertPlaylist(playlist)
    }

    suspend fun addSongsToPlaylist(playlistId: Long, songIds: List<String>) {
        val currentMaxOrder = getMaxOrderIndex(playlistId)
        songIds.forEachIndexed { index, songId ->
            val join = PlaylistSongJoin(
                playlistId = playlistId,
                songId = songId,
                orderIndex = currentMaxOrder + index + 1
            )
            musicDao.addSongToPlaylist(join)
        }
        musicDao.updatePlaylistSongCount(playlistId)
    }

    suspend fun getPlaylistDetails(playlistId: Long): PlaylistWithSongs? {
        return musicDao.getPlaylistWithSongs(playlistId)
    }

    private suspend fun getMaxOrderIndex(playlistId: Long): Int {
        val songs = musicDao.getSongsInPlaylist(playlistId)
        return songs.size
    }

}