package com.adonyskevin.listadecompras

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    //private var helper: ListaComprasDatabase = ListaComprasDatabase.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val produtosAdapter = ProdutoAdapter(this)

        val lista = findViewById<ListView>(R.id.list_produtos)
        lista.adapter = produtosAdapter

        val btnAdicionar = findViewById<Button>(R.id.btn_adicionar)
        btnAdicionar.setOnClickListener{
            //Criando intent explícita
            val intent = Intent(this, CadastroActivity::class.java)

            //Iniciando a Activity
            startActivity(intent)
        }

        lista.setOnItemLongClickListener { adapterView, view, i, l ->
            val item = produtosAdapter.getItem(i)
            produtosAdapter.remove(item)
            produtosGlobal.remove(item)
            preencheTotal()

            //Retorno indicando que o click foi realizado com sucesso
            true
        }
        //helper = ListaComprasDatabase(this)
    }

    override fun onResume() {
        super.onResume()
        val adapter = findViewById<ListView>(R.id.list_produtos).adapter as ProdutoAdapter
        adapter.clear()
        adapter.addAll(produtosGlobal)
        preencheTotal()
    }

    @SuppressLint("SetTextI18n")
    private fun preencheTotal() {
        val soma = produtosGlobal.sumOf{it.valor * it.quantidade}
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        findViewById<TextView>(R.id.txt_total).text = "TOTAL: ${f.format(soma)}"
    }
}
