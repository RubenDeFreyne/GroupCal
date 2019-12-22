package com.example.groupcal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groupcal.R
import com.example.groupcal.models.Group
import com.example.groupcal.ui.GroupHolder

class GroupListAdapter : RecyclerView.Adapter<GroupHolder>(){

   var items: MutableList<Group> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.update(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val cardItem = LayoutInflater.from(parent.context).inflate(R.layout.fragment_group_list_card, parent, false)
        return GroupHolder(cardItem)
    }

    fun setGroups(items: MutableList<Group>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun deleteGroup(position: Int) {
        this.items.removeAt(position)
        notifyDataSetChanged()
    }

    fun getItemOnPosition(position: Int): Group {
        return this.items[position]
    }

}

