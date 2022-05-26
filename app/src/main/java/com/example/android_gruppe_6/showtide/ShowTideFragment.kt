package com.example.android_gruppe_6.showtide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.charts.Pie
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.android_gruppe_6.databinding.FragmentShowTideBinding
import com.example.android_gruppe_6.domain.Harbor

class ShowTideFragment : Fragment() {

    private val viewModel: ShowTideViewModel by lazy {
        val application = requireNotNull(activity).application

        val harbor = ShowTideFragmentArgs.fromBundle(requireArguments()).harbor

        ViewModelProvider(this, ShowTideViewModel.Factory(harbor, application))
            .get(ShowTideViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentShowTideBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.anyChartView.setProgressBar(binding.progressBar)

        binding.anyChartView.setChart(viewModel.cartesian.value)

        // Inflate the layout for this fragment
        return binding.root
    }

}