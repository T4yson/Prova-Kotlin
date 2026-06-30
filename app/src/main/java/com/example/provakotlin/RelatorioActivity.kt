package com.example.provakotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class RelatorioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorio)

        val txtOperador: TextView = findViewById(R.id.txtValOperador)
        val txtLinha: TextView = findViewById(R.id.txtValLinha)
        val txtPecas: TextView = findViewById(R.id.txtValPecas)
        val txtTempoTotal: TextView = findViewById(R.id.txtValTempoTotal)
        val txtTempoMedio: TextView = findViewById(R.id.txtValTempoMedio)
        val btnVoltar: Button = findViewById(R.id.btnVoltar)

        val operador = intent.getStringExtra("OPERADOR") ?: "N/A"
        val linha = intent.getStringExtra("LINHA") ?: "N/A"
        val pecas = intent.getIntExtra("PECAS", 0)
        val tempoTotalMillis = intent.getLongExtra("TEMPO_TOTAL", 0L)
        val tempoMedioSegundos = intent.getDoubleExtra("TEMPO_MEDIO", 0.0)

        txtOperador.text = operador.uppercase(Locale.getDefault())
        txtLinha.text = linha.uppercase(Locale.getDefault())

        txtPecas.text = pecas.toString()

        val segundosTotais = tempoTotalMillis / 1000
        val minutos = segundosTotais / 60
        val segundosRestantes = segundosTotais % 60
        txtTempoTotal.text = String.format(Locale.getDefault(), "%02d min %02d seg", minutos, segundosRestantes)

        txtTempoMedio.text = String.format(Locale.getDefault(), "%.2f segundos", tempoMedioSegundos)

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
