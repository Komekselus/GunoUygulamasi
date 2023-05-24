package com.kadogiyazilim.gunouygulamasi

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.app.admin.DeviceAdminReceiver
import android.content.Intent

class KilitSayfasi : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var secilenUygulamalar: MutableList<ApplicationInfo>
    private lateinit var kilitTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kilit_sayfasi)
        secilenUygulamalar = mutableListOf<ApplicationInfo>()
        // ListView bileşenine erişim sağlıyoruz
        listView = findViewById(R.id.kilitListe)

        // Google Play Store'dan yüklenmiş uygulamaları alıyoruz
        val pm = packageManager
        val appList = mutableListOf<ApplicationInfo>()

        for (app in pm.getInstalledApplications(PackageManager.GET_META_DATA)) {
            val installerPackageName = pm.getInstallerPackageName(app.packageName)
            if (installerPackageName == "com.android.vending") {
                appList.add(app)
            }
        }

        // Her bir uygulama öğesi için CheckBox, TextView ve ImageView oluşturuyoruz
        val adapter = object : ArrayAdapter<ApplicationInfo>(
            this,
            R.layout.activity_kilit_uygulamalari_getir,
            appList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = convertView
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                if (view == null) {
                    view =
                        inflater.inflate(R.layout.activity_kilit_uygulamalari_getir, parent, false)
                }

                val app = getItem(position)
                val appName = view?.findViewById<TextView>(R.id.app_name)
                val checkbox = view?.findViewById<CheckBox>(R.id.checkbox)
                val appIcon = view?.findViewById<ImageView>(R.id.app_icon)

                appName?.text = pm.getApplicationLabel(app!!).toString()
                checkbox?.isChecked = false
                appIcon?.setImageDrawable(pm.getApplicationIcon(app))

                checkbox?.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        secilenUygulamalar.add(app)
                    } else {
                        secilenUygulamalar.remove(app)
                    }
                }

                return view!!
            }

        }

        // ListView'e adapter'ı atıyoruz
        listView.adapter = adapter

        // Kilit butonuna tıklandığında
        val kilitButton = findViewById<Button>(R.id.kilitUygulamaKilitle)
        kilitButton.setOnClickListener {
            kilitTimer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Her saniye geri sayımı güncelliyoruz
                    val message = "Uygulama kilitleniyor: ${millisUntilFinished / 1000} saniye"
                    Toast.makeText(this@KilitSayfasi, message, Toast.LENGTH_SHORT).show()
                }

                override fun onFinish() {
                    // Geri sayım tamamlandığında, tüm seçilen uygulamaların kilidini kaldırıyoruz
                    secilenUygulamalar.forEach { appInfo ->
                        val packageName = appInfo.packageName
                        val intent = Intent(this@KilitSayfasi, KilitSayfasi::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.putExtra("packageName", packageName)
                        startActivity(intent)
                    }
                }
            }
            kilitTimer.start()
        }
    }

    // Geri butonuna basıldığında uygulamanın kapanmaması için onBackPressed metodu override ediliyor
    override fun onBackPressed() {
        // Boş bir blok bırakarak hiçbir şey yapmamasını sağlıyoruz
    }
}

