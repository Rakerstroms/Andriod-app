package com.example.ra930641.traning

import android.content.Intent
import android.graphics.Rect
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ra930641.traning.objects.Group
import kotlinx.android.synthetic.main.item_musclegroup.view.*

class WorkourGroupAdapter: RecyclerView.Adapter<CutsomViewHolder>() {

    private val titles = listOf("Biceps", "Triceps", "Chest", "Back", "Legs", "Others")
    private val images = arrayOf(R.drawable.biceps, R.drawable.triceps, R.drawable.chest, R.drawable.back, R.drawable.legs, R.drawable.stomack)


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CutsomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_musclegroup, parent, false)
        return CutsomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CutsomViewHolder, p1: Int) {
        val title = titles[p1]
        holder.view.imageTextView.text = title
        holder.view.imageView.setImageResource(images[p1])

    }

    override fun getItemCount(): Int {
        return titles.size
    }


}

class CutsomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    companion object {
        val WORKOUTGROUP_TITLE = "TITLE"
    }

    init {
        view.setOnClickListener{
            val type = view.imageTextView.text.toString()
            val intent = Intent(view.context, ExercisesActivity()::class.java)
            intent.putExtra(WORKOUTGROUP_TITLE, type)
            view.context.startActivity(intent)
        }
    }
}

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left =  spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}



