package com.sweetguyfanclub2th.mickmick.ui.main.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.IconData
import com.sweetguyfanclub2th.mickmick.databinding.ActivityChangeIconBinding

class ChangeIconActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeIconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeIconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recyclerViewItems = ArrayList<IconData>()

        val imgList: List<Int> = listOf(
            R.drawable.c_red,
            R.drawable.c_yellow,
            R.drawable.c_green,
            R.drawable.c_blue,
            R.drawable.c_purple,
            R.drawable.c_gray,
        )

        val nameList: List<String> = listOf(
            "불부리", "다양이", "미크", "리버피", "꼬물이", "딴딴이"
        )

        for (i in imgList.indices) {
            recyclerViewItems.add(
                IconData(imgList[i], nameList[i], "아이콘을 누르면 프로필이 변경됩니다.")
            )
        }

        binding.iconRecycler.layoutManager = LinearLayoutManager(this)
        binding.iconRecycler.adapter = IconAdapter(recyclerViewItems)

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}