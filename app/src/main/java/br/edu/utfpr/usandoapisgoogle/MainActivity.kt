package br.edu.utfpr.usandoapisgoogle

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var tvLatitude : TextView
    private lateinit var tvLongitude : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvLatitude = findViewById( R.id.tvLatitude )
        tvLongitude = findViewById( R.id.tvLongitude )
    }

    fun btVerEnderecoOnClick(view: View) {
        Thread ( Runnable {
            val endereco = "https://maps.googleapis.com/maps/api/geocode/xml?latlng=${tvLatitude.text},${tvLongitude.text}&key=AIzaSyDsy454kAkXofX828BEMieAQ7EbtpjohZY"
            val url = URL(endereco)
            var con = url.openConnection()

            val inputStreamReader = con.getInputStream()

            val entrada = BufferedReader( InputStreamReader( inputStreamReader ) )

            val msgSaida = StringBuilder()

            var linha = entrada.readLine()

            while( linha != null ) {
                msgSaida.append( linha )
                linha = entrada.readLine()
            }

            val formattedAddress = msgSaida.substring(
                msgSaida.indexOf( "<formatted_address>") + 19,
                msgSaida.indexOf( "</formatted_address>" )
            )

            runOnUiThread {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Endereço: ")
                dialog.setMessage(formattedAddress)
                dialog.setCancelable(false)
                dialog.setNeutralButton("OK", null)
                dialog.show()
            }




        } ).start()


    }
    fun btVerMapaOnClick(view: View) {
        Thread {
            val endereco = "https://maps.googleapis.com/maps/api/staticmap?center=Pato+Branco&zoom=11&size=400x400&key=AIzaSyDsy454kAkXofX828BEMieAQ7EbtpjohZY"

            val url = URL( endereco )
            val urlConnection = url.openConnection()
            val inputStream = urlConnection.getInputStream()

            val imagem = BitmapFactory.decodeStream( inputStream )

            runOnUiThread {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Mapa: ")

                val imageView = ImageView( this )
                imageView.setImageBitmap( imagem )
                dialog.setView( imageView)

                dialog.setCancelable(false)
                dialog.setNeutralButton("OK", null)
                dialog.show()
            }

        }.start()
    }
}