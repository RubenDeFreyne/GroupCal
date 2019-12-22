package com.example.groupcal.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.groupcal.R
import com.example.groupcal.models.Group
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GroupHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private val nameTextView: TextView = itemView.findViewById(R.id.groupNameText)
    private val membersTextView: TextView = itemView.findViewById(R.id.groupMembersText)
    private val groupColor: FloatingActionButton = itemView.findViewById(R.id.group_color)

    fun update(group: Group) {
        nameTextView.text = group.name
        membersTextView.text = "Members: " + group.members.size.toString()
        if (!(group.color?.isEmpty())!!) {
            groupColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(group.color))
        }
    }

}