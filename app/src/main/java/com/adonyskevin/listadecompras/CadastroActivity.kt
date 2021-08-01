package com.adonyskevin.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val edt_produto = findViewById<EditText>(R.id.edt_produto)
        val edt_qtd_produto = findViewById<EditText>(R.id.edt_qtd_produto)
        val edt_valor = findViewById<EditText>(R.id.edt_valor)
        val btn_inserir = findViewById<Button>(R.id.btn_inserir)

        btn_inserir.setOnClickListener {
            //Recebendo os valores digitados pelo usuário
            val produto = edt_produto.text.toString()
            val qtd = edt_qtd_produto.text.toString()
            val valor = edt_valor.text.toString()

            if ((produto.isNotEmpty()) && (qtd.isNotEmpty()) && (valor.isNotEmpty())){
                //Enviando o item para a lista
                val prod = Produto(produto, qtd.toInt(), valor.toDouble())
                
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
    }
}