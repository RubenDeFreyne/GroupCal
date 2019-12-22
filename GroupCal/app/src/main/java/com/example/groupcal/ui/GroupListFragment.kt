package com.example.groupcal.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.groupcal.R
import com.example.groupcal.databinding.FragmentGroupListBinding
import com.example.groupcal.viewmodels.GroupViewModel


class GroupListFragment : Fragment() {

    private lateinit var adapter: GroupListAdapter
    private lateinit var binding: FragmentGroupListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupListBinding.inflate(inflater)

        val groupViewModel = ViewModelProviders.of(this).get(GroupViewModel::class.java)

        groupViewModel.getGroups()
        binding.groupRecycler.let {
            adapter = GroupListAdapter()
            it.adapter = adapter
        }
        groupViewModel.groups.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }




}
