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
        val btn_inserir = findViewById<Button>(R.id.btn_inserir)

        btn_inserir.setOnClickListener {
            val produto = edt_produto.text.toString()
            if (produto.isNotEmpty()){
                //Enviar o item pra lista
                //adapter.add(produto)
                edt_produto.text.clear()
            }else
                edt_produto.error = "Informe um produto"
        }
    }
}