package com.adonyskevin.listadecompras

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.database.getBlobOrNull
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var helper: ListaComprasDatabase

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val produtosAdapter = ProdutoAdapter(this)

        val lista = findViewById<ListView>(R.id.list_produtos)
        lista.adapter = produtosAdapter

        helper = ListaComprasDatabase(this)

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
    }

    @SuppressLint("Recycle")
    private fun preencherProdutos(helper: ListaComprasDatabase) {
        val db: SQLiteDatabase = helper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM produtos", null)
        val totalRegistros: Int = cursor.count

        if (totalRegistros > 0){
            cursor.moveToFirst()
            var prod: Produto?
            var i = 0
            while (i < totalRegistros){
                prod = Produto(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("nome")).toString(),
                    cursor.getInt(cursor.getColumnIndex("quantidade")),
                    cursor.getDouble(cursor.getColumnIndex("valor")),
                    cursor.getBlobOrNull(cursor.getColumnIndex("foto"))?.toBitmap()
                )

                produtosGlobal.add(prod)
                cursor.moveToNext()
                i++;
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val adapter = findViewById<ListView>(R.id.list_produtos).adapter as ProdutoAdapter
        adapter.clear()
        produtosGlobal.clear()
        preencherProdutos(this.helper)
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
