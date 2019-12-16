package com.example.groupcal.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

import com.example.groupcal.R
import com.example.groupcal.viewmodels.AddEventViewModel
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddEventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEventFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: AddEventViewModel
    private lateinit var dateEditText : TextView
    private lateinit var startTimeTextView: TextView
    private lateinit var endTimeTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this) .get(AddEventViewModel::class.java)
        dateEditText = view.findViewById(R.id.dateTextView)
        startTimeTextView = view.findViewById(R.id.startTimeTextView)
        endTimeTextView = view.findViewById(R.id.endTimeTextView)

        val args = AddEventFragmentArgs.fromBundle(arguments)
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        viewModel.time = sdf.parse(args.time)
        Log.i("test", viewModel.time.toString())

        //date edit text
        dateEditText.text = SimpleDateFormat("dd.MM.yyyy").format(viewModel.time)
        var cal = Calendar.getInstance()
        cal.time = viewModel.time
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dateEditText.text = sdf.format(cal.time)
        }
        dateEditText.setOnClickListener {
            DatePickerDialog(
                this.context!!, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        //start time edit

        startTimeTextView.text = SimpleDateFormat("HH:mm").format(viewModel.time)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            val myFormat = "HH:mm" // mention the format you need
            startTimeTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        startTimeTextView.setOnClickListener {
            TimePickerDialog(
                this.context!!, timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show()
        }

        //end time edit

        endTimeTextView.text = SimpleDateFormat("HH:mm").format(viewModel.time)

        val endTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            val myFormat = "HH:mm" // mention the format you need
            endTimeTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        endTimeTextView.setOnClickListener {
            TimePickerDialog(
                this.context!!, endTimeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show()
        }


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }



}
