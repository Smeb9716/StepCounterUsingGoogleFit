package com.lhd.fit.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.lhd.fit.R
import com.lhd.fit.adapter.ReceiveAdapter
import com.lhd.fit.customviews.MySeekBar
import com.lhd.fit.customviews.modelCustomView.ReceiveSeekbar
import com.lhd.fit.databinding.FragmentHomeBinding
import com.lhd.fit.fragment.fragChart.DayFragment
import com.lhd.fit.fragment.fragChart.MonthFragment
import com.lhd.fit.fragment.fragChart.WeekFragment
import com.lhd.fit.interfacePresenter.HomeInterface
import com.lhd.fit.viewmodel.HomeViewModel
import kotlin.collections.ArrayList


const val TAG = "HomeFragment"

class HomeFragment(private val goToReceive: HomeInterface) : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding
    private var myAdapter = ReceiveAdapter(arrayListOf(), 0)
    private var lsIconReceive = ArrayList<ReceiveSeekbar>()
    private lateinit var viewModel: HomeViewModel
    private var fragment: Fragment? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        viewModel.checkPermission(requireActivity())
        mBinding.homePresenter = viewModel
        //
        setUpUI()
        setupMySeekBar()
        setUpRcv()
        observerComponent()
        return mBinding.root
    }

    private fun observerComponent() {
        viewModel.process.observe(viewLifecycleOwner) {
            mBinding.mySeekBar.progress = it / 4500f
            mBinding.numSteps.text = it.toString()
        }
        viewModel.challengerCollected.observe(viewLifecycleOwner) {
            myAdapter.addData(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // listener
        with(mBinding) {
            tvViewAll.setOnClickListener {
                goToReceive.replaceReceive(ReceiveFragment())
            }

            mySeekBar.setOnClickListener(object : MySeekBar.OnClickBitmapReceive {
                override fun clickItem(index: Int) {
                    /**
                     * 1, neu isDisable = false => CLICK
                     * 2, update tr???ng th??i c???a lsIconReceive[index] isisDisable = true
                     * 3, truy???n ls m??i update v??o MySeekBar => update l???i view
                     */
                    if (!lsIconReceive[index].isDisable) {
                        lsIconReceive[index].isDisable = !lsIconReceive[index].isDisable
                        Log.e("CLICKED  ", "clickItem: $index")

                        // X??? l?? logic click button ....

                        updateSeekBar(lsIconReceive)
                    }
                }
            })
        }
    }

    /**
     * Update l???i seekbar
     */
    private fun updateSeekBar(lsIconReceive: ArrayList<ReceiveSeekbar>) {
        mBinding.mySeekBar.indicatorBitmapReceive = lsIconReceive
        mBinding.mySeekBar.invalidate()
    }

    /**
     * Add v??? tr?? c???a indicator
     * Add text
     */
    private fun setupMySeekBar() {
        addIconReceive()
        mBinding.mySeekBar.indicatorPositions = listOf(0F, 0.11F, 0.25F, 0.89F)
        mBinding.mySeekBar.indicatorText = listOf("0", "500", "1000", "4000")
        mBinding.mySeekBar.indicatorBitmapReceive = lsIconReceive
    }

    private fun setUpRcv() {
        mBinding.rcv.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }
    }

    private fun addIconReceive() {
        lsIconReceive.add(ReceiveSeekbar(0, true))
        lsIconReceive.add(ReceiveSeekbar(R.drawable.reiceve1, false))
        lsIconReceive.add(ReceiveSeekbar(R.drawable.receive2, false))
        lsIconReceive.add(ReceiveSeekbar(R.drawable.reiceve3, false))
    }


    private fun setupTabLayout() {
        with(mBinding) {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> fragment = DayFragment()
                        1 -> fragment = WeekFragment()
                        2 -> fragment = MonthFragment()
                    }
                    replaceFragment(fragment)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun replaceFragment(fragment: Fragment?) {
        val fm = requireActivity().supportFragmentManager
        val ft = fm.beginTransaction()
        if (fragment != null) {
            ft.replace(R.id.frameFrg, fragment)
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }

    private fun setUpUI() {
        setupTabLayout()
        replaceFragment(DayFragment())
    }

}