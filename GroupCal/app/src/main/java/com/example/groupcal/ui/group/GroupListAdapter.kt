package com.example.groupcal.ui.group

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.groupcal.models.Group

class GroupListAdapter(val clickListener: GroupListener) : ListAdapter<Group, GroupHolder>(
    GroupDiffCallback()
){


    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.update(getItem(position))
        holder.bind(clickListener,getItem(position)!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        return GroupHolder.from(parent)
    }
}

class GroupDiffCallback : DiffUtil.ItemCallback<Group>() {
    override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem.backendId == newItem.backendId
    }

    override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem == newItem
    }
}

class GroupListener(val clickListener: (groupId: String) -> Unit) {
    fun onClick(group: Group) = clickListener(group.backendId)
}

