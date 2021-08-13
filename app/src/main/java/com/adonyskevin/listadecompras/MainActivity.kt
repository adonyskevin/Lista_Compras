package com.adonyskevin.listadecompras

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var helper: ListaComprasDatabase
    private lateinit var produtosAdapter: ProdutoAdapter

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        produtosAdapter = ProdutoAdapter(this)

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
            val idProduto = item!!.id

            excluirProduto(idProduto)
            Toast.makeText(this, "Produto excluído com sucesso!", Toast.LENGTH_LONG).show()

            listarProdutos(this.helper)
            true
        }
    }

    private fun excluirProduto(idProduto: Int) {
        val db = helper.writableDatabase
        db.delete("produtos", "_id = $idProduto", null)
    }

    @SuppressLint("Recycle")
    private fun listarProdutos(helper: ListaComprasDatabase) {
        val db = helper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM produtos", null)
        val totalRegistros: Int = cursor.count

        val adapter = findViewById<ListView>(R.id.list_produtos).adapter as ProdutoAdapter
        adapter.clear()
        produtosGlobal.clear()

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
                    cursor.getBlob(cursor.getColumnIndex("foto"))?.toBitmap()
                )

                produtosGlobal.add(prod)
                cursor.moveToNext()
                i++
            }
            adapter.addAll(produtosGlobal)
            preencheTotal()
        }
    }

    override fun onResume() {
        super.onResume()
        listarProdutos(this.helper)
    }

    @SuppressLint("SetTextI18n")
    private fun preencheTotal() {
        val soma = produtosGlobal.sumOf{it.valor * it.quantidade}
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        findViewById<TextView>(R.id.txt_total).text = "TOTAL: ${f.format(soma)}"
    }
}
