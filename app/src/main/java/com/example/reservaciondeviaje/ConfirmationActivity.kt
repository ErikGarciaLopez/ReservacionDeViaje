package com.example.reservaciondeviaje

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reservaciondeviaje.databinding.ActivityConfirmationBinding

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

            Log.d("APPLOGS", "Nombre: $nombre Apellido: $apellido Correo: $correo Origen: $origen")
            Log.d("APPLOGS", "FechaSalida: $fechaSalida HoraSalida: $horaSalida Destino: $destino")
            Log.d("APPLOGS", "FechaRegreso: $fechaRegreso HoraRegreso: $horaRegreso")
            Log.d("APPLOGS", "AsientoIda: $asientoIda AsientoRegreso: $asientoRegreso")


        }


    }
}