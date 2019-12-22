package com.example.groupcal.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.groupcal.R
import com.example.groupcal.databinding.FragmentGroupListBinding


class GroupListFragment : Fragment() {

    private lateinit var adapter: GroupListAdapter
    private lateinit var binding: FragmentGroupListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.groupRecycler.let {
            adapter = GroupListAdapter()
            it.adapter = adapter
        }

    }




}
