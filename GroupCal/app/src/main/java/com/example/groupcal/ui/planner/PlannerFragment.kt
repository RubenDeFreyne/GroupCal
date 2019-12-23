package com.example.groupcal.ui.planner

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewDisplayable

import com.example.groupcal.R
import com.example.groupcal.models.Event
import com.example.groupcal.ui.planner.PlannerFragmentArgs
import com.example.groupcal.ui.planner.PlannerFragmentDirections
import com.example.groupcal.viewmodels.CalendarViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PlannerFragment : Fragment() {


    private val viewModel by viewModel<CalendarViewModel>()

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
super.onViewCreated(view, savedInstanceState)
        val args =
            PlannerFragmentArgs.fromBundle(arguments)
        viewModel.groupId = args.groupId

        weekView = view.findViewById<WeekView<Event>>(R.id.weekView)
        monthText = view.findViewById<TextView>(R.id.textView4)
        dayText = view.findViewById<TextView>(R.id.textView3)
        dayButton = view.findViewById<Button>(R.id.dayButton)
        weekButton = view.findViewById<Button>(R.id.weekButton)

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

        val events: MutableLiveData<List<WeekViewDisplayable<Event>>> = loadEvents()

        if(events.value!!.size != 0)
            events.observe(this, androidx.lifecycle.Observer { a -> weekView.submit(a) })

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

        weekView.setOnEmptyViewLongClickListener { time ->  view!!.findNavController().navigate(
            PlannerFragmentDirections.ActionPlannerFragmentToAddEventFragment(
                time.time.toString(),
                viewModel.groupId
            )
        )}

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


        weekView.setOnEventClickListener { data, rect ->  this.findNavController().navigate(
            PlannerFragmentDirections.actionPlannerFragmentToEventFragment(
                data.id
            )
        )}
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlannerFragment()
    }
}
