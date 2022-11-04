package com.sweetguyfanclub2th.mickmick.ui.splash.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sweetguyfanclub2th.mickmick.databinding.ActivityWelcomeBinding
import com.sweetguyfanclub2th.mickmick.ui.login.LoginActivity
import com.sweetguyfanclub2th.mickmick.ui.splash.WelcomeAdapter

class WelcomeActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "로그"
    }

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var viewPager2Adatper: WelcomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
    }

    private fun checkThird(){
        binding.welcomeButtonToNextActivity.visibility = View.INVISIBLE
    }

    private fun checkNotThirdAndPlusItem() {
        binding.welcomeButtonToNextActivity.visibility = View.INVISIBLE
        binding.myIntroViewPager.currentItem = binding.myIntroViewPager.currentItem + 1
    }

    private fun moveLoginPage(){
        checkNotThirdAndPlusItem()
        binding.welcomeButtonToNextActivity.visibility = View.VISIBLE
        binding.welcomeButtonToNextActivity.setOnClickListener {
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    private fun initViewPager() {
        viewPager2Adatper = WelcomeAdapter(this)
        viewPager2Adatper.addFragment(ViewPagerPage1())
        viewPager2Adatper.addFragment(ViewPagerPage2())
        viewPager2Adatper.addFragment(ViewPagerPage3())
        viewPager2Adatper.addFragment(ViewPagerPage4())

        binding.myIntroViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d(TAG, "position: $position")
                when (position) {
                    3 -> {
                        moveLoginPage()
                    }
                    else -> {
                        checkThird()
                    }
                }
            }
        })

        binding.myIntroViewPager.apply {
            adapter = viewPager2Adatper
            orientation = binding.myIntroViewPager.currentItem

            binding.dotsIndicator.setViewPager2(this)
        }
    }
}