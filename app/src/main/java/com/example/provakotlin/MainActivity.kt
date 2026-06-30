package com.example.provakotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var edtOperador: TextInputEditText
    private lateinit var edtLinha: TextInputEditText
    private lateinit var txtContador: TextView

    private lateinit var btnStart: Button
    private lateinit var btnPeca: Button
    private lateinit var btnFinalizar: Button

    private var contador = 0
    private var tempoInicio = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtOperador = findViewById(R.id.edtOperador)
        edtLinha = findViewById(R.id.edtLinha)
        txtContador = findViewById(R.id.txtContador)
        btnStart = findViewById(R.id.btnStart)
        btnPeca = findViewById(R.id.btnPeca)
        btnFinalizar = findViewById(R.id.btnFinalizar)

        btnPeca.isEnabled = false

        btnStart.setOnClickListener {
            val operador = edtOperador.text.toString()
            val linha = edtLinha.text.toString()

            if (operador.isNotEmpty() && linha.isNotEmpty()) {
                tempoInicio = System.currentTimeMillis()
                
                contador = 0
                txtContador.text = contador.toString()

                edtOperador.isEnabled = false
                edtLinha.isEnabled = false
                
                btnPeca.isEnabled = true
                btnStart.isEnabled = false
                
                Toast.makeText(this, "Produção Iniciada!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Preencha o Operador e a Linha!", Toast.LENGTH_SHORT).show()
            }
        }

        btnPeca.setOnClickListener {
            contador++
            txtContador.text = contador.toString()
        }

        btnFinalizar.setOnClickListener {
            if (tempoInicio == 0L) {
                Toast.makeText(this, "Inicie a produção primeiro!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tempoFim = System.currentTimeMillis()
            val tempoTotalMillis = tempoFim - tempoInicio
            
            val tempoMedioSegundos = if (contador > 0) {
                (tempoTotalMillis / 1000.0) / contador
            } else {
                0.0
            }

            val intent = Intent(this, RelatorioActivity::class.java).apply {
                putExtra("OPERADOR", edtOperador.text.toString())
                putExtra("LINHA", edtLinha.text.toString())
                putExtra("PECAS", contador)
                putExtra("TEMPO_TOTAL", tempoTotalMillis)
                putExtra("TEMPO_MEDIO", tempoMedioSegundos)
            }
            startActivity(intent)
            
            resetApp()
        }
    }

    private fun resetApp() {
        btnStart.isEnabled = true
        btnPeca.isEnabled = false
        edtOperador.isEnabled = true
        edtLinha.isEnabled = true
        edtOperador.text?.clear()
        edtLinha.text?.clear()
        contador = 0
        txtContador.text = "0"
        tempoInicio = 0L
    }
}
