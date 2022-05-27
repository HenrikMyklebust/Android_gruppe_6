package com.example.android_gruppe_6.harborlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android_gruppe_6.R
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuSettings -> {
                val action = HarborlistFragmentDirections.actionHarbourlistFragmentToSettingsFragment()
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}