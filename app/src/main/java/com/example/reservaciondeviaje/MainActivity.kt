package com.example.reservaciondeviaje

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

        //Nombre
        val nombre = binding.etNombre.text.toString();

        //Apellido
        val apellido = binding.etNombre.text.toString();

        //Correo
        val correo = binding.etCorreo.text.toString();

        //if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
        //    Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show()
        //}

        //Origen
        val origen = binding.etOrigen.text.toString();


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


        //Fecha Regreso
        binding.btnFechaRegreso.setOnClickListener {
            mostrarDatePicker(binding.btnFechaRegreso, "regreso")
        }

        //Asiento
        // TODO: Asientos

        //Validar que esten llenos los campos
        binding.etNombre.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                //binding.btnReservar.isEnabled = binding.etNombre.text.toString().isNotEmpty()
                binding.btnReservar.isEnabled = validateFields()
            }

        })

    }

    private fun mostrarDatePicker(button: Button, tipoFecha: String) {
        //Fecha actual
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        //DatePicker
        val datePickerDialog = DatePickerDialog(this, { _, anio, mes, dia ->
            // Aquí se maneja la fecha seleccionada
            val fechaSeleccionada = "$dia/${mes + 1}/$anio"

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

    private fun validateFields(): Boolean{
        return binding.etNombre.text.toString().isNotEmpty() &&
                binding.etApellido.text.toString().isNotEmpty() &&
                binding.etCorreo.text.toString().isNotEmpty() &&
                binding.etOrigen.text.toString().isNotEmpty() &&
                binding.spinnerDestino.toString().isNotEmpty() &&
                binding.btnFechaSalida.text.toString().isNotEmpty() &&
                binding.spinnerHoraSalida.toString().isNotEmpty() &&
                binding.btnFechaRegreso.toString().isNotEmpty()
                // TODO: Faltan mas validaciones
    }

}