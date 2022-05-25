package com.example.android_gruppe_6.showtide

import android.app.Application
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_gruppe_6.domain.Harbor
import com.example.android_gruppe_6.domain.TideData
import java.lang.IllegalArgumentException
import java.util.Calendar.DAY_OF_MONTH

class ShowTideViewModel(val harbor: Harbor, val app: Application): ViewModel() {
    private val _tides = MutableLiveData<List<TideData>>()
    val tides: LiveData<List<TideData>> get() = _tides

    private val _viewingDay = MutableLiveData<Int>()
    val viewingDay: LiveData<Int> get() = _viewingDay

    init {
        _tides.value = getDummyData()
        _viewingDay.value = getDay()
    }

    fun getDay(): Int {
        val rightNow: Int = Calendar.getInstance().get(DAY_OF_MONTH)
        return rightNow
    }


    class Factory(val harbor: Harbor, val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShowTideViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ShowTideViewModel(harbor, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

private fun getDummyData(): List<TideData> {
    return listOf(
        TideData("Bergen",2022,   5,   4,   0,    0,  -0.07,   0.46,   0.39,  -0.12,  -0.07,  -0.07,  -0.07,  -0.02),
        TideData("Bergen",2022,   5,   4,   1,    1,  -0.06,   0.35,   0.29,  -0.11,  -0.07,  -0.07,  -0.07,  -0.01),
        TideData("Bergen",2022,   5,   4,   2,    2,  -0.07,   0.12,   0.05,  -0.12,  -0.07,  -0.07,  -0.07,  -0.02),
        TideData("Bergen",2022,   5,   4,   3,    3,  -0.07,  -0.17,  -0.24,  -0.12,  -0.08,  -0.08,  -0.08,  -0.02),
        TideData("Bergen",2022,   5,   4,   4,    4,  -0.06,  -0.42,  -0.48,  -0.12,  -0.08,  -0.07,  -0.07,  -0.01),
        TideData("Bergen",2022,   5,   4,   5,    5,  -0.07,  -0.56,  -0.63,  -0.12,  -0.08,  -0.08,  -0.07,  -0.01),
        TideData("Bergen",2022,   5,   4,   6,    6,  -0.07,  -0.57,  -0.64,  -0.12,  -0.08,  -0.08,  -0.07,  -0.02),
        TideData("Bergen",2022,   5,   4,   7,    7,  -0.06,  -0.46,  -0.52,  -0.11,  -0.07,  -0.07,  -0.07,  -0.01),
        TideData("Bergen",2022,   5,   4,   8,    8,  -0.06,  -0.26,  -0.32,  -0.11,  -0.07,  -0.07,  -0.07,  -0.01),
        TideData("Bergen",2022,   5,   4,   9,    9,  -0.06,  -0.02,  -0.08,  -0.11,  -0.06,  -0.06,  -0.06,  -0.00),
        TideData("Bergen",2022,   5,   4,  10,   10,  -0.05,   0.21,   0.16,  -0.10,  -0.07,  -0.06,  -0.06,   0.01),
        TideData("Bergen",2022,   5,   4,  11,   11,  -0.03,   0.37,   0.34,  -0.09,  -0.06,  -0.06,  -0.05,   0.02),
        TideData("Bergen",2022,   5,   4,  12,   12,  -0.04,   0.43,   0.39,  -0.10,  -0.06,  -0.06,  -0.05,   0.01),
        TideData("Bergen",2022,   5,   4,  13,   13,  -0.04,   0.36,   0.32,  -0.09,  -0.06,  -0.06,  -0.06,   0.02),
        TideData("Bergen",2022,   5,   4,  14,   14,  -0.04,   0.17,   0.13,  -0.09,  -0.06,  -0.06,  -0.05,   0.02),
        TideData("Bergen",2022,   5,   4,  15,   15,  -0.04,  -0.10,  -0.14,  -0.10,  -0.06,  -0.05,  -0.05,   0.01),
        TideData("Bergen",2022,   5,   4,  16,   16,  -0.04,  -0.35,  -0.39,  -0.10,  -0.05,  -0.05,  -0.04,   0.02),
        TideData("Bergen",2022,   5,   4,  17,   17,  -0.04,  -0.50,  -0.54,  -0.09,  -0.05,  -0.04,  -0.04,   0.02),
        TideData("Bergen",2022,   5,   4,  18,   18,  -0.03,  -0.53,  -0.56,  -0.09,  -0.05,  -0.05,  -0.04,   0.03),
        TideData("Bergen",2022,   5,   4,  19,   19,  -0.03,  -0.43,  -0.46,  -0.09,  -0.04,  -0.04,  -0.04,   0.03),
        TideData("Bergen",2022,   5,   4,  20,   20,  -0.01,  -0.25,  -0.26,  -0.07,  -0.03,  -0.03,  -0.03,   0.05),
        TideData("Bergen",2022,   5,   4,  21,   21,  -0.01,  -0.03,  -0.04,  -0.07,  -0.04,  -0.03,  -0.03,   0.05),
        TideData("Bergen",2022,   5,   4,  22,   22,  -0.02,   0.18,   0.16,  -0.07,  -0.04,  -0.03,  -0.03,   0.04),
        TideData("Bergen",2022,   5,   4,  23,   23,  -0.01,   0.33,   0.32,  -0.07,  -0.03,  -0.02,  -0.02,   0.05)
    )
}