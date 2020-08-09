package com.example.eh_ho.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eh_ho.R
import com.example.eh_ho.data.Topic
import com.example.eh_ho.data.TopicsRepo

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {

    var topic: Topic? = null
    private var postsFragment: PostsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        title = "Posts"

        val topicId = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        postsFragment = PostsFragment(topicId)
        topic = TopicsRepo.getTopic(topicId)

        topic?.let {
//            labelTitle.text = it.title
        }

        if (savedInstanceState == null) {
            postsFragment?.let {
                supportFragmentManager.beginTransaction()
                    .add(R.id.postsContainer, it)
                    .commit()
            }
        }
    }
}