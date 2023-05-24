package com.kadogiyazilim.gunouygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast

class KilitEkraniKilit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kilit_ekrani_kilit)



        val countdownTimer = object : CountDownTimer(60000 , 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Her saniye geri sayımı güncelliyoruz
                val message = "Uygulama kilitli: ${millisUntilFinished / 1000} saniye"
                Toast.makeText(this@KilitEkraniKilit, message, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
                // Geri sayım tamamlandığında, ana ekrana geri dönüyoruz
                val intent = Intent(this@KilitEkraniKilit, KilitSayfasi::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
        countdownTimer.start()


    }
    override fun onBackPressed() {
        // Geri dönüşü engelliyoruz
    }
}