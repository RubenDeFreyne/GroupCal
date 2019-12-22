package com.example.groupcal.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.groupcal.databinding.FragmentGroupListCardBinding
import com.example.groupcal.models.Group

class GroupHolder private constructor (val binding: FragmentGroupListCardBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    fun update(group: Group) {
        binding.groupNameText.text = group.name
        binding.groupMembersText.text = "Members: " + group.members.size.toString()
        if (!(group.color?.isEmpty())!!) {
            binding.groupColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(group.color))
        }
    }

    fun bind(clickListener: GroupListener, item: Group) {
        binding.group = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }


    companion object {
        fun from(parent: ViewGroup): GroupHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = FragmentGroupListCardBinding.inflate(inflater, parent, false)
            return GroupHolder(binding)
        }
    }

}