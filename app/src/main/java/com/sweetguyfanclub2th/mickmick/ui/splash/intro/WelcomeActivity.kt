package com.sweetguyfanclub2th.mickmick.ui.splash.intro

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityWelcomeBinding
import com.sweetguyfanclub2th.mickmick.ui.login.LoginActivity
import com.sweetguyfanclub2th.mickmick.ui.splash.MyIntroPagerRecyclerAdapter
import kotlin.concurrent.thread

class WelcomeActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "로그"
    }

    private var pageItemList = ArrayList<PageItem>()
    private lateinit var myIntroPagerRecyclerAdapter: MyIntroPagerRecyclerAdapter
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.previousBtn.setOnClickListener {
            Log.d(TAG, "WelcomeActivity - 이전 버튼 클릭")
            binding.myIntroViewPager.currentItem = binding.myIntroViewPager.currentItem - 1
        }

        binding.nextBtn.setOnClickListener {
            Log.d(TAG, "WelcomeActivity - 다음 버튼 클릭")
            when(binding.myIntroViewPager.currentItem){
                2 -> moveLoginPage()
                else -> checkNotThird()
            }
        }

        pageItemList.add(PageItem(R.color.white, R.drawable.ic_baseline_check_box_24, "화면1"))
        pageItemList.add(PageItem(R.color.white, R.drawable.ic_baseline_check_box_24, "화면2"))
        pageItemList.add(PageItem(R.color.white, R.drawable.ic_baseline_check_box_24, "화면3"))
        pageItemList.add(PageItem(R.color.white, R.drawable.ic_baseline_check_box_24, "화면4"))

        myIntroPagerRecyclerAdapter = MyIntroPagerRecyclerAdapter(pageItemList)

        // 뷰페이저에 설정
        binding.myIntroViewPager.apply {
            adapter = myIntroPagerRecyclerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            binding.dotsIndicator.setViewPager2(this)
        }

    }

    private fun checkNotThird() {
        binding.myIntroViewPager.currentItem = binding.myIntroViewPager.currentItem + 1
    }

    private fun moveLoginPage(){
        checkNotThird()
        binding.welcomeButtonToNextActivity.visibility = View.VISIBLE
        binding.welcomeButtonToNextActivity.setOnClickListener {
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}