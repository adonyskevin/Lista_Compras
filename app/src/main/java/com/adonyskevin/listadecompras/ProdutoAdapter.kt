package com.adonyskevin.listadecompras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
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

        val txt_produto = v.findViewById<TextView>(R.id.txt_item_produto)
        val txt_qtd = v.findViewById<TextView>(R.id.txt_item_qtd)
        val txt_valor = v.findViewById<EditText>(R.id.txt_item_valor)
        val img_produto = v.findViewById<ImageView>(R.id.img_item_foto)

        if (item != null){
            txt_produto.text = item.nome
            txt_qtd.text = item.quantidade.toString()
            txt_valor.setText(item.valor.toString())

            if (item.foto != null){
                img_produto.setImageBitmap(item.foto)
            }
        }

        //obtendo a instância do objeto de formatação
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        //formatando a variável no formato moeda BR 0,00
        txt_valor.setText(f.format(item!!.valor))

        return v
    }
}