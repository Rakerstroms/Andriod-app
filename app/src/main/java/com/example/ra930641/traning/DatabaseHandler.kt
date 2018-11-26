package com.example.ra930641.traning

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.ra930641.traning.objects.Exercise
import com.example.ra930641.traning.objects.Group

val DATABASE_NAME = "WorkoutDB"
val TABLE_NAME = "MuscleGroups"
val TABLE_BICEPS = "BicepsTable"

val COL_GROUP = "groups"
val COL_STATUS = "status"
val COL_TYPE = "type"
val COL_NAME = "name"
val COL_DESC = "description"
val COL_SET = "sets"
val COL_IMAGE = "exerciseimage"

val array: Array<Group> = arrayOf(Group("Biceps", 0), Group("Triceps", 0), Group("Chest", 0),Group("Back", 0),Group("Legs", 0),Group("Others", 0))

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){

    val context = context
    override fun onCreate(db: SQLiteDatabase?) {

        val createGroupTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_GROUP + " VARCHAR(256)," + COL_STATUS + " INTERGER)"
        val createBicepsTable = "CREATE TABLE " + TABLE_BICEPS + " (" +
                COL_TYPE + " VARCHAR(256)," + COL_NAME + " VARCHAR(256)," + COL_DESC + " VARCHAR(256)," + COL_SET + " VARCHAR(256)," + COL_IMAGE + " BLOB)"

        db?.execSQL(createGroupTable)
        db?.execSQL(createBicepsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun insertExercise(exercise: Exercise){
        val db = this.writableDatabase

        val cv = ContentValues()

        cv.put(COL_TYPE, exercise.type)
        cv.put(COL_NAME, exercise.name)
        cv.put(COL_DESC, exercise.description)
        cv.put(COL_SET, exercise.set)


        var result = db.insert(TABLE_BICEPS, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readExercises() : MutableList<Exercise>{

        val list : MutableList<Exercise> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_BICEPS"

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                var exercise = Exercise()
                exercise.type = result.getString(result.getColumnIndex(COL_TYPE))
                exercise.name = result.getString(result.getColumnIndex(COL_NAME))
                exercise.description = result.getString(result.getColumnIndex(COL_DESC))
                exercise.set = result.getString(result.getColumnIndex(COL_SET))
                list.add(exercise)
            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }

    fun readGroups() : MutableList<Group> {

        val list : MutableList<Group> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                var day = Group()
                day.group = result.getString(result.getColumnIndex(COL_GROUP))
                day.status = result.getString(result.getColumnIndex(COL_STATUS)).toInt()
                list.add(day)
            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }

    fun updateExercise(exerciseName: String, newSet: String){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_BICEPS"

        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_SET, newSet)
                db.update(TABLE_BICEPS, cv, COL_NAME + "=?", arrayOf(exerciseName))
            }while (result.moveToNext())
        }
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        result.close()
        db.close()
    }


    fun updateGroup(name: String, status: Int){

        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                var cv = ContentValues()
                cv.put(COL_STATUS, status)
                db.update(TABLE_NAME, cv, COL_GROUP + "=?", arrayOf(name))


            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }


    fun insertWorkOutGroupChecks(){
        val db = this.writableDatabase
        var fail = false

        for (group in array) {
            val cv = ContentValues()

            cv.put(COL_GROUP, group.group)
            cv.put(COL_STATUS, group.status)

            var result = db.insert(TABLE_NAME, null, cv)
            if (result == -1.toLong())
                fail = true
            else {
            }
        }
        if(fail){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }
}