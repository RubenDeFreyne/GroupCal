package com.example.groupcal.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.groupcal.databinding.FragmentGroupListBinding
import com.example.groupcal.viewmodels.GroupViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 * @property viewModel The view model corresponding to the fragment, initialised in [onViewCreated]
 * @property binding The binding linked with the fragment, initialised in [onCreateView]
 * @property adapter The adapter for the RecyclerView, initialised in [onViewCreated]
 */
class GroupListFragment : Fragment() {

    private lateinit var adapter: GroupListAdapter
    private lateinit var binding: FragmentGroupListBinding
    private val viewModel by viewModel<GroupViewModel>()

    /**
     * Inflate view with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupListBinding.inflate(inflater)
        return binding.root
    }

    /**
     * Instantiate the properties
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Create adapter for the RecyclerView
        binding.groupRecycler.let {
            adapter =
                GroupListAdapter(GroupListener { groupId ->
                    Toast.makeText(context, groupId, Toast.LENGTH_LONG).show()
                    view!!.findNavController().navigate(
                        GroupListFragmentDirections.ActionGroupListFragmentToPlannerFragment(
                            groupId
                        )
                    )
                })
            it.adapter = adapter
        }

        //Observe groups
        viewModel.groups.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        //Set clickListener for addGroupButton
        binding.addGroupButton.setOnClickListener {
            view!!.findNavController().navigate(
                GroupListFragmentDirections.ActionGroupListFragmentToAddGroupFragment()
            )
        }
    }
}
