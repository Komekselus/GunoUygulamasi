package com.kadogiyazilim.gunouygulamasi

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class GorevListesiYeniGorevEkle : AppCompatActivity() {
    val calendar = java.util.Calendar.getInstance()
    val suankiKullaniciID = FirebaseAuth.getInstance().currentUser?.uid
    val databaseReference = FirebaseDatabase.getInstance().reference//database referansımız
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gorev_listesi_yeni_gorev_ekle)

        val tarihButonu= findViewById<Button>(R.id.tarihSecButonu)
        val cikisButonu = findViewById<Button>(R.id.btnYeniGorevEkleKapat)
        val kaydetButonu = findViewById<Button>(R.id.yeniGorevKaydet)
        val baslik = findViewById<TextView>(R.id.gorevEkleBaslik)
        val aciklama = findViewById<TextView>(R.id.gorevEkleAciklama)
        val calendar = Calendar.getInstance()
        var veritabaniYil = calendar.get(Calendar.YEAR)
        var veritabaniAy = calendar.get(Calendar.MONTH) + 1
        var veritabaniGun = calendar.get(Calendar.DAY_OF_MONTH)
        tarihButonu.text = "$veritabaniGun/${veritabaniAy}/$veritabaniYil"


        val minTarih = Calendar.getInstance()
        minTarih.set(2023, 0, 1)
        tarihButonu.setOnClickListener {
            // DatePickerDialog'u oluşturun
            val datePickerDialog = DatePickerDialog(this, { _, yil, ay, gun ->
                tarihButonu.text = "$gun/${ay+1}/$yil"
                veritabaniYil = yil
                veritabaniAy = ay + 1
                veritabaniGun = gun
            }, veritabaniYil, veritabaniAy, veritabaniGun)
            datePickerDialog.datePicker.minDate = minTarih.timeInMillis
            // DatePickerDialog'u açın
            datePickerDialog.show()
        }

        cikisButonu.setOnClickListener {
            val intent = Intent(applicationContext,GorevListesi::class.java)
            startActivity(intent)
        }
        kaydetButonu.setOnClickListener {
            if(baslik.text != null){
                if(aciklama.text != null) {
                    databaseReference.child("Kullanici").child(suankiKullaniciID.toString()).child("Gorevler").child(veritabaniGun.toString()+veritabaniAy.toString()+veritabaniYil.toString()).child(baslik.text.toString().trim()).setValue(aciklama.text.toString())
                    Toast.makeText(this@GorevListesiYeniGorevEkle,"Kaydetme Başarılı", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                else {
                    Toast.makeText(this@GorevListesiYeniGorevEkle,"Açıklama Kısmı Boş Geçilemez", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }
            else {
                Toast.makeText(this@GorevListesiYeniGorevEkle,"Başlık Kısmı Boş Geçilemez", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

        }

    }
}