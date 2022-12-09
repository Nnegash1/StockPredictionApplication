package com.example.stockpredictionapplication.view.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.stockpredictionapplication.R
import com.example.stockpredictionapplication.databinding.FragmentGraphScreenBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class GraphScreen : Fragment() {

    private lateinit var binding: FragmentGraphScreenBinding
    private val vm: GraphScreenViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGraphScreenBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLineChartData()
        vm.getGlobalUpdate()
    }

    private fun setLineChartData() {

        val linevalues = ArrayList<Entry>()
        linevalues.add(Entry(20f, 0.0F))
        linevalues.add(Entry(30f, 3.0F))
        linevalues.add(Entry(40f, 2.0F))
        linevalues.add(Entry(50f, 1.0F))
        linevalues.add(Entry(60f, 8.0F))
        linevalues.add(Entry(70f, 10.0F))
        linevalues.add(Entry(80f, 1.0F))
        linevalues.add(Entry(90f, 2.0F))
        linevalues.add(Entry(100f, 5.0F))
        linevalues.add(Entry(110f, 1.0F))
        linevalues.add(Entry(120f, 20.0F))
        linevalues.add(Entry(130f, 40.0F))
        linevalues.add(Entry(140f, 50.0F))

        val linedataset = LineDataSet(linevalues, "First")
        //We add features to our chart
        linedataset.color = resources.getColor(R.color.teal_200)

        linedataset.circleRadius = 10f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 16F
        linedataset.valueTextColor = resources.getColor(R.color.white)
        linedataset.fillColor = resources.getColor(R.color.teal_700)
        linedataset.mode = LineDataSet.Mode.CUBIC_BEZIER;

        //We connect our data to the UI Screen
        val data = LineData(linedataset)
        binding.getTheGraph.data = data
        binding.getTheGraph.axisLeft.textColor = resources.getColor(R.color.white)
        binding.getTheGraph.xAxis.axisLineColor = resources.getColor(R.color.white)
        binding.getTheGraph.setBackgroundColor(resources.getColor(R.color.black))
        binding.getTheGraph.animateXY(2000, 2000, Easing.EaseInCubic)

    }
}