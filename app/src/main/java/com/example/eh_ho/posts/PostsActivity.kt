package com.example.eh_ho.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eh_ho.R
import com.example.eh_ho.data.RequestError
import com.example.eh_ho.data.Topic
import com.example.eh_ho.data.TopicsRepo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_topic.*

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity(), PostsFragment.PostsInteractionListener,
    CreatePostFragment.CreatePostInteractionListener {

    var topic: Topic? = null
    private var postsFragment: PostsFragment? = null
    private var createPostsFragment: CreatePostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        title = "Posts"

        val topicId = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        topic = TopicsRepo.getTopicById(topicId)

        /* NOT NECESSARY
        TopicsRepo.getTopic(
            topicId,
            this.applicationContext,
            {
                topic = it
            },
            {
                handleError(it)
            }
        )
         */

        topic?.let {
            postsFragment = PostsFragment(it.id)
            createPostsFragment = CreatePostFragment(it)
        }

        if (savedInstanceState == null) {
            postsFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.postsContainer, it)
                    .commit()
            }
        }
    }

    private fun handleError(error: RequestError) {
        val message = if (error.messageResId != null)
            getString(error.messageResId)
        else error.message ?: getString(R.string.error_default)

        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
    }

    override fun addPostButtonPressed() {
        createPostsFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.postsContainer, it)
                .addToBackStack("create_post")
                .commit()
        }
    }

    override fun onPostCreated() {
        supportFragmentManager.popBackStack()
    }
}