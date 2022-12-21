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
import com.lhd.fit.databinding.FragmentDayBinding
import com.lhd.fit.utils.Utils
import com.lhd.fit.viewmodel.HomeViewModel
import java.util.*

const val TAG = "DayFragment"

class DayFragment : Fragment() {

    private lateinit var mBinding: FragmentDayBinding
    private lateinit var viewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDayBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        observerChart()
        return mBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observerChart() {
        viewModel.dataChartByWeek.observe(viewLifecycleOwner) {
            displayChart(it.lsAxis, it.lsBarEntry)
        }
//        viewModel.isSignIn.observe(viewLifecycleOwner) {
//            if (it) {
//                viewModel.getStepsByDayOfWeek()
//            }
//        }
    }

    private fun displayChart(lsAxis: ArrayList<String>, lsBarEntries: ArrayList<BarEntry>) {
        SetupChart(context, mBinding.barChart, lsBarEntries, lsAxis, Utils.MAX_DAY.toFloat())
            .applyOptions()
    }

}