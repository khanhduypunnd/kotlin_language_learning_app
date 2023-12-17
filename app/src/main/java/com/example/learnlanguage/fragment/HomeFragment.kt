package com.example.learnlanguage.fragment

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.learnlanguage.R
import com.example.learnlanguage.adapter.HomePagerAdapter
import com.example.learnlanguage.base.BaseFragment
import com.example.learnlanguage.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var homePagerAdapter: HomePagerAdapter
    companion object {
        internal val TAG = HomeFragment::class.java.name
    }

    override fun getVM(): ViewModel {
        TODO("Not yet implemented")
    }

    override fun initViews() {

        homePagerAdapter = HomePagerAdapter(requireActivity())
        binding.viewpager2Home.adapter = homePagerAdapter
        binding.viewpager2Home.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.layoutBottomBar.menu.findItem(R.id.menu_folder).isChecked = true
                    1 -> binding.layoutBottomBar.menu.findItem(R.id.menu_topic).isChecked = true
                    2 -> binding.layoutBottomBar.menu.findItem(R.id.menu_study).isChecked = true
                    3 -> binding.layoutBottomBar.menu.findItem(R.id.menu_profile).isChecked = true
                }
            }
        })

        binding.layoutBottomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_folder -> binding.viewpager2Home.currentItem = 0
                R.id.menu_topic -> binding.viewpager2Home.currentItem = 1
                R.id.menu_study -> binding.viewpager2Home.currentItem = 2
                R.id.menu_profile -> binding.viewpager2Home.currentItem = 3
            }
            true
        }
    }

    override fun getLayoutId() = R.layout.fragment_home
}