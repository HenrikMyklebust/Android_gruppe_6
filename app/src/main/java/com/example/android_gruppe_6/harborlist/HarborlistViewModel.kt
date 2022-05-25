package com.example.android_gruppe_6.harborlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_gruppe_6.domain.Harbor
import com.example.android_gruppe_6.domain.getHarbors
import java.lang.IllegalArgumentException

class HarborlistViewModel(val app: Application): ViewModel() {
    private val _harbors = MutableLiveData<List<Harbor>>()
    val harbors : LiveData<List<Harbor>> get() = _harbors
    private val _favorites = MutableLiveData<List<Harbor>>()
    val favorites : LiveData<List<Harbor>> get() = _favorites
    private val _navigateToSelectedHarbor = MutableLiveData<Harbor>()
    val navigateToSelectedHarbor: LiveData<Harbor> get() = _navigateToSelectedHarbor

    init {
        _harbors.value = getHarbors()
    }

    fun displayTide(harbour: Harbor) {
        _navigateToSelectedHarbor.value = harbour
    }
    fun displayTideComplete() {
        _navigateToSelectedHarbor.value = null
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HarborlistViewModel::class.java)) {

                @Suppress("UNCHECKED_CAST")
                return HarborlistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
