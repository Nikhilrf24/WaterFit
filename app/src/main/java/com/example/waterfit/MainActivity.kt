package com.example.waterfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var waterlog : ArrayList<WaterItem>
    lateinit var wateradapter: WaterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sleepRv = findViewById<RecyclerView>(R.id.waterdata)
        waterlog = ArrayList()
        wateradapter = WaterAdapter(waterlog, this@MainActivity)
        sleepRv.adapter = wateradapter
        lifecycleScope.launch {
            (application as WaterfitApplication).db.waterDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    WaterItem(
                        entity.litres,
                        entity.date
                    )
                }.also { mappedList ->
                    waterlog.clear()
                    waterlog.addAll(mappedList)
                    wateradapter.notifyDataSetChanged()
                }
            }
        }
        sleepRv.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            sleepRv.addItemDecoration(dividerItemDecoration)
        }

        findViewById<Button>(R.id.deleteAll).setOnClickListener {
            lifecycleScope.launch(IO) {
                (application as WaterfitApplication).db.waterDao().deleteAll()
            }
        }

        findViewById<Button>(R.id.record).setOnClickListener {
            val intent = Intent(this, WaterActivity::class.java)
            startActivity(intent)
        }
    }

    fun delete(waterItem : WaterItem) {
        lifecycleScope.launch(IO) {
            (application as WaterfitApplication).db.waterDao().delete(
                WaterEntity(
                    litres = waterItem.litres,
                    date = waterItem.date
                )
            )
        }
    }

}