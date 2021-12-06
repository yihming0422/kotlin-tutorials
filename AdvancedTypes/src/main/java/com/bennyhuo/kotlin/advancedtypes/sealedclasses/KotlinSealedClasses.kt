package com.bennyhuo.kotlin.advancedtypes.sealedclasses

//region entity
data class Song(val name: String, val url: String, var position: Int)

data class ErrorInfo(val code: Int, val message: String)

object Songs {
    val StarSky = Song("Star Sky", "https://fakeurl.com/321144.mp3", 0)
}
//endregion

//region state
sealed class PlayerState

object Idle : PlayerState()

class Playing(val song: Song) : PlayerState() {
    fun start() {}
    fun stop() {}
}

class Error(val errorInfo: ErrorInfo) : PlayerState() {
    fun recover() {}
}
//endregion

class Player {
    var state: PlayerState = Idle

    fun play(song: Song) {
        this.state = when (val state = this.state) {
            Idle -> {
                Playing(song).also(Playing::start)
            }
            is Playing -> {
                state.stop()
                Playing(song).also(Playing::start)
            }
            is Error -> {
                state.recover()
                Playing(song).also(Playing::start)
            }
        }
    }
}

fun main() {
    val player = Player()
    player.play(Songs.StarSky)
}