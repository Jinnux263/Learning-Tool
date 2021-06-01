package com.example.learningtool

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learningtool.databinding.FragmentSetClockBinding
import java.util.*


class SetClockFragment : Fragment() {

    var minute : Int = 30
    var hour : Int = 0
    lateinit var binding : FragmentSetClockBinding
    //lateinit var DataBase : DataBaseHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSetClockBinding.inflate(layoutInflater)

        /**
         * DataBase = DataBaseHandler(context)
        val list = DataBase.readData()
        hour = list[0].time?.toInt() ?: 0
        binding.clockDisplay.text = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)
         */
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setButton.setOnClickListener { view ->

            val bundle = Bundle()

            bundle.putInt(KEY_HOUR, hour)
            bundle.putInt(KEY_MINUTE, minute)

            val fragment = RunCountDownTimer()
            fragment.arguments = bundle
            val fm = getFragmentManager()

            val transaction = fm!!.beginTransaction()
            transaction.replace(R.id.placeholder, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.clockDisplay.setOnClickListener {
            popTimePicker()
        }
    }

    override fun onStop() {
        super.onStop()
        hour = 0
        minute = 30
    }

    fun popTimePicker() {
        val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourPicker, minutePicker ->
            hour = hourPicker
            minute = minutePicker
            binding.clockDisplay.text = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)

        }

        val style = R.style.timePickerStyle
        val timePickerDialog = TimePickerDialog(requireContext(), style, onTimeSetListener,  hour, minute, true)
        timePickerDialog.setTitle("Choose time")
        timePickerDialog.show()

    }

}