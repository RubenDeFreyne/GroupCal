package com.example.groupcal.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.groupcal.databinding.FragmentEventBinding
import com.example.groupcal.viewmodels.EventViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 * @property viewModel The view model corresponding to the fragment, initialised in [onViewCreated]
 * @property binding The binding linked with the fragment, initialised in [onCreateView]
 */
class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding
    private val viewModel by viewModel<EventViewModel>()

    /**
     * Inflate view with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater)
        return binding.root
    }

    /**
     * Instantiate the properties
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get navigation arguments
        val args = EventFragmentArgs.fromBundle(arguments)
        viewModel.getEvent(args.id)

        binding.titleText.setText(viewModel.title)
        binding.datePreviewText.setText(viewModel.date)
        binding.timePreviewText.setText(viewModel.time)
        binding.dateTextView.setText(viewModel.dateDetail)
        binding.locationTextView.setText(viewModel.location)
    }
}
