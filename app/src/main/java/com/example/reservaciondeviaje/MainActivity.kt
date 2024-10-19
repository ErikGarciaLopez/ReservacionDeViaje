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

    private var fechaSalida: Calendar? = null   //Variable global para fechaSalida
    private var fechaRegreso: Calendar? = null  //Variable global para fechaRegreso

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

        val ciudades = arrayOf("Cancún", "Ciudad de México", "Guadalajara", "Monterrey", "Los Cabos", "Tijuana")

        // Spinner Origen
        val spinnerOrigenAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ciudades)
        spinnerOrigenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOrigen.adapter = spinnerOrigenAdapter

        // Spinner Destinos
        val spinnerDestinoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ciudades)
        spinnerDestinoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDestino.adapter = spinnerDestinoAdapter

        //Fecha Salida
        binding.btnFechaSalida.setOnClickListener {
            mostrarDatePicker(binding.btnFechaSalida, "salida")
        }

        //Spinner HoraSalida
        val horarios = arrayOf("7:00 a.m.", "10:00 a.m.", "2:00 p.m.", "6:00 p.m.")
        val spinnerHoraSalidaAdapter  = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
        spinnerHoraSalidaAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHoraSalida.adapter = spinnerHoraSalidaAdapter

        //Spiner HoraRegreso
        val spinnerHoraRegresoAdapter  = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
        spinnerHoraRegresoAdapter .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHoraRegreso.adapter = spinnerHoraRegresoAdapter

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
            val origen = binding.spinnerOrigen.selectedItem.toString()

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

            //Origen!=Destino
            if (origen == destinoSeleccionado) {
                Toast.makeText(this, getString(R.string.origen_diferente_destino), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
            val numViajeroTexto = binding.etNumViajero.text.toString()

            // Validación de longitud
            if (numViajeroTexto.isNotEmpty() && numViajeroTexto.length != 8) {
                mostrarError(getString(R.string.error_num_viajero), binding.etNumViajero)
                return@setOnClickListener
            }
            val numViajero = numViajeroTexto.toIntOrNull() ?: 0

            val params = bundleOf(
                "nombre" to nombre,
                "apellido" to apellido,
                "correo" to correo,
                "origen" to origen,
                "fechaSalida" to formatFecha(fechaSalida!!),
                "horaSalida" to horaSalida,
                "asientoIda" to asiendoIda,
                "destino" to destinoSeleccionado,
                "fechaRegreso" to formatFecha(fechaRegreso!!),
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
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedAnio, selectedMes, selectedDia ->
            val fechaSeleccionada = Calendar.getInstance().apply {
                set(selectedAnio, selectedMes, selectedDia)
            }

            when (tipoFecha) {
                "salida" -> {
                    if (fechaSeleccionada.before(Calendar.getInstance())) {
                        Toast.makeText(this, getString(R.string.fecha_salida_pasada), Toast.LENGTH_SHORT).show()
                    } else {
                        fechaSalida = fechaSeleccionada
                        button.text = formatFecha(fechaSeleccionada)
                        // Resetear la fecha de regreso si es anterior a la nueva fecha de salida
                        if (fechaRegreso != null && fechaRegreso!!.before(fechaSalida)) {
                            fechaRegreso = null
                            binding.btnFechaRegreso.text = getString(R.string.fecha_de_regreso)
                        }
                    }
                }
                "regreso" -> {
                    if (fechaSalida == null) {
                        Toast.makeText(this, getString(R.string.seleccionar_fecha_salida_primero), Toast.LENGTH_SHORT).show()
                    } else if (fechaSeleccionada.before(fechaSalida) || fechaSeleccionada == fechaSalida) {
                        Toast.makeText(this, getString(R.string.fecha_regreso_invalida), Toast.LENGTH_SHORT).show()
                    } else {
                        fechaRegreso = fechaSeleccionada
                        button.text = formatFecha(fechaSeleccionada)
                    }
                }
            }
        }, anio, mes, dia)

        if (tipoFecha == "regreso" && fechaSalida != null) {
            datePickerDialog.datePicker.minDate = fechaSalida!!.timeInMillis + 86400000 // +1 día en milisegundos
        } else {
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        }

        datePickerDialog.show()
    }

    private fun formatFecha(fecha: Calendar): String {
        return "${fecha.get(Calendar.DAY_OF_MONTH)}/${fecha.get(Calendar.MONTH) + 1}/${fecha.get(Calendar.YEAR)}"
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