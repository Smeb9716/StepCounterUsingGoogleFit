package com.lhd.fit.fragment.fragChart

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.BarEntry
import com.lhd.fit.customviews.SetupChart
import com.lhd.fit.databinding.FragmentWeekBinding
import com.lhd.fit.utils.Utils
import com.lhd.fit.viewmodel.HomeViewModel

class WeekFragment : Fragment() {

    private lateinit var mBinding: FragmentWeekBinding
    private lateinit var viewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentWeekBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        viewModel.getStepsByWeekOfMonth()
        observerComponent()
        return mBinding.root
    }

    private fun observerComponent() {
        viewModel.dataChartByWeekOfMonth.observe(viewLifecycleOwner) {
            if (it.lsBarEntry.size == 5) {
                displayChart(it.lsAxis, it.lsBarEntry)
            }
        }
    }

    private fun displayChart(
        lsAxis: ArrayList<String>,
        lsBarEntries: ArrayList<BarEntry>
    ) {
        lsAxis.add("")
        lsBarEntries.add(BarEntry(5f, null))

        SetupChart(context, mBinding.barChart, lsBarEntries, lsAxis, Utils.MAX_WEEK.toFloat())
            .applyOptions()
    }
}