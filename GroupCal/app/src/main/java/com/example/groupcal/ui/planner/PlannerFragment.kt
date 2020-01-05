package com.example.groupcal.ui.planner

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.alamkanak.weekview.WeekView
import com.example.groupcal.R
import com.example.groupcal.models.Event
import com.example.groupcal.viewmodels.CalendarViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.example.groupcal.databinding.FragmentPlannerBinding

/**
 * A simple [Fragment] subclass.
 *
 * @property viewModel The view model corresponding to the fragment, initialised in [onViewCreated]
 */
class PlannerFragment : Fragment() {

    private val viewModel by viewModel<CalendarViewModel>()
    private lateinit var binding: FragmentPlannerBinding
    private lateinit var weekView: WeekView<Event>
    private lateinit var dayText: TextView
    private lateinit var dayButton: Button
    private lateinit var monthText: TextView
    private lateinit var threeDayButton: Button
    private lateinit var weekButton: Button

    /**
     * Inflate view with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlannerBinding.inflate(inflater)
        return binding.root
    }

    /**
     * Instantiate the properties
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args =
            PlannerFragmentArgs.fromBundle(arguments)
        viewModel.groupId = args.groupId
        viewModel.fetchEvents(args.groupId)
        viewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let {
                weekView.submit(it)
            }
        })
        weekView = view.findViewById<WeekView<Event>>(R.id.weekView)
        monthText = view.findViewById<TextView>(R.id.monthText)
        dayText = view.findViewById<TextView>(R.id.dayText)
        dayButton = view.findViewById<Button>(R.id.dayButton)
        weekButton = view.findViewById<Button>(R.id.weekButton)
        threeDayButton = view.findViewById<Button>(R.id.threeDayButton)

        weekView.minDate = viewModel.startDate
        weekView.maxDate = viewModel.endDate
        weekView.goToCurrentTime()
    }

    override fun onStart() {
        super.onStart()

        // Set onRangeChangeListener for swiping
        weekView.setOnRangeChangeListener { firstVisibleDate, lastVisibleDate ->
            run {
                viewModel.currentlyViewing = firstVisibleDate


                if (firstVisibleDate == lastVisibleDate) {
                    dayText.setText(
                        firstVisibleDate.time.date.toString()
                    )
                } else {
                    dayText.setText(
                        "" + firstVisibleDate.time.date.toString() + " - " + lastVisibleDate.time.date.toString()
                    )
                }
                monthText.setText(viewModel.getMonthText().capitalize())
            }
        }

        // Set onEmptyViewLongClickListener for adding new events
        weekView.setOnEmptyViewLongClickListener { time -> view!!.findNavController().navigate(
            PlannerFragmentDirections.ActionPlannerFragmentToAddEventFragment(
                time.time.toString(),
                viewModel.groupId
            )
        ) }

        // Set onClickListener for changing to dayview
        dayButton.setOnClickListener(View.OnClickListener {
            weekView.numberOfVisibleDays = 1
            weekView.headerRowTextSize = 0
            weekView.headerRowPadding = 0
            weekView.headerRowBottomLineWidth= 0
            weekView.isShowHeaderRowBottomLine= false
            dayButton.setTextColor(getResources().getColor(R.color.colorPrimary))
            weekButton.setTextColor(Color.parseColor("#33000000"))
            threeDayButton.setTextColor(Color.parseColor("#33000000"))
            weekView.goToDate(viewModel.currentlyViewing)
        })

        // Set onClickListener for changing to threedayview
        threeDayButton.setOnClickListener(View.OnClickListener {
            weekView.numberOfVisibleDays = 3
            weekView.headerRowTextSize = 20
            weekView.headerRowPadding = 20
            weekView.isShowHeaderRowBottomLine = true
            threeDayButton.setTextColor(getResources().getColor(R.color.colorPrimary))
            dayButton.setTextColor(Color.parseColor("#33000000"))
            weekButton.setTextColor(Color.parseColor("#33000000"))
            weekView.goToDate(viewModel.currentlyViewing)
        })

        // Set onClickListener for changing to weekview
        weekButton.setOnClickListener(View.OnClickListener {
            weekView.numberOfVisibleDays = 7
            weekView.headerRowTextSize = 20
            weekView.headerRowPadding = 20
            weekView.isShowHeaderRowBottomLine = true
            weekButton.setTextColor(getResources().getColor(R.color.colorPrimary))
            dayButton.setTextColor(Color.parseColor("#33000000"))
            threeDayButton.setTextColor(Color.parseColor("#33000000"))
            weekView.goToDate(viewModel.currentlyViewing)
        })

        // Set onEventClickListener for getting event details
        weekView.setOnEventClickListener { data, rect -> this.findNavController().navigate(
            PlannerFragmentDirections.actionPlannerFragmentToEventFragment(
                data.databaseId
            )
        ) }
    }
}
