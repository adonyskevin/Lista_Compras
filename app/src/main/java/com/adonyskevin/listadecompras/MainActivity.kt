package com.adonyskevin.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1)

        val lista = findViewById<ListView>(R.id.list_produtos)
        lista.adapter = adapter

        val edt_produto = findViewById<EditText>(R.id.edt_produto)

        val btn_inserir = findViewById<Button>(R.id.btn_inserir)
        btn_inserir.setOnClickListener {
            val produto = edt_produto.text.toString()
            if (produto.isNotEmpty()){
                adapter.add(produto)
                edt_produto.text.clear()
            }else
                edt_produto.error = "Informe um produto"
        }

        lista.setOnItemLongClickListener { adapterView, view, i, l ->
            val item = adapter.getItem(i)
            adapter.remove(item)

            //Retorno indicando que o click foi realizado com sucesso
            true
        }
    }
}
