package com.example.groupcal.ui.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.groupcal.databinding.FragmentCalendarBinding

/**
 * A simple [Fragment] subclass.
 *
 * @property binding The binding linked with the fragment, initialised in [onCreateView]
 */
class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    /**
     * Inflate view with data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        return binding.root
    }
}
