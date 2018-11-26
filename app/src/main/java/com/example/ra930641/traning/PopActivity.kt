package com.example.ra930641.traning

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import com.example.ra930641.traning.objects.Exercise

import kotlinx.android.synthetic.main.activity_pop.*

class PopActivity() : Activity()  {

    val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val height = dm.heightPixels

        window.setLayout((width * 0.8).toInt(), (height * 0.45).toInt())

        val params = window.attributes
        params.gravity = Gravity.CENTER
        params.x = 0
        params.y = -150

        val type = intent.getStringExtra("type")

        window.attributes = params

        saveButton.setOnClickListener {
            val db = DatabaseHandler(applicationContext)
            val name = nameTextInput2.text.toString()
            val desc = descTextInput2.text.toString()
            val exercise = Exercise(type,name,desc, "0x0")
            db.insertExercise(exercise)
            finish()
        }
    }
}
