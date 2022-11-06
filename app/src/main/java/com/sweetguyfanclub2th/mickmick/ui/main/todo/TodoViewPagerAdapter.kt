package com.sweetguyfanclub2th.mickmick.ui.main.todo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sweetguyfanclub2th.mickmick.ui.main.todo.menu.MapFragment
import com.sweetguyfanclub2th.mickmick.ui.main.todo.menu.ScheduleFragment

class TodoViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return ScheduleFragment()
            1 -> return MapFragment()
        }
        return MapFragment()
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }

    fun removeFragment() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }
}