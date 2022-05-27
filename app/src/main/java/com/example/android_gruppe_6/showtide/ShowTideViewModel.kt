package com.example.android_gruppe_6.showtide

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.*
import com.anychart.chart.common.dataentry.DataEntry
import com.example.android_gruppe_6.database.getDatabase
import com.example.android_gruppe_6.domain.Harbor
import com.example.android_gruppe_6.domain.TideData
import com.example.android_gruppe_6.repository.TideRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.*

class ShowTideViewModel(val harbor: Harbor, val app: Application) : ViewModel() {
    private val _tides = MutableLiveData<List<TideData>>()
    val tides: LiveData<List<TideData>> get() = _tides

    private val _days = mutableListOf<Int>()
    private var _daysIndex: Int = 0

    private val _dataset = MutableLiveData<List<DataEntry>>()
    val dataset: LiveData<List<DataEntry>> = _dataset

    private var _dayOfMonth: Int = 0
    val dayOfMonth: Int get() = _dayOfMonth
    private var _month: Int = 0
    val month: Int get() = _month
    private var _year: Int = 0
    val year: Int get() = _year

    private val _apiRequest = MutableLiveData<Boolean>()
    val apiRequest: LiveData<Boolean> get() = _apiRequest

    private val _displayingDay = MutableLiveData<Int>()
    private val _lastEntry = MutableLiveData<Boolean>()
    val lastEntry: LiveData<Boolean> get() = _lastEntry

    private val _firstEntry = MutableLiveData<Boolean>()
    val firstEntry: LiveData<Boolean> get() = _firstEntry

    private val repository = TideRepository(getDatabase(app.applicationContext))

    init {
        val temp = Calendar.getInstance()
        _month = temp.get(MONTH)
        _year = temp.get(YEAR)
        _dataset.value = getDataset()
        viewModelScope.launch {
            _tides.value = getTide()

            for (x in _tides.value.orEmpty()) {
                if (x.day !in _days) {
                    _days.add(x.day)
                }
            }
            _daysIndex = _days.indexOf(getDay())
            if (_daysIndex == -1) {
                _daysIndex = 0
            }
            _dayOfMonth = _days.get(_daysIndex)
            _dataset.value = getDataset()
        }
    }

    fun showNextDay() {
        if (_daysIndex < _days.size - 1) {
            _daysIndex += 1
            _dataset.value = getDataset()
            _dayOfMonth = _days[_daysIndex]
        }
    }

    fun showPreviousDay() {
        if (_daysIndex > 0){
            _daysIndex -= 1
            _dataset.value = getDataset()
            _dayOfMonth = _days[_daysIndex]
        }

    }

    fun getDay(): Int {
        val rightNow: Int = Calendar.getInstance().get(DAY_OF_MONTH)
        return rightNow
    }

    private suspend fun getTide(): List<TideData> {
        var tide: List<TideData>

        tide = repository.getDbTide(harbor.apiName)

        if (tide.isNullOrEmpty()) {
            try {
                repository.insertTides(repository.getApiTide(harbor.apiName))
                tide = repository.getDbTide(harbor.apiName)
            }catch (e: Exception) {
                _apiRequest.value = false
            }
            _apiRequest.value = true

        } else if (tide[0].day != getDay()) {
            try {
                repository.insertTides(repository.getApiTide(harbor.apiName))
                tide = repository.getDbTide(harbor.apiName)
            }catch (e: Exception) {
                _apiRequest.value = false
            }
            _apiRequest.value = true
        }
        return tide
    }

    private fun getDataset() : List<DataEntry>{
        val seriesData = mutableListOf<DataEntry>()
        if (_tides.value == null) {
            seriesData.add(CustomDataEntry("0", 0, 0,0))
            return seriesData
        }
        for (x in _tides.value.orEmpty()) {
            if (x.day == _days[_daysIndex]) {
                seriesData.add(CustomDataEntry(x.hour.toString(), x.total, x.tide, x.surge))
                if (x.month != _month) {
                    _month = x.month
                }
            }
        }
        return seriesData
    }


    class Factory(val harbor: Harbor, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShowTideViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ShowTideViewModel(harbor, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
