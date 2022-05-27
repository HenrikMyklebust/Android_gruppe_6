package com.example.android_gruppe_6.showtide

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.anychart.APIlib
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
import com.example.android_gruppe_6.R
import com.example.android_gruppe_6.databinding.FragmentShowTideBinding
import com.example.android_gruppe_6.domain.Harbor
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShowTideFragment : Fragment() {

    private lateinit var hideBottomNav: BottomNavigationView

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
        hideBottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        hideBottomNav.isVisible = false
        val binding = FragmentShowTideBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val set: Set = Set.instantiate()
        set.data(viewModel.dataset.value)
        val cartesian : Cartesian = AnyChart.line()
        val series1Mapping = set.mapAs("{x: 'x', value: 'value' }")
        val series2Mapping = set.mapAs("{x: 'x', value: 'value2' }")
        val series3Mapping = set.mapAs("{x: 'x', value: 'value3' }")


        binding.anyChartView.setProgressBar(binding.progressBar)

        cartesian.animation(true)
        cartesian.padding(10, 20, 5, 20)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair().yLabel(true).yStroke()

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.yAxis(0).title("Meter")
        cartesian.xAxis(0).title("Hour")
        cartesian.xAxis(0).labels().padding(5, 5, 5, 5)

        val series1 = cartesian.line(series1Mapping)
        series1.name("Total")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers().type(MarkerType.CIRCLE).size(4)
        series1.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5).offsetY(5)

        val series2 = cartesian.line(series2Mapping)
        series2.name("Tide")
        series2.hovered().markers().enabled(true)
        series2.hovered().markers().type(MarkerType.CIRCLE).size(4)
        series2.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5).offsetY(5)

        val series3 = cartesian.line(series3Mapping)
        series3.name("Storm Surge")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers().type(MarkerType.CIRCLE).size(4)
        series3.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5).offsetY(5)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13)
        cartesian.legend().padding(0, 0, 10, 0)

        binding.anyChartView.setChart(cartesian)

        viewModel.dataset.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                APIlib.getInstance().setActiveAnyChartView(binding.anyChartView)
                set.data(viewModel.dataset.value)
            }
        })
        viewModel.dayOfMonth.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.dataTitle.text = "${viewModel.harbor.name} : ${viewModel.dayOfMonth.value}" +
                        "/${viewModel.month.value}/${viewModel.year.value}"
            }
        })

        binding.navShowTides.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bnbNextDay -> viewModel.showNextDay()
                R.id.bnbPopShowTide -> findNavController().popBackStack()
                R.id.bnbPreviousDay -> viewModel.showPreviousDay()
            }
            true
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideBottomNav.isVisible = true
    }


}