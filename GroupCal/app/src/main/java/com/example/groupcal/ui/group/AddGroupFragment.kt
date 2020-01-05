package com.example.groupcal.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.groupcal.databinding.FragmentAddGroupBinding
import com.example.groupcal.viewmodels.AddGroupViewModel
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 * @property viewModel The view model corresponding to the fragment, initialised in [onViewCreated]
 * @property binding The binding linked with the fragment, initialised in [onCreateView]
 */
class AddGroupFragment : Fragment() {

    private val viewModel by viewModel<AddGroupViewModel>()
    private lateinit var binding: FragmentAddGroupBinding

    /**
     * Inflate view with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddGroupBinding.inflate(inflater)
        return binding.root
    }

    /**
     * Instantiate the properties
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set start color
        val cp = ColorPicker(this.activity, 255, 255, 255)

        // Set click listener for ColorTextView
        binding.groupColorTextView.setOnClickListener {
            cp.show()
            cp.enableAutoClose()
        }

        // Set listener for ColorPicker
        cp.setCallback { color -> run {
            binding.groupColorTextView.setBackgroundColor(color)
            binding.groupColorTextView.setText(color.toString())
            binding.groupColorTextView.setTextColor(color)
        } }

        // Set click listener for SaveButton
        binding.saveGroupButton.setOnClickListener {
            viewModel.name = binding.groupNameTextView.text.toString()
            viewModel.color = binding.groupColorTextView.text.toString()

            // Show toast depending on fields
            var result = viewModel.addGroup()
            if (result) {
                Toast.makeText(context, "Added new Group", Toast.LENGTH_LONG).show()
                view!!.findNavController().navigate(
                    AddGroupFragmentDirections.ActionAddGroupFragmentToGroupListFragment()
                )
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }
        }

        // Cancel
        binding.cancelGroupButton.setOnClickListener {
            view!!.findNavController().navigateUp()
        }
    }
}
