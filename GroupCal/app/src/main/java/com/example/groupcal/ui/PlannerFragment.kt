package com.example.groupcal.ui

import android.graphics.Color
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.alamkanak.weekview.OnEventClickListener
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewDisplayable

import com.example.groupcal.R
import com.example.groupcal.models.Event
import com.example.groupcal.viewmodels.CalendarViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PlannerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlannerFragment : Fragment() {


    private lateinit var viewModel: CalendarViewModel

    private lateinit var weekView: WeekView<Event>


    private lateinit var dayText: TextView

    private lateinit var dayButton: Button

    private lateinit var monthText: TextView

    private lateinit var weekButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planner, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this) .get(CalendarViewModel::class.java)

        weekView = view.findViewById<WeekView<Event>>(R.id.weekView)
        monthText = view.findViewById<TextView>(R.id.textView4)
        dayText = view.findViewById<TextView>(R.id.textView3)
        dayButton = view.findViewById<Button>(R.id.dayButton)
        weekButton = view.findViewById<Button>(R.id.weekButton)

        val events: MutableLiveData<List<WeekViewDisplayable<Event>>> = loadEvents()
        events.observe(this, androidx.lifecycle.Observer { a -> weekView.submit(a) })

        weekView.minDate = viewModel.startDate
        weekView.maxDate = viewModel.endDate
    }

    //TODO: In ViewModel?
    fun loadEvents(): MutableLiveData<List<WeekViewDisplayable<Event>>> {
        this.viewModel.fetchEvents(
            viewModel.startDate,
            viewModel.endDate
        )
        return viewModel.events
    }

    override fun onStart() {
        super.onStart()

        weekView.setOnRangeChangeListener { firstVisibleDate, lastVisibleDate ->
            run {
                viewModel.currentlyViewing = firstVisibleDate
                dayText.setText(
                    firstVisibleDate.time.date.toString()
                )
                var fmt = Formatter()
                val cal = Calendar.getInstance().apply {
                    set(Calendar.MONTH, firstVisibleDate.time.month)
                }
                fmt = Formatter()
                fmt.format("%tB", cal)
                monthText.setText(fmt.toString())
            }
        }

        dayButton.setOnClickListener(View.OnClickListener {
            weekView.numberOfVisibleDays = 1
            dayButton.setTextColor(getResources().getColor(R.color.colorPrimary))
            weekButton.setTextColor(Color.parseColor("#33000000"))
            weekView.goToDate(viewModel.currentlyViewing)
        })

        weekButton.setOnClickListener(View.OnClickListener {
            weekView.numberOfVisibleDays = 7
            weekButton.setTextColor(getResources().getColor(R.color.colorPrimary))
            dayButton.setTextColor(Color.parseColor("#33000000"))
            weekView.goToDate(viewModel.currentlyViewing)
        })


        weekView.setOnEventClickListener { data, rect ->  this.findNavController().navigate(PlannerFragmentDirections.actionPlannerFragmentToEventFragment(data.id))}




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() = PlannerFragment()
    }
}
