package com.adonyskevin.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class CadastroActivity : AppCompatActivity() {
    val COD_IMAGE = 101
    val imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val edt_produto = findViewById<EditText>(R.id.edt_produto)
        val edt_qtd_produto = findViewById<EditText>(R.id.edt_qtd_produto)
        val edt_valor = findViewById<EditText>(R.id.edt_valor)
        val img_foto_produto = findViewById<ImageView>(R.id.img_foto_produto)
        val btn_inserir = findViewById<Button>(R.id.btn_inserir)

        btn_inserir.setOnClickListener {
            //Recebendo os valores digitados pelo usuário
            val produto = edt_produto.text.toString()
            val qtd = edt_qtd_produto.text.toString()
            val valor = edt_valor.text.toString()

            if ((produto.isNotEmpty()) && (qtd.isNotEmpty()) && (valor.isNotEmpty())){
                //Enviando o item para a lista
                val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)

                produtosGlobal.add(prod)

                edt_produto.text.clear()
                edt_qtd_produto.text.clear()
                edt_valor.text.clear()
            }else {
                edt_produto.error = if(edt_produto.text.isEmpty()) "Informe um produto" else null
                edt_qtd_produto.error = if(edt_qtd_produto.text.isEmpty()) "Informe a quantidade" else null
                edt_valor.error = if(edt_valor.text.isEmpty()) "Informe o valor" else null
            }
        }

        img_foto_produto.setOnClickListener {
            abrirGaleria()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == COD_IMAGE) && (resultCode == Activity.RESULT_OK)){
            if (data != null){
                //Lendo a URI da imagem
                val inputStream = contentResolver.openInputStream(data.data!!)

                //Transformando o resultado em bitmap
                val imageBitMap = BitmapFactory.decodeStream(inputStream)

                //Exibir a imagem no aplicativo
                //img_foto_produto.setImageBitmap(imageBitMap)
            }
        }
    }

    fun abrirGaleria(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        //Definindo filtro para imagens
        intent.type = "image/*"

        //Inicializando a activity com o resultado
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }
}