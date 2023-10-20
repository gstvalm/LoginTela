package com.example.logintela

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.logintela.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#3171D4")

        // Conecte-se ao banco de dados.
        db = openOrCreateDatabase("database.sqlite", SQLiteDatabase.OPEN_READWRITE)

        // Crie a tabela de usuários, se ela ainda não existir.
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, email TEXT NOT NULL UNIQUE, rm TEXT NOT NULL UNIQUE, celular TEXT, senha TEXT NOT NULL)")

        binding.btRegistro.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val email = binding.editEmail.text.toString()
            val rm = binding.editRM.text.toString()
            val senha = binding.editSenha.text.toString()

            when {
                nome.isEmpty() -> {
                    binding.txtInputLayoutNome.error = "Preencha o Nome"
                }
                email.isEmpty() -> {
                    binding.txtInputLayoutEmail.error = "Preencha o Email"
                }
                rm.isEmpty() -> {
                    binding.txtInputLayoutRM.error = "Preencha o RM"
                }
                senha.isEmpty() -> {
                    binding.txtInputLayoutSenha.error = "Preencha a Senha"
                }
                else -> {
                    // Registre o usuário no banco de dados.
                    registrarUsuario(nome, email, rm, senha)
                }
            }
        }
    }

    // Registre um novo usuário no banco de dados.
    fun registrarUsuario(nome: String, email: String, rm: String, senha: String) {
        val values = ContentValues()
        values.put("nome", nome)
        values.put("email", email)
        values.put("rm", rm)
        values.put("senha", senha)

        db.insert("usuarios", null, values)
    }

    // Feche o banco de dados.
    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}
