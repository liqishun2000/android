package com.example.android.training.jetpack.media3

/**
 * <androidx.media3.ui.PlayerView 点击暂停闪烁问题：
 * 设置: app:surface_type="texture_view"
 * app:shutter_background_color="@color/base_transparent"
 *
 * 媒体通知栏 player事件拦截 用ForwardingPlayer，传给MediaSession
 * class MusicPlayer(
 *     private val player: Player
 * ) : ForwardingPlayer(player) {
 *
 *     override fun seekToPrevious() {
 *         mediaLog("MusicPlayer>>>seekToPrevious")
 *         AudioPlayer.playPreview()
 *     }
 *
 *     override fun seekToNext() {
 *         mediaLog("MusicPlayer>>>seekToNext")
 *         AudioPlayer.playNext()
 *     }
 * }
 * MediaSession.Builder(this, MusicPlayer(exoPlayer!!))
 *
 * 自定义媒体会话通知栏:
 * 通过给CommandButton设置
 * .setPlayerCommand(Player.COMMAND_SEEK_TO_PREVIOUS)
 *
 * */