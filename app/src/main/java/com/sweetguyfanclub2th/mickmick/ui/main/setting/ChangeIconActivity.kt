package com.sweetguyfanclub2th.mickmick.ui.main.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityChangeIconBinding

class ChangeIconActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeIconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeIconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recyclerViewItems = ArrayList<IconData>()

        for (i in 0 .. 15) {
            recyclerViewItems.add(
                IconData(
                    R.drawable.profile_logo,
                    "초록색 미크미크짱",
                    "여기에 설명을 써주시면 베리 나이스한 것이에요."
                )
            )
        }

        binding.iconRecycler.layoutManager = LinearLayoutManager(this)
        binding.iconRecycler.adapter = IconAdapter(recyclerViewItems)

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}