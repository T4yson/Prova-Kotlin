package com.example.provakotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.provakotlin.viewmodel.MainViewModel
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var edtOperador: TextInputEditText
    private lateinit var edtLinha: TextInputEditText
    private lateinit var txtContador: TextView

    private lateinit var btnStart: Button
    private lateinit var btnPeca: Button
    private lateinit var btnFinalizar: Button

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupObservers()
        setupClickListeners()
    }

    private fun setupViews() {
        edtOperador = findViewById(R.id.edtOperador)
        edtLinha = findViewById(R.id.edtLinha)
        txtContador = findViewById(R.id.txtContador)
        btnStart = findViewById(R.id.btnStart)
        btnPeca = findViewById(R.id.btnPeca)
        btnFinalizar = findViewById(R.id.btnFinalizar)
    }

    private fun setupObservers() {
        viewModel.contador.observe(this) { count ->
            txtContador.text = count.toString()
        }

        viewModel.producaoIniciada.observe(this) { iniciada ->
            btnPeca.isEnabled = iniciada
            btnStart.isEnabled = !iniciada
            edtOperador.isEnabled = !iniciada
            edtLinha.isEnabled = !iniciada
        }
    }

    private fun setupClickListeners() {
        btnStart.setOnClickListener {
            val operador = edtOperador.text.toString()
            val linha = edtLinha.text.toString()

            if (operador.isNotEmpty() && linha.isNotEmpty()) {
                viewModel.iniciarProducao()
                Toast.makeText(this, getString(R.string.msg_producao_iniciada), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.msg_erro_campos), Toast.LENGTH_SHORT).show()
            }
        }

        btnPeca.setOnClickListener {
            viewModel.registrarPeca()
        }

        btnFinalizar.setOnClickListener {
            val result = viewModel.finalizarProducao()
            
            if (result == null) {
                Toast.makeText(this, getString(R.string.msg_erro_inicio), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, RelatorioActivity::class.java).apply {
                putExtra("OPERADOR", edtOperador.text.toString())
                putExtra("LINHA", edtLinha.text.toString())
                putExtra("PECAS", result.totalPecas)
                putExtra("TEMPO_TOTAL", result.tempoTotalMillis)
                putExtra("TEMPO_MEDIO", result.tempoMedioSegundos)
            }
            startActivity(intent)
            
            resetUI()
        }
    }

    private fun resetUI() {
        viewModel.reset()
        edtOperador.text?.clear()
        edtLinha.text?.clear()
    }
}
