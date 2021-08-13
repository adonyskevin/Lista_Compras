package com.adonyskevin.listadecompras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

class ProdutoAdapter(contexto: Context) : ArrayAdapter<Produto>(contexto, 0){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v: View

        if (convertView != null) {
            v = convertView
        } else {
            //inflando o layout
            v = LayoutInflater.from(context)
                .inflate(R.layout.list_view_item, parent, false)
        }
        val item = getItem(position)

        val txtProduto = v.findViewById<TextView>(R.id.txt_item_produto)
        val txtQtd = v.findViewById<TextView>(R.id.txt_item_qtd)
        val txtValor = v.findViewById<TextView>(R.id.txt_item_valor)
        val imgProduto = v.findViewById<ImageView>(R.id.img_item_foto)

        if (item != null){
            txtProduto.text = item.nome
            txtQtd.text = item.quantidade.toString()
            txtValor.text = item.valor.toString()

            //if (item.foto != null){
                imgProduto.setImageBitmap(item.foto)
            //}
        }

        //obtendo a instância do objeto de formatação
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        //formatando a variável no formato moeda BR 0,00
        txtValor.setText(f.format(item!!.valor))

        return v
    }
}