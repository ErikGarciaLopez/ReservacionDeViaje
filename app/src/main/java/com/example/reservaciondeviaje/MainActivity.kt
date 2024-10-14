package com.example.reservaciondeviaje

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reservaciondeviaje.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var fechaSalida: String? = null   //Variable global para fechaSalida
    private var fechaRegreso: String? = null  //Variable global para fechaRegreso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Spinner Destinos
        val destinos = arrayOf("Cancún", "Ciudad de México", "Guadalajara", "Monterrey", "Los Cabos", "Tijuana")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, destinos)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDestino.adapter = spinnerAdapter

        //Fecha Salida
        binding.btnFechaSalida.setOnClickListener {
            mostrarDatePicker(binding.btnFechaSalida, "salida")
        }

        //Spinner HoraSalida
        val horarios = arrayOf("7:00 a.m.", "10:00 a.m.", "2:00 p.m.", "6:00 p.m.")
        val spinnerAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHoraSalida.adapter = spinnerAdapter2

        //Spiner HoraRegreso
        val spinnerAdapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
        spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHoraRegreso.adapter = spinnerAdapter3

        //Fecha Regreso
        binding.btnFechaRegreso.setOnClickListener {
            mostrarDatePicker(binding.btnFechaRegreso, "regreso")
        }

        binding.btnReservar.setOnClickListener {

            //Nombre
            val nombre = binding.etNombre.text.toString()
            if (nombre.isEmpty()){
                mostrarError(getString(R.string.falta_nombre), binding.etNombre)
                return@setOnClickListener //Salir del metodo
            }

            //Apellido
            val apellido = binding.etApellido.text.toString()
            if (apellido.isEmpty()){
                mostrarError(getString(R.string.falta_apellido), binding.etApellido)
                return@setOnClickListener //Salir del metodo
            }

            //Correo
            val correo = binding.etCorreo.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                mostrarError(getString(R.string.correo_no_valido), binding.etCorreo)
                return@setOnClickListener //Salir del metodo
            }

            //Origen
            val origen = binding.etOrigen.text.toString()

            //FechaSalida: global
            if (fechaSalida == null) {
                Toast.makeText(this, getString(R.string.falta_fecha_salida), Toast.LENGTH_SHORT).show()
                return@setOnClickListener //Salir del metodo
            }

            //HoraSalida
            val horaSalida = binding.spinnerHoraSalida.selectedItem.toString()

            //AsientoIda
            val asiendoIda = asientoAleatorio()

            // Destino
            val destinoSeleccionado = binding.spinnerDestino.selectedItem.toString()

            //FechaRegreso: global
            if (fechaRegreso == null) {
                Toast.makeText(this, getString(R.string.falta_fecha_regreso), Toast.LENGTH_SHORT).show()
                return@setOnClickListener //Salir del metodo
            }

            //Hora regreso
            val horaRegreso = binding.spinnerHoraRegreso.selectedItem.toString()

            //AsientoRegreso
            val asientoRegreso = asientoAleatorio()

            //Num viajero (opcional)
            val numViajero = binding.etNumViajero.text.toString().toIntOrNull() ?: 0

            val params = bundleOf(
                "nombre" to nombre,
                "apellido" to apellido,
                "correo" to correo,
                "origen" to origen,
                "fechaSalida" to fechaSalida,
                "horaSalida" to horaSalida,
                "asientoIda" to asiendoIda,
                "destino" to destinoSeleccionado,
                "fechaRegreso" to fechaRegreso,
                "horaRegreso" to horaRegreso,
                "asientoRegreso" to asientoRegreso,
                "numViajero" to numViajero
            )

            val intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtras(params) //Mandamos el Bundle
            startActivity(intent)

        }

    }

    private fun mostrarDatePicker(button: Button, tipoFecha: String) {
        //Fecha actual
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        //DatePicker
        val datePickerDialog = DatePickerDialog(this, { _, selectedAnio, selectedMes, selectedDia ->
            // Aquí se maneja la fecha seleccionada
            val fechaSeleccionada = "$selectedDia/${selectedMes + 1}/$selectedAnio"

            if (tipoFecha == "salida"){
                fechaSalida = fechaSeleccionada
                button.text = fechaSalida  // Mostrar la fecha en el botón
            } else if (tipoFecha == "regreso"){
                fechaRegreso = fechaSeleccionada
                button.text = fechaRegreso  // Mostrar la fecha en el botón
            }

        }, anio, mes, dia)

        datePickerDialog.show()
    }

    private fun asientoAleatorio(): String {
        val fila = (1..25).random()
        val columna = ('A'..'D').random()
        return "$fila$columna"
    }

    private fun mostrarError(mensaje: String, campo: android.widget.EditText){
        //Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        campo.error = mensaje
        campo.requestFocus()
    }


}