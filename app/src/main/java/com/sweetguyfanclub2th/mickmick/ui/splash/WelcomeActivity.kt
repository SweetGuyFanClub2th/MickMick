package com.sweetguyfanclub2th.mickmick.ui.splash

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityMainBinding
import com.sweetguyfanclub2th.mickmick.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "로그"
    }

    private var pageItemList = ArrayList<PageItem>()
    private lateinit var myIntroPagerRecyclerAdapter: WelcomeViewHolder
    private lateinit var binding: ActivityWelcomeBinding


    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.previousBtn.setOnClickListener {
            Log.d(TAG, "MainActivity - 이전 버튼 클릭")

            binding.myIntroViewPager.currentItem = binding.myIntroViewPager.currentItem - 1
        }

        binding.nextBtn.setOnClickListener {
            Log.d(TAG, "MainActivity - 다음 버튼 클릭")
            binding.myIntroViewPager.currentItem = binding.myIntroViewPager.currentItem + 1
        }

        pageItemList.add(PageItem(R.color.MICKMICK, R.drawable.ic_baseline_emoji_emotions_24, "화면1"))
        pageItemList.add(PageItem(R.color.MICKMICK, R.drawable.ic_baseline_emoji_people_24, "화면2"))
        pageItemList.add(PageItem(R.color.MICKMICK, R.drawable.ic_baseline_escalator_warning_24, "화면3"))
        pageItemList.add(PageItem(R.color.MICKMICK, R.drawable.ic_baseline_directions_bike_24, "화면4"))

        myIntroPagerRecyclerAdapter = MyIntroPagerRecyclerAdapter(pageItemList)

        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.

        actionBar?.hide()

        // 뷰페이저에 설정
        binding.myIntroViewPager.apply {

            adapter = myIntroPagerRecyclerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL

//            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
////                    supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorBlue)))
//                }
//
//            })

            binding.dotsIndicator.setViewPager2(this)
        }

    }

}