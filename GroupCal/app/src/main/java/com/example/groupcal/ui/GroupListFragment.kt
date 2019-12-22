package com.example.groupcal.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

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


        binding.groupRecycler.let {
            adapter = GroupListAdapter(GroupListener { view!!.findNavController().navigate(GroupListFragmentDirections.ActionGroupListFragmentToPlannerFragment())
            })
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
