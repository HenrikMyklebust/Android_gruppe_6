package com.example.android_gruppe_6.harbourlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_gruppe_6.data_class.Harbour
import com.example.android_gruppe_6.data_class.getHarbours
import java.lang.IllegalArgumentException

class HarbourlistViewModel(val app: Application): ViewModel() {
    private val _harbours = MutableLiveData<List<Harbour>>()
    val harbours : LiveData<List<Harbour>> get() = _harbours
    private val _favorites = MutableLiveData<List<Harbour>>()
    val favorites : LiveData<List<Harbour>> get() = _favorites
    private val _navigateToSelectedHarbour = MutableLiveData<Harbour>()
    val navigateToSelectedHarbour: LiveData<Harbour> get() = _navigateToSelectedHarbour

    init {
        _harbours.value = getHarbours()
    }

    fun displayTide(harbour: Harbour) {
        _navigateToSelectedHarbour.value = harbour
    }
    fun displayTideComplete() {
        _navigateToSelectedHarbour.value = null
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HarbourlistViewModel::class.java)) {

                @Suppress("UNCHECKED_CAST")
                return HarbourlistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
