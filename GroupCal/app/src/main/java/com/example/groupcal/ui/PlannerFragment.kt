package com.example.groupcal.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewDisplayable

import com.example.groupcal.R
import com.example.groupcal.models.Event
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PlannerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlannerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val weekView: WeekView<Event> by lazy {
        requireActivity().findViewById<WeekView<Event>>(R.id.weekView)
    }

    private val card: CardView by lazy {
        requireActivity().findViewById<CardView>(R.id.card)
    }

    private val textview: TextView by lazy {
        requireActivity().findViewById<TextView>(R.id.textView3)
    }

    private val textviewMonth: TextView by lazy {
        requireActivity().findViewById<TextView>(R.id.textView4)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planner, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val activities: List<WeekViewDisplayable<Event>> = loadEvents()
        weekView.submit(activities)
        Log.d("test", activities.toString())
        Log.d("test", "test")

        weekView.setOnRangeChangeListener { firstVisibleDate, lastVisibleDate ->
            run {
                textview.setText(
                    firstVisibleDate.time.date.toString()
                )
                var fmt = Formatter()
                val cal = Calendar.getInstance().apply {
                    set(Calendar.MONTH, firstVisibleDate.time.month)
                }
                fmt = Formatter()
                fmt.format("%tB", cal)
                textviewMonth.setText(fmt.toString())

            }
        }


        weekView.minDate = getStartDate()
        weekView.maxDate = getEndDate()
    }

    private fun getStartDate(): Calendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, getActualMaximum(Calendar.MONTH) - 1)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
    }

    private fun getEndDate(): Calendar = Calendar.getInstance().apply {
        val daysInMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
        set(Calendar.MONTH, getActualMaximum(Calendar.MONTH) + 1)
        set(Calendar.DAY_OF_MONTH, daysInMonth)
        set(Calendar.HOUR_OF_DAY, 23)

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlannerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlannerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun loadEvents(

    ): List<WeekViewDisplayable<Event>> {
        val year = Calendar.getInstance().get(Calendar.YEAR);
        val month = Calendar.getInstance().get(Calendar.MONTH);

        val idOffset = year + 10L * month
        val events = mutableListOf<WeekViewDisplayable<Event>>()

        events += newEvent(
            id = idOffset + 1,
            year = year,
            month = month,
            dayOfMonth = 28,
            hour = 1,
            minute = 0,
            duration = 90,
            color = Color.parseColor("#808000")
        ).toWeekViewEvent()
        return events
    }

    private fun newEvent(
        id: Long,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hour: Int,
        minute: Int,
        duration: Int,
        color: Int,
        isAllDay: Boolean = false,
        isCanceled: Boolean = false
    ): Event {
        val startTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endTime = startTime.clone() as Calendar
        endTime.add(Calendar.MINUTE, duration)

        val title = buildEventTitle(startTime)
        return Event(id, title, startTime, endTime, "Location $id", color, isAllDay, isCanceled)
    }

    private fun buildEventTitle(time: Calendar): String {
        val sdf = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)
        val formattedDate = sdf.format(time.time)
        val hour = time.get(Calendar.HOUR_OF_DAY)
        val minute = time.get(Calendar.MINUTE)
        return String.format("🦄 Event of %02d:%02d %s", hour, minute, formattedDate)
    }
}
