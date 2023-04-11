package com.fionei.multigames.tools


val Int.toNormalTime: String
    get() {
        val hours = this / 1000 / 60 / 60 % 60
        val minutes = this / 1000 / 60 % 60
        val seconds = this / 1000 % 60
        val milliseconds = this % 1000
        var result = ""
        if (hours > 0) {
            result += "$hours hours "
        }
        if (hours > 0 || minutes > 0) {
            result += "$minutes minutes "
        }
        result += "$seconds seconds "
        result += "$milliseconds milliseconds"
        return result
    }

val Long.toNormalTime: String
    get() {
        val hours = this / 1000 / 60 / 60 % 60
        val minutes = this / 1000 / 60 % 60
        val seconds = this / 1000 % 60
        val milliseconds = this % 1000
        var result = ""
        if (hours > 0) {
            result += "$hours hours "
        }
        if (hours > 0 || minutes > 0) {
            result += "$minutes minutes "
        }
        result += "$seconds seconds "
        result += "$milliseconds milliseconds"
        return result
    }

val Int.toTimerTime: String
    get() {
        val hours = this / 1000 / 60 / 60 % 60
        val minutes = this / 1000 / 60 % 60
        val seconds = this / 1000 % 60
        val milliseconds = this / 10 % 100
        var result = ""
        if (hours > 0) {
            result += if (minutes in 0..9) "0$hours:"
            else "$hours:"
        }
        result += if (minutes in 0..9) "0$minutes:"
        else "$minutes:"
        result += if (seconds in 0..9) "0$seconds."
        else "$seconds."
        result += if (milliseconds in 0..9) "0$milliseconds"
        else "$milliseconds"
        return result
    }

val Long.toTimerTime: String
    get() {
        val hours = this / 1000 / 60 / 60 % 60
        val minutes = this / 1000 / 60 % 60
        val seconds = this / 1000 % 60
        val milliseconds = this / 10 % 100
        var result = ""
        if (hours > 0) {
            result += if (minutes in 0..9) "0$hours:"
            else "$hours:"
        }
        result += if (minutes in 0..9) "0$minutes:"
        else "$minutes:"
        result += if (seconds in 0..9) "0$seconds."
        else "$seconds."
        result += if (milliseconds in 0..9) "0$milliseconds"
        else "$milliseconds"
        return result
    }