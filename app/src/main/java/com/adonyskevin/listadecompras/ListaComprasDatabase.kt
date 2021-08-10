package com.adonyskevin.listadecompras

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val NOME_BANCO = "ListaCompras.db"
val NOME_TABELA = "produtos"
val VERSAO_BANCO = 1

class ListaComprasDatabase(context: Context) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_BANCO) {
    //Padrão Singleton
    companion object {
        private var instance: ListaComprasDatabase? = null
        @Synchronized
        fun getInstance(ctx: Context): ListaComprasDatabase {
            if (instance == null) {
                instance = ListaComprasDatabase(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Criação de tabelas
        val sql =   "CREATE TABLE $NOME_TABELA (\n" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "nome varchar(100) NOT NULL,\n" +
                "quantidade INTEGER NOT NULL,\n" +
                "valor REAL NOT NULL,\n" +
                "foto BLOB NULL\n" +
                ");"

        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Atualizar a base de dados
        val sql = "DROP TABLE IF EXISTS $NOME_TABELA;"
        db!!.execSQL(sql)
        onCreate(db)
    }
}

// Acesso a propriedade pelo contexto
val Context.database: ListaComprasDatabase
    get() = ListaComprasDatabase.getInstance(getApplicationContext())