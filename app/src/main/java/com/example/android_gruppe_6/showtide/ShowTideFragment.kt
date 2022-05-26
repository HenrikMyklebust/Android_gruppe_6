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

        //TODO() REPLACE WITH ARGS
        //val harbor = Harbor("Test Havn", "BERGEN", 0.0, 0.0)
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

        val cartesian : Cartesian = AnyChart.line()
        cartesian.animation(true)
        cartesian.padding(10, 20, 5, 20)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair().yLabel(true).yStroke()

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("Tidevann for {viewModel.harbour.name}")

        cartesian.yAxis(0).title("Meter")
        cartesian.xAxis(0).labels().padding(5, 5, 5, 5)


        val seriesData = mutableListOf<DataEntry>()
        for (x in viewModel.tides.value.orEmpty()) {
            seriesData.add(CustomDataEntry(x.hour.toString(), x.total, x.tide, x.surge))
        }

        val set = Set.instantiate()
        set.data(seriesData)
        val series1Mapping = set.mapAs("{x: 'x', value: 'value' }")
        val series2Mapping = set.mapAs("{x: 'x', value: 'value2' }")
        val series3Mapping = set.mapAs("{x: 'x', value: 'value3' }")

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
        series3.name("Surge")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers().type(MarkerType.CIRCLE).size(4)
        series3.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5).offsetY(5)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13)
        cartesian.legend().padding(0, 0, 10, 0)

        binding.anyChartView.setChart(cartesian)



        // Inflate the layout for this fragment
        return binding.root
    }

}