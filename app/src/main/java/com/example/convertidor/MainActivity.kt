package com.example.convertidor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.convertidor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val unidadesAdapter = ArrayAdapter.createFromResource(this, R.array.unidades, android.R.layout.simple_spinner_item)
        binding.spnCombo.adapter = unidadesAdapter

        val longitudAdapter = ArrayAdapter.createFromResource(this, R.array.longitud, android.R.layout.simple_spinner_item)
        val masaAdapter = ArrayAdapter.createFromResource(this, R.array.masa, android.R.layout.simple_spinner_item)
        val tiempoAdapter = ArrayAdapter.createFromResource(this, R.array.tiempo, android.R.layout.simple_spinner_item)

        binding.spnCombo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> setAdapter(longitudAdapter)
                    1 -> setAdapter(masaAdapter)
                    2 -> setAdapter(tiempoAdapter)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        binding.btnConvertir.setOnClickListener {
            val tipoUnidad = binding.spnCombo.selectedItemPosition

            val unidad1 = binding.spnNumber1.selectedItemPosition
            val valor1 = binding.tieNumber1.text.toString().toFloat()

            val unidad2 = binding.spnNumber2.selectedItemPosition
            var valor2 : Float = 0.0f

            valor2 = when(tipoUnidad){
                0 -> obtenerLongitud(unidad1, valor1, unidad2)
                1 -> obtenerMasa(unidad1, valor1, unidad2)
                2 -> obtenerTiempo(unidad1, valor1, unidad2)
                else -> 0.0f
            }

            binding.tieNumber2.setText(valor2.toString())

        }
    }

    private fun setAdapter(adapter :ArrayAdapter<CharSequence>){
        binding.spnNumber1.adapter = adapter
        binding.spnNumber2.adapter = adapter
    }

    private fun obtenerLongitud(unidad1 : Int, valor1 : Float, unidad2 : Int) : Float {
        val factor = when(unidad1){
            //Kilometro
            0 ->{
                when(unidad2){
                    0 -> 1.0f
                    1 -> 1_000.0f
                    2 -> 100_000.0f
                    3 -> 1_000_000.0f
                    else -> 0.0f
                }
            }
            //Metro
            1 -> {
                when(unidad2){
                    0 -> 0.001f
                    1 -> 1.0f
                    2 -> 100.0f
                    3 -> 1_000.0f
                    else -> 0.0f
                }
            }
            //Centrimetro
            2 -> {
                when(unidad2){
                    0 -> 0.000_1f
                    1 -> 0.01f
                    2 -> 1.0f
                    3 -> 10f
                    else -> 0.0f
                }
            }
            //Milimetro
            3 -> {
                when(unidad2){
                    0 -> 0.000_001f
                    1 -> 0.001f
                    2 -> 0.1f
                    3 -> 1.0f
                    else -> 0.0f
                }
            }
            else -> 0.0f
        }

        return factor * valor1
    }

    private fun obtenerMasa(unidad1: Int, valor1: Float, unidad2: Int): Float {
        val factor = when(unidad1){
            //Kilogramo
            0 ->{
                when(unidad2){
                    0 -> 1.0f
                    1 -> 1_000.0f
                    2 -> 1_000_000.0f
                    else -> 0.0f
                }
            }
            //Gramo
            1 -> {
                when(unidad2){
                    0 -> 0.001f
                    1 -> 1.0f
                    2 -> 1_000.0f
                    else -> 0.0f
                }
            }
            //Miligramo
            2 -> {
                when(unidad2){
                    0 -> 0.000_001f
                    1 -> 0.001f
                    2 -> 1.0f
                    else -> 0.0f
                }
            }
            else -> 0.0f
        }

        return factor * valor1

    }

    private fun obtenerTiempo(unidad1: Int, valor1: Float, unidad2: Int): Float {
        val factor = when(unidad1){
            //Día
            0 ->{
                when(unidad2){
                    0 -> 1.0f
                    1 -> 24.0f
                    2 -> 1_440.0f
                    3 -> 86_400.0f
                    else -> 0.0f
                }
            }
            //Hora
            1 -> {
                when(unidad2){
                    0 -> 0.041_666_6667f
                    1 -> 1.0f
                    2 -> 60.0f
                    3 -> 3_600.0f
                    else -> 0.0f
                }
            }
            //Minutos
            2 -> {
                when(unidad2){
                    0 -> 0.000_694_444_4f
                    1 -> 0.016_666_666_7f
                    2 -> 1.0f
                    3 -> 60.00f
                    else -> 0.0f
                }
            }
            //Segundos
            3 -> {
                when(unidad2){
                    0 -> 0.000_011_574_1f
                    1 -> 0.000_277_777_8f
                    2 -> 0.0166_666_667f
                    3 -> 1.0f
                    else -> 0.0f
                }
            }
            else -> 0.0f
        }

        return factor * valor1
    }
}