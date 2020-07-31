package com.example.eh_ho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eh_ho.data.Topic
import com.example.eh_ho.data.TopicsRepo
import kotlinx.android.synthetic.main.activity_posts.*

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

//        if (intent.getStringExtra(EXTRA_TOPIC_ID) == null)
        val topicId = intent.getStringExtra(EXTRA_TOPIC_ID) ?: "" // Elvis operator
        val topic: Topic? = TopicsRepo.getTopic(topicId)

        topic?.let {
            labelTitle.text = it.title
        }
//        labelTitle.text = topic?.title
    }
}