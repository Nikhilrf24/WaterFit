package com.example.waterfit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.water_activity)

        findViewById<Button>(R.id.record2).setOnClickListener {
            val hours = findViewById<EditText>(R.id.water_intake)
            val date = findViewById<EditText>(R.id.water_date)

            val waterItem = WaterItem(hours.text.toString(), date.text.toString())

            hours.setText("")
            date.setText("")

            lifecycleScope.launch(Dispatchers.IO) {
                (application as WaterfitApplication).db.waterDao().insert(
                    WaterEntity(
                        litres = waterItem.litres,
                        date = waterItem.date,
                    )
                )
            }

            finish()
        }
    }
}