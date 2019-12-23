package com.example.groupcal.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.groupcal.R
import com.pes.androidmaterialcolorpickerdialog.ColorPicker

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



class AddGroupFragment : Fragment() {

    private lateinit var colorTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorTextView = view.findViewById(R.id.groupColorTextView)

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
    }





}
