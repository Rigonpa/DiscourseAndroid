package com.example.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eh_ho.R
import com.example.eh_ho.data.PostsRepo
import com.example.eh_ho.data.RequestError
import com.example.eh_ho.inflate
import com.example.eh_ho.topics.LoadingDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_topic.*
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment(private val topicId: String) : Fragment() {

    private val loadingDialogFragment = LoadingDialogFragment()
    private val postsAdapter: PostsAdapter by lazy {
        val postsAdapter = PostsAdapter {
            // TODO: Post detail appears when touching its cell
        }
        postsAdapter
    }

    private var postsInteractionListener: PostsInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PostsInteractionListener)
            postsInteractionListener = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_posts)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_posts, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_post)
            this.postsInteractionListener?.addPostButtonPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRecyclerView.layoutManager = LinearLayoutManager(context)
        postsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        postsRecyclerView.adapter = postsAdapter

        swipeRefresh.setOnRefreshListener {
            getPosts()
            swipeRefresh.isRefreshing = false
        }

    }

    override fun onResume() {
        super.onResume()

        getPosts()
    }

    private fun getPosts() {
        loadingDialogFragment.show(childFragmentManager, null)
        context?.let { context ->
            PostsRepo.getPosts(
                topicId,
                context.applicationContext,
                {
                    loadingDialogFragment.dismiss()
                    postsAdapter.setPosts(it)
                },
                {
                    loadingDialogFragment.dismiss()
                    handleError(it)
                }
            )
        }
    }

    private fun handleError(error: RequestError) {
        val message = if (error.messageResId != null)
            getString(error.messageResId)
        else error.message ?: getString(R.string.error_default)

        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        postsInteractionListener = null
    }

    interface PostsInteractionListener {
        fun addPostButtonPressed()
    }
}