package com.ksc.movingalarm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksc.movingalarm.ui.RecordViewModel
import com.ksc.movingalarm.ui.RecordListAdapter
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity() {

    private lateinit var recordViewModel: RecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val adapter = RecordListAdapter() {
            Toast.makeText(this@ReportActivity, "$it", Toast.LENGTH_SHORT).show()
            val mapIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:${it.latitude},${it.longitude}")
            )
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this@ReportActivity)

        recordViewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        recordViewModel.allrecords.observe(this, Observer { records ->
            records?.let { adapter.setRecords(it) }
        })
    }
}