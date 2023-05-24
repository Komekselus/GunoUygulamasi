package com.kadogiyazilim.gunouygulamasi


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class PomodoroAyar : AppCompatActivity(), View.OnClickListener {
    private lateinit var uygulaButton: Button
    private lateinit var pomoSure: EditText
    private lateinit var kisaSure: EditText
    private lateinit var uzunSure: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_ayarlar)

        uygulaButton = findViewById(R.id.button5)
        pomoSure = findViewById(R.id.pomoSure)
        kisaSure = findViewById(R.id.kisaSure)
        uzunSure = findViewById(R.id.uzunSure)

        uygulaButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button5 -> {
                val sharedPref = getSharedPreferences("pomoSureler", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("pomoSure", pomoSure.text.toString())
                editor.putString("kisaSure", kisaSure.text.toString())
                editor.putString("uzunSure", uzunSure.text.toString())
                editor.apply()

                val intent = Intent(this, Pomodoro::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

   //region parsetime
    /*private fun parseTime(time: String?): Long {
        // Eğer time null ise, 0 döndürüldü
        if (time == null || time.isEmpty()) {
            return 0
        }
        val parts = time.split(":")
        // Listenin boyutunu kontrol etmek için
        if (parts.size != 2) { // Eğer liste iki eleman değilse
            return 0 // Sıfır döndür
        } else { // Eğer liste iki elemandan fazla veya eşit ise
            val minutes = parts[0].toLong()
            val seconds = parts[1].toLong()
            return (minutes * 60 + seconds) * 1000
        }
    }*/
    //endregion
