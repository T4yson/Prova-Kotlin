package com.example.provakotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.provakotlin.model.ProductionResult

class MainViewModel : ViewModel() {

    private val _contador = MutableLiveData(0)
    val contador: LiveData<Int> = _contador

    private val _producaoIniciada = MutableLiveData(false)
    val producaoIniciada: LiveData<Boolean> = _producaoIniciada

    private var tempoInicio = 0L

    fun iniciarProducao() {
        tempoInicio = System.currentTimeMillis()
        _contador.value = 0
        _producaoIniciada.value = true
    }

    fun registrarPeca() {
        if (_producaoIniciada.value == true) {
            _contador.value = (_contador.value ?: 0) + 1
        }
    }

    fun finalizarProducao(): ProductionResult? {
        if (tempoInicio == 0L) return null

        val tempoFim = System.currentTimeMillis()
        val tempoTotalMillis = tempoFim - tempoInicio
        val pecas = _contador.value ?: 0
        
        val tempoMedioSegundos = if (pecas > 0) {
            (tempoTotalMillis / 1000.0) / pecas
        } else {
            0.0
        }

        return ProductionResult(tempoTotalMillis, tempoMedioSegundos, pecas)
    }

    fun reset() {
        _contador.value = 0
        _producaoIniciada.value = false
        tempoInicio = 0L
    }
}
