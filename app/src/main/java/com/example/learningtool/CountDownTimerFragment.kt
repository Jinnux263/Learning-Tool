package com.example.learningtool

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.learningtool.dataBase.DataBaseHandler
import com.example.learningtool.dataBase.Item
import com.example.learningtool.databinding.FragmentCountDownTimerBinding
import java.util.*

var minute : Int = 0
var hour : Int = 0
var second :Int = 0
var timer : CountDownTimer? = null

val KEY_HOUR = "hour"
val KEY_MINUTE = "minute"

class RunCountDownTimer : Fragment() {
    lateinit var binding : FragmentCountDownTimerBinding
    lateinit var DataBase : DataBaseHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        DataBase = DataBaseHandler(context)
        binding = FragmentCountDownTimerBinding.inflate(layoutInflater)
        val bundle = this.requireArguments()
        hour = bundle.getInt(KEY_HOUR)
        minute = bundle.getInt(KEY_MINUTE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clockDisplay.text = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)

        clockRun()
    }


    override fun onStop() {
        super.onStop()
        hour = 0
        minute = 0
        second = 0
    }
    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
        }
    }

    fun clockRun(){
        val totalTime : Long = (hour.toLong() * 3600 + minute.toLong() * 60) * 1000
        var item = DataBase.getLast()
        if (item != null) {
            item.time = item.time?.plus(totalTime)
        } else {
            item = Item(totalTime)
        }

        timer = object: CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                convertTime(millisUntilFinished)
                binding.clockDisplay.text = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)
            }

            override fun onFinish() {
                if (DataBase.isEmpty()) {
                    DataBase.updateItem(0, item)
                } else {
                    DataBase.insertData(item)
                }
                val list = DataBase.readData()
                convertTime(item.time!!)
                binding.clockDisplay.text = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)
            }
        }
        timer!!.start()
    }

    fun convertTime(millisUntilFinished : Long){
        var millisTillEnd = millisUntilFinished
        hour = (millisTillEnd / 3600 / 1000).toInt();

        millisTillEnd = millisTillEnd % 3600000
        minute = (millisTillEnd / 60000).toInt()

        millisTillEnd = millisTillEnd % 60000
        second = (millisTillEnd / 1000).toInt()
    }

}