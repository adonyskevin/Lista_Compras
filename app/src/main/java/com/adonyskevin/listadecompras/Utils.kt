package com.adonyskevin.listadecompras

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

//Variável que armazenará os produtos da lista
val produtosGlobal = mutableListOf<Produto>()

//Extensões
fun Bitmap.toByteArray(): ByteArray{
    val stream = ByteArrayOutputStream()

    //Comprimindo a imagem
    this.compress(android.graphics.Bitmap.CompressFormat.PNG, 0, stream)

    return stream.toByteArray()
}

fun ByteArray.toBitmap(): Bitmap{
    return BitmapFactory.decodeByteArray(this,0,this.size)
}