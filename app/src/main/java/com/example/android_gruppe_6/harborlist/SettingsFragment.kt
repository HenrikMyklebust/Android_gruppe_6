package com.example.android_gruppe_6.harborlist

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.android_gruppe_6.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}