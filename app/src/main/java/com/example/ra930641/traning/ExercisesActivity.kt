package com.example.ra930641.traning


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.ra930641.traning.objects.Exercise
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExercisesActivity() : AppCompatActivity(){


    var exercises : MutableList<Exercise> = ArrayList()
    var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        type = intent.getStringExtra(CutsomViewHolder.WORKOUTGROUP_TITLE)
        supportActionBar?.title = type
        exercises = loadCurrentMuscleGroupd()

        recycler_exercise.layoutManager = LinearLayoutManager(this)
        recycler_exercise.adapter = ExerciseAdapter(exercises, type)

        addButton.setOnClickListener{
            val intent = Intent(applicationContext, PopActivity()::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
        }


    }



    override fun onResume() {
        super.onResume()
        exercises = loadCurrentMuscleGroupd()

        recycler_exercise.layoutManager = LinearLayoutManager(this)
        recycler_exercise.adapter = ExerciseAdapter(exercises, type)
    }
    fun loadCurrentMuscleGroupd(): MutableList<Exercise>{
        val db = DatabaseHandler(applicationContext)
        val list = db.readExercises()
        val newList : MutableList<Exercise> = ArrayList()
        for (item in list){
            if(item.type == type){
                newList.add(item)
            }
        }
        return newList
    }






    class ExerciseAdapter(exercises: MutableList<Exercise>, type: String) : RecyclerView.Adapter<CustomWorkoutViewHolder>(){

        val exercises = exercises
        val type = type

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomWorkoutViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.item_exercise, parent, false)
            return CustomWorkoutViewHolder(cellForRow)
        }

        override fun getItemCount(): Int {

           return exercises.size
        }

        override fun onBindViewHolder(holder: CustomWorkoutViewHolder, p1: Int) {

                holder.view.exerciseTextView.text = exercises[p1].name
                holder.view.descriptionTextView.text = exercises[p1].description
                holder.view.setTextInput.setText(exercises[p1].set)


        }
    }

    class CustomWorkoutViewHolder(val view: View): RecyclerView.ViewHolder(view){

        init {
            view.saveSetButton.setOnClickListener{
                val db = DatabaseHandler(view.context)
                db.updateExercise(view.exerciseTextView.text.toString(), view.setTextInput.text.toString())

            }
        }


    }


}