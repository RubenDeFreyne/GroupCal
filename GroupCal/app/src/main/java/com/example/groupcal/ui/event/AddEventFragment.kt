package com.example.groupcal.ui.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.groupcal.databinding.FragmentAddEventBinding
import com.example.groupcal.viewmodels.AddEventViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 * @property viewModel The view model corresponding to the fragment, initialised in [onViewCreated]
 * @property binding The binding linked with the fragment, initialised in [onCreateView]
 */
class AddEventFragment : Fragment() {

    private lateinit var binding: FragmentAddEventBinding
    private val viewModel by viewModel<AddEventViewModel>()

    /**
     * Inflate view with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater)
        return binding.root
    }

    /**
     * Instantiate the properties
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get navigation arguments
        val args = AddEventFragmentArgs.fromBundle(arguments)

        // Create date format for time argument
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US)

        // Set ViewModel properties from arguments
        viewModel.time = sdf.parse(args.time)
        viewModel.groupId = args.groupId

        // Set ViewModel startTime and endTime to clicked time
        viewModel.startTime = viewModel.time
        viewModel.endTime = viewModel.time

        // Set timePreview and datePreview text to clicked time
        binding.timePreviewText.setText(viewModel.getTimePreview())
        binding.datePreviewText.setText(viewModel.getDatePreview())

        // Set dateTextView to cliecked time
        binding.dateTextView.text = SimpleDateFormat("dd.MM.yyyy").format(viewModel.time)
        var cal = Calendar.getInstance()
        cal.time = viewModel.time

        // Set Listener for DatePicker
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            viewModel.time = cal.time
            binding.datePreviewText.setText(viewModel.getDatePreview())
        }

        // Set DateTextView click listener
        binding.dateTextView.setOnClickListener {
            DatePickerDialog(
                this.context!!, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Set startTime to clicked time
        binding.startTimeTextView.text = SimpleDateFormat("HH:mm").format(viewModel.time)

        // Set listener for TimePicker for startTime
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            binding.startTimeTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
            viewModel.startTime = cal.time
            binding.timePreviewText.setText(viewModel.getTimePreview())
        }

        // Set StartTimeTextView click listener
        binding.startTimeTextView.setOnClickListener {
            TimePickerDialog(
                this.context!!, timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show()
        }

        // Set endTime to clicked time
        binding.endTimeTextView.text = SimpleDateFormat("HH:mm").format(viewModel.time)

        // Set listener for TimePicker for endTime
        val endTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            binding.endTimeTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
            viewModel.endTime = cal.time
            binding.timePreviewText.setText(viewModel.getTimePreview())
        }

        // Set EndTimeTextView click listener
        binding.endTimeTextView.setOnClickListener {
            TimePickerDialog(
                this.context!!, endTimeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show()
        }

        // Set ColorPicker start color
        val cp = ColorPicker(this.activity, 255, 255, 255)

        // Set ColorTextView click listener
        binding.colorTextView.setOnClickListener {
            cp.show()
            cp.enableAutoClose()
        }

        // Set listener for ColorPicker
        cp.setCallback { color -> run {
            viewModel.color = color.toString()
            binding.colorTextView.setBackgroundColor(color)
            binding.colorTextView.setText(color.toString())
            binding.colorTextView.setTextColor(color) }
        }

        // Save the event
        binding.saveEventButton.setOnClickListener {
            viewModel.title = binding.titleEditText.text.toString()
            viewModel.location = binding.locationEditText.text.toString()
            val result = viewModel.addEvent(args.groupId)

            // Show Toast depending on fields
            if (result) {
                Toast.makeText(context, "Added new Event", Toast.LENGTH_LONG).show()
                if (viewModel.checkTime()) {
                view!!.findNavController().navigate(
                    AddEventFragmentDirections.ActionAddEventFragmentToPlannerFragment(viewModel.groupId)
                ) } else {
                    Toast.makeText(context, "End Time cannot be before Start Time", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }
        }
    }
}
