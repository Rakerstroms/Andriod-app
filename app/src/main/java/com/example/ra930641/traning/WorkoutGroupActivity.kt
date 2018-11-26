package com.example.ra930641.traning


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Button
import android.widget.Toast
import com.example.ra930641.traning.objects.Group
import kotlinx.android.synthetic.main.activity_musclegroup.*

class WorkoutGroupActivity : AppCompatActivity() {

    private val checkImages = arrayOf(R.drawable.check, R.drawable.cross)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musclegroup)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = WorkourGroupAdapter()
        recyclerView_main.addItemDecoration(MarginItemDecoration(20))
        val db = DatabaseHandler(applicationContext)
        var data = db.readGroups()
        if(data.size <= 0){
            db.insertWorkOutGroupChecks()
            data = db.readGroups()
        }


        placeButtonsAndTags(data)

        loadWorkoutGroupsChecks(db)


        button_biceps.setOnClickListener{
            bicepsButtonPressed(button_biceps, "Biceps", db)

        }

        button_triceps.setOnClickListener{
           bicepsButtonPressed(button_triceps, "Triceps", db)
        }

        button_chest.setOnClickListener{
            bicepsButtonPressed(button_chest, "Chest", db)

        }

        button_back.setOnClickListener{
            bicepsButtonPressed(button_back, "Back", db)
        }

        button_legs.setOnClickListener{
            bicepsButtonPressed(button_legs, "Legs", db)

        }

        button_others.setOnClickListener{
           bicepsButtonPressed(button_others, "Others", db)
        }
    }

    fun bicepsButtonPressed(button: Button, name: String, db: DatabaseHandler){
        val buttonTag = button.getTag()

        if (buttonTag == "cross")  // here "bg" is the tag that you set previously
        {
            button.setBackgroundResource(checkImages[0])
            button.setTag("check")
            db.updateGroup(name, 1)
        }
        else
        {
            button.setBackgroundResource(checkImages[1])
            button.setTag("cross")
            db.updateGroup(name, 0)
        }
    }

    fun placeButtonsAndTags(list: MutableList<Group>) {
        val buttons = arrayOf(button_biceps,button_triceps,button_chest,button_back,button_legs,button_others)
        var i = 0
        for (item in list){
            if(item.status == 0){
                buttons[i].setBackgroundResource(checkImages[1])
                buttons[i].setTag("cross")
            }else {
                buttons[i].setBackgroundResource(checkImages[0])
                buttons[i].setTag("check")
            }
            i++
        }



    }

    fun loadWorkoutGroupsChecks(db: DatabaseHandler): MutableList<Group>{

        val data = db.readGroups()

        return data
    }




}
