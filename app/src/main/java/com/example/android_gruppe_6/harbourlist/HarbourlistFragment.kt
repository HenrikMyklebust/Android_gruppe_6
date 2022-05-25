package com.example.android_gruppe_6.harbourlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_gruppe_6.R
import com.example.android_gruppe_6.databinding.FragmentHarbourlistBinding

class HarbourlistFragment : Fragment() {

    private val viewModel: HarbourlistViewModel by lazy {
        val application = requireNotNull(activity).application
        ViewModelProvider(this, HarbourlistViewModel.Factory(application))
            .get(HarbourlistViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHarbourlistBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.rvHarbours.adapter = HarbourlistRvAdapter(HarbourlistRvAdapter.OnClickListener {
            viewModel.displayTide(it)
        })
        viewModel.navigateToSelectedHarbour.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(HarbourlistFragmentDirections
                    .actionHarbourlistFragmentToShowTideFragment(it))
                viewModel.displayTideComplete()
            }
        })

        return binding.root
    }
}