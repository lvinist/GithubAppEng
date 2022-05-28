package com.dicoding.submission2.ui.folls

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(fm: FragmentManager, lc: Lifecycle, data: Bundle) : FragmentStateAdapter(fm, lc) {

    private var fragmentBundle: Bundle = data

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment.arguments = this.fragmentBundle
        return fragment
    }
}