package com.example.groupcal.ui.group

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.groupcal.databinding.FragmentGroupListCardBinding
import com.example.groupcal.models.Group

/**
 * Viewholder for [GroupListAdapter] for items in the Recyclerview
 */
class GroupHolder private constructor (val binding: FragmentGroupListCardBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    /**
     * Fill in all the textfields with data from group
     *
     * @param group The group to show the data
     */
    fun update(group: Group) {
        binding.groupNameText.text = group.name
        binding.groupMembersText.text = "Members: " + group.members.size.toString()
        if (!(group.color?.isEmpty())!!) {
            Log.i("test", group.color)
            binding.groupColor.backgroundTintList = ColorStateList.valueOf(Integer.parseInt(group.color))
        }
    }

    /**
     * Bind clickListener for the group holder
     *
     * @param clickListener The clickListener for the groupholder
     */
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