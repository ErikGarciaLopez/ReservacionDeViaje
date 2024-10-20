# Reservacion de viaje

This is a simple Kotlin app for travel reservation (simulation)

## Preview

### First Screen
<img src="images/img1.jpeg" width="200">

<img src="images/img2.jpeg" width="200">

### Second screen
<img src="images/img3.jpeg" width="200">

### Some validations
<img src="images/img4.png" width="300">


## Requirements 

Realizar una aplicación en Android con el IDE oficial que presente un formulario simulando la reservación de un viaje redondo en una aerolínea ficticia para 1 solo pasajero(a) con los siguientes datos a ingresar: 

* Nombre(s) 
* Apellido(s)
* Origen
* Destino
* Fecha Salida
* Fecha regreso
* Correo electrónico (validando que lo ingresado tenga forma de correo)
* Número de viajero frecuente (opcional) y de 8 dígitos

El monto del vuelo redondo se obtendrá aleatoriamente con un monto de entre $3,000.00 a $10,000.00. En caso de proporcionar un número de viajero frecuente, se aplicará un descuento del 15%

Se considera que la aerolínea tiene vuelos únicamente en los siguientes horarios:
* 7:00 a.m.
* 10:00 a.m.
* 2:00 p.m.
* 6:00 p.m.

Y los destinos son únicamente los siguientes:
* Cancún
* Ciudad de México
* Guadalajara
* Monterrey
* Los Cabos
* Tijuana

A partir de los datos ingresados, se deberá implementar un botón para "Reservar" e ir a otra pantalla ver los datos finales de la reservación, que deben incluir:
* Nombre completo del pasajero(a)
* Origen con fecha y hora del vuelo
* Asiento en vuelo de ida (asignado aleatoriamente)
* Destino con fecha y hora del vuelo
* Asiento en vuelo de regreso (asignado aleatoriamente)
* Monto final
* Si proporcionó número de cliente frecuente, monto con el descuento aplicado.

Algunos ejemplos de asientos serían los siguientes: 4C, 15A, 2B.
