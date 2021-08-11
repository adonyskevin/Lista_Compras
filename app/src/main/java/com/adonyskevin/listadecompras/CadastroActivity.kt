package com.adonyskevin.listadecompras

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class CadastroActivity : AppCompatActivity() {
    private var imageBitMap: Bitmap? = null
    private lateinit var imgFotoProduto: ImageView
    private lateinit var helper: ListaComprasDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val edtProduto = findViewById<EditText>(R.id.edt_produto)
        val edtQtdProduto = findViewById<EditText>(R.id.edt_qtd_produto)
        val edtValor = findViewById<EditText>(R.id.edt_valor)
        imgFotoProduto = findViewById(R.id.img_foto_produto)
        val btnInserir = findViewById<Button>(R.id.btn_inserir)

        helper = ListaComprasDatabase(this)

        btnInserir.setOnClickListener {
            //Recebendo os valores digitados pelo usuário
            val produto = edtProduto.text.toString()
            val qtd = edtQtdProduto.text.toString()
            val valor = edtValor.text.toString()

            if ((produto.isNotEmpty()) && (qtd.isNotEmpty()) && (valor.isNotEmpty())){
                //Enviando o item para a lista
                //val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)

                //val idProduto: Long = salvarProduto(prod)
                val idProduto: Long = salvarProduto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)
                //Verifica se a inserção funcionou
                if (idProduto != -1L){
                    //produtosGlobal.add(prod)
                    Toast.makeText(this, "Produto inserido com sucesso!.", Toast.LENGTH_SHORT).show()

                    edtProduto.text.clear()
                    edtQtdProduto.text.clear()
                    edtValor.text.clear()
                }
                else Toast.makeText(this, "Ocorreu um erro ao inserir o produto.", Toast.LENGTH_LONG).show()
            }else {
                edtProduto.error = if(edtProduto.text.isEmpty()) "Informe um produto" else null
                edtQtdProduto.error = if(edtQtdProduto.text.isEmpty()) "Informe a quantidade" else null
                edtValor.error = if(edtValor.text.isEmpty()) "Informe o valor" else null
            }
        }

        imgFotoProduto.setOnClickListener {
            abrirGaleria()
        }
    }

    private fun salvarProduto(nome: String, quantidade: Int, valor: Double, foto: Bitmap?): Long {
        val db: SQLiteDatabase = helper.writableDatabase
        val values = ContentValues()
        values.put("nome", nome)
        values.put("quantidade", quantidade.toString())
        values.put("valor", valor)
        values.put("foto", foto?.toByteArray())
        return db.insert("produtos", null, values)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            if (data != null){
                //Lendo a URI da imagem
                val inputStream = contentResolver.openInputStream(data.data!!)

                //Transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)

                //Exibir a imagem no aplicativo
                imgFotoProduto.setImageBitmap(imageBitMap)
            }
        }
    }

    private fun abrirGaleria(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        //Definindo filtro para imagens
        intent.type = "image/*"

        //Inicializando a activity com o resultado
        resultLauncher.launch(Intent.createChooser(intent, "Selecione uma imagem"))
    }
}
