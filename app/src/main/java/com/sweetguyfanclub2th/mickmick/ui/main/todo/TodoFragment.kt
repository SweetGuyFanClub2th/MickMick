package com.sweetguyfanclub2th.mickmick.ui.main.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sweetguyfanclub2th.mickmick.databinding.ActivityLoginBinding
import com.sweetguyfanclub2th.mickmick.databinding.FragmentTodoBinding
import com.sweetguyfanclub2th.mickmick.ui.main.todo.menu.MapFragment
import com.sweetguyfanclub2th.mickmick.ui.main.todo.menu.ScheduleFragment


class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val tabTitleArray = arrayOf("Schedule", "Map")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = TodoViewPagerAdapter(requireActivity())
        viewPagerAdapter.addFragment(MapFragment())
        viewPagerAdapter.addFragment(ScheduleFragment())

        binding.todoViewPager.adapter = viewPagerAdapter
        binding.todoViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.todoViewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}