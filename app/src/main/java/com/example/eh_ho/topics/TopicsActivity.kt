package com.example.eh_ho.topics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.eh_ho.*
import com.example.eh_ho.data.Topic
import com.example.eh_ho.data.UserRepo
import kotlinx.android.synthetic.main.activity_topics.*

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity : AppCompatActivity(), TopicsFragment.TopicsInteractionListener,
    CreateTopicFragment.CreateTopicInteractionListener, ErrorTopicsInteractionListener {

    private val topicsFragment = TopicsFragment()
    private val createTopicFragment = CreateTopicFragment()
    private val errorFragment = ErrorFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        if (isFirstTimeCreated(savedInstanceState))
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, topicsFragment)
                .commit()
    }

    private fun goToPosts(topic: Topic) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, topic.id)
        startActivity(intent)
    }

    override fun onCreateTopic() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, createTopicFragment)
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }

    override fun onShowPosts(topic: Topic) {
        goToPosts(topic)
    }

    override fun onErrorTopics() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, errorFragment, null)
            .commit()
    }

    override fun onTopicCreated() {
        supportFragmentManager.popBackStack()
    }

    override fun onLogout() {
        // Delete data and go to login activity
        UserRepo.logout(this.applicationContext)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun retry() {
        supportFragmentManager.beginTransaction()
            .remove(errorFragment)
            .commit()
        topicsFragment.loadingTopics()
    }
}