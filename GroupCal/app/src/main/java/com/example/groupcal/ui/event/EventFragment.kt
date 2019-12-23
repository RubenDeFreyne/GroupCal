package com.example.groupcal.ui.event

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.groupcal.R
import com.example.groupcal.ui.event.EventFragmentArgs
import com.example.groupcal.viewmodels.EventViewModel
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {

    private lateinit var titleText: TextView
    private lateinit var dateText: TextView
    private lateinit var timeText: TextView
    private lateinit var dateTextDetail: TextView
    private lateinit var locationText: TextView


    private var listener: OnFragmentInteractionListener? = null

    private val viewModel by viewModel<EventViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args =
            EventFragmentArgs.fromBundle(arguments)
        viewModel.getEvent(args.id)


        titleText = view.findViewById(R.id.titleText)
        dateText = view.findViewById(R.id.datePreviewText)
        timeText = view.findViewById(R.id.timePreviewText)
        dateTextDetail = view.findViewById(R.id.dateTextView)
        locationText = view.findViewById(R.id.locationTextView)

        titleText.setText(viewModel.title)
        dateText.setText(viewModel.date)
        timeText.setText(viewModel.time)
        dateTextDetail.setText(viewModel.dateDetail)
        locationText.setText(viewModel.location)

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
