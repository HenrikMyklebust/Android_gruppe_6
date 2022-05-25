package com.example.android_gruppe_6.showtide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android_gruppe_6.R
import com.example.android_gruppe_6.data_class.Harbour
import com.example.android_gruppe_6.databinding.FragmentShowTideBinding

class ShowTideFragment : Fragment() {

    private val viewModel: ShowTideViewModel by lazy {
        val application = requireNotNull(activity).application

        //TODO() REPLACE WITH ARGS
        val harbour = Harbour("Test Havn", "BERGEN", 0.0, 0.0)
        //ShowTideFragmentArgs.fromBundle(requireArguments()).selectedHarbour

        ViewModelProvider(this, ShowTideViewModel.Factory(harbour, application))
            .get(ShowTideViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShowTideBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.tvTEST.text = viewModel.tides.value.toString()

        // Inflate the layout for this fragment
        return binding.root
    }
}