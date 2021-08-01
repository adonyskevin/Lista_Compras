package com.adonyskevin.listadecompras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val produtosAdapter = ProdutoAdapter(this)

        val lista = findViewById<ListView>(R.id.list_produtos)
        lista.adapter = produtosAdapter

        val btn_adicionar = findViewById<Button>(R.id.btn_adicionar)
        btn_adicionar.setOnClickListener{
            //Criando intent explícita
            val intent = Intent(this, CadastroActivity::class.java)

            //Iniciando a Activity
            startActivity(intent)
        }

        lista.setOnItemLongClickListener { adapterView, view, i, l ->
            val item = produtosAdapter.getItem(i)
            produtosAdapter.remove(item)

            //Retorno indicando que o click foi realizado com sucesso
            true
        }
    }

    override fun onResume() {
        super.onResume()
        val adapter = findViewById<ListView>(R.id.list_produtos).adapter as ProdutoAdapter
        adapter.clear()
        adapter.addAll(produtosGlobal)
    }
}
