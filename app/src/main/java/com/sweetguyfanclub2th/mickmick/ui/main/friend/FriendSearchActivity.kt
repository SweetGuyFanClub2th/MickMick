package com.sweetguyfanclub2th.mickmick.ui.main.friend


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import kotlinx.android.synthetic.main.activity_friendsearch.*
import kotlinx.android.synthetic.main.friend_search_layout.view.*

//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.item.view.*

class FriendSearchActivity : AppCompatActivity() {

    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friendsearch)

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        recyclerview.adapter = RecyclerViewAdapter()
        recyclerview.layoutManager = LinearLayoutManager(this)

        var searchOption = "nickname"

        // 스피너 옵션에 따른 동작
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (spinner.getItemAtPosition(position)) {
                    "닉네임" -> {
                        searchOption = "nickname"
                    }
                    "이메일" -> {
                        searchOption = "email1"
                    }
                }
            }
        }
        searchBtn.setOnClickListener {
            (recyclerview.adapter as RecyclerViewAdapter).search(searchWord.text.toString(), searchOption)
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // Person 클래스 ArrayList 생성성
        var userName : ArrayList<Person> = arrayListOf()
        init {  // telephoneBook의 문서를 불러온 뒤 Person으로 변환해 ArrayList에 담음
            firestore?.collection("test")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                userName.clear()


                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(Person::class.java)
                    userName.add(item!!)
                }
                notifyDataSetChanged()
            }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.friend_search_layout, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView

            viewHolder.nickname.text = userName[position].nickname
            viewHolder.email1.text = userName[position].email1
        }


        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return userName.size
        }
        fun search(searchWord : String, option : String) {
            firestore?.collection("test")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                userName.clear()

                for (snapshot in querySnapshot!!.documents) {
                    if (snapshot.getString(option)!!.contains(searchWord)) {
                        var item = snapshot.toObject(Person::class.java)
                        userName.add(item!!)
                    }
                }

                notifyDataSetChanged()
            }
        }
    }
}
