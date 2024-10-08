package com.kadogiyazilim.gunouygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kadogiyazilim.gunouygulamasi.Takvim
import java.util.Calendar

class AnaMenu : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null//başlangıç değeri boş olabilir
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_menu)
        // TextView öğesini tanımlayın
        val textView = findViewById<TextView>(R.id.tarih)
// Calendar sınıfını kullanarak bugünkü tarihi alın
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // 0-11 arası olduğu için +1 eklenir
        val day = calendar.get(Calendar.DAY_OF_MONTH)

// Tarihi TextView'a yazdırın
        val todayDate = "$day/$month/$year"
        textView.text = todayDate



        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Kullanici")
        val currentUser = auth.currentUser
        val adSoyad = findViewById<TextView>(R.id.anaMenuAdSoyad)

        //realtime databasedeki idye ulaşıp altındaki childların içindeki veriyi isim soyisim yerine aktarıcaz
        val userReference = databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                adSoyad.text = snapshot.child("AdiSoyadi").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        // takvim sayfasına gidiyoruz
        val takvimButon = findViewById<ImageButton>(R.id.btnAnaMenuTakvim)
        takvimButon.setOnClickListener {
            val intent = Intent(applicationContext,Takvim::class.java)
            startActivity(intent)
        }
        val gunlukButon = findViewById<ImageButton>(R.id.btnAnaMenuGunlugum)
        gunlukButon.setOnClickListener {
            val intent = Intent(applicationContext,Gunlugum::class.java)
            startActivity(intent)
        }
        val gorevButon = findViewById<ImageButton>(R.id.btnAnaMenuGorevListesi)
        gorevButon.setOnClickListener {
            val intent = Intent(applicationContext,GorevListesi::class.java)
            startActivity(intent)
        }
        val pomodoroButon = findViewById<ImageButton>(R.id.btnAnaMenuPomodoro)
        pomodoroButon.setOnClickListener {
            val intent = Intent(applicationContext,Pomodoro::class.java)
            startActivity(intent)
        }
        val kilitButon = findViewById<ImageButton>(R.id.btnAnaMenuGorevAgaci)
        kilitButon.setOnClickListener{
            val intent = Intent(applicationContext,KilitSayfasi::class.java)
            startActivity(intent)
        }
        /*hesabımı sil
        * currentUser?.delete()?.addOnCompleteListener{
        *       if(it.isSuccseful){
        *           "Silindi Mesajı"
        *             auth.singOut()
        * v                 ar currentUserDb = databaseReference?.child(currentUser.uid)
                            currentUserDb?.removeValue()
        *              "giriş sayfasına gönder"}
        *           }
        *
        *
        *
        *
        *
        * */


        // Çıkış yap butonu
        // cikisyapbutonu.setOnClickListener {
        //auth.signOut()
        // startActivity(Intent(this@anaMenu,GirisActivity::class.java))
        //finish()
        // }



    }
}