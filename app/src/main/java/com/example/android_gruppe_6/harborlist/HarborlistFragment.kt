package com.example.android_gruppe_6.harborlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_gruppe_6.databinding.FragmentHarbourlistBinding

class HarborlistFragment : Fragment() {

    private val viewModel: HarborlistViewModel by lazy {
        val application = requireNotNull(activity).application
        ViewModelProvider(this, HarborlistViewModel.Factory(application))
            .get(HarborlistViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHarbourlistBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.rvHarbours.adapter = HarborlistRvAdapter(HarborlistRvAdapter.OnClickListener {
            viewModel.displayTide(it)
        })
        viewModel.navigateToSelectedHarbor.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(HarborlistFragmentDirections
                    .actionHarbourlistFragmentToShowTideFragment(it))
                viewModel.displayTideComplete()
            }
        })

        return binding.root
    }
}