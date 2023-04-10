package com.fionei.multigames.tools


val Int.toNormalTime: String
    get() {
        val hours = this / 1000 / 60 / 60 % 60
        val minutes = this / 1000 / 60 % 60
        val seconds = this / 1000 % 60
        var result = ""
        if (hours > 0) {
            result += "$hours hours "
        }
        if (hours > 0 || minutes > 0) {
            result += "$minutes minutes "
        }
        result += "$seconds seconds"
        return result
    }