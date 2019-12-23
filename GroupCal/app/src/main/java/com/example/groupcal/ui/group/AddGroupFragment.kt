package com.example.groupcal.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.example.groupcal.R
import com.example.groupcal.databinding.FragmentAddGroupBinding
import com.example.groupcal.databinding.FragmentGroupListBinding
import com.example.groupcal.viewmodels.AddGroupViewModel
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



class AddGroupFragment : Fragment() {

    private val viewModel by viewModel<AddGroupViewModel>()
    private lateinit var binding: FragmentAddGroupBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddGroupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //COLOR PICKER

        val colorTextView = binding.groupColorTextView
        val cp = ColorPicker(this.activity, 255, 255, 255)
        colorTextView.setOnClickListener{
            cp.show()
            cp.enableAutoClose()
        }
        cp.setCallback { color -> run {
            colorTextView.setBackgroundColor(color)
            colorTextView.setText("Picked color")
            colorTextView.setTextColor(color)
        }}


        //SAVE BUTTON

        binding.saveGroupButton.setOnClickListener{
            viewModel.name = binding.groupNameTextView.text.toString()
            viewModel.color = binding.groupColorTextView.text.toString()
            var result = viewModel.addGroup()

            if(result){
                Toast.makeText(context, "Added new Group}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }
        }


    }





}
