package com.example.reservaciondeviaje

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reservaciondeviaje.databinding.ActivityConfirmationBinding
import kotlin.random.Random

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val params = intent.extras

        params?.let { args ->
            val nombre = args.getString("nombre", "")
            val apellido = args.getString("apellido", "")
            val correo = args.getString("correo", "")
            val origen = args.getString("origen", "")
            val fechaSalida = args.getString("fechaSalida", "")
            val horaSalida = args.getString("horaSalida", "")
            val asientoIda = args.getString("asientoIda", "")
            val destino = args.getString("destino", "")
            val fechaRegreso = args.getString("fechaRegreso", "")
            val horaRegreso = args.getString("horaRegreso", "")
            val asientoRegreso = args.getString("asientoRegreso", "")
            val numViajero = args.getInt("numViajero", 0)

            Log.d("APPLOGS", "Nombre: $nombre Apellido: $apellido Correo: $correo Origen: $origen")
            Log.d("APPLOGS", "FechaSalida: $fechaSalida HoraSalida: $horaSalida Destino: $destino")
            Log.d("APPLOGS", "FechaRegreso: $fechaRegreso HoraRegreso: $horaRegreso")
            Log.d("APPLOGS", "AsientoIda: $asientoIda AsientoRegreso: $asientoRegreso")
            Log.d("APPLOGS", "NumViajero: $numViajero")

            val nombreCompleto = "$nombre $apellido"
            binding.tvNombre.text = nombreCompleto
            binding.tvOrigen2.text = origen
            val fechayhoraIda = "$fechaSalida $horaSalida"
            binding.tvFechaIda2.text = fechayhoraIda
            binding.tvAsientoIda2.text = asientoIda
            binding.tvDestino2.text = destino
            val fechayhoraRegreso = "$fechaRegreso $horaRegreso"
            binding.tvFechaRegreso2.text = fechayhoraRegreso
            binding.tvAsientoRegreso2.text = asientoRegreso
            val precio = calcMonto()
            binding.tvPrecio2.text = String.format("%.2f$", precio)

            if (numViajero != 0) {
                binding.tvPrecio3.apply {
                    text = context.getString(R.string.precio_con_descuento, calcDescuento(precio), 15)
                    visibility = View.VISIBLE
                }
            }




        }


    }

    private fun calcMonto (): Double {
        val precio = Random.nextInt(3000, 10001).toDouble()
        return precio
    }

    private fun calcDescuento (precio: Double): Double {
        return precio * 0.85
    }

}