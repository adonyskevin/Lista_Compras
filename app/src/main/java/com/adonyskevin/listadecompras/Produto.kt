package com.adonyskevin.listadecompras

import android.graphics.Bitmap

data class Produto(
    var id: Int,
    var nome: String,
    var quantidade: Int,
    var valor: Double,
    val foto: Bitmap? = null)


