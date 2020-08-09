package com.example.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.eh_ho.R
import com.example.eh_ho.data.*
import com.example.eh_ho.inflate
import com.example.eh_ho.topics.LoadingDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_post.*
import kotlinx.android.synthetic.main.fragment_create_topic.*

class CreatePostFragment(private val topic: Topic): Fragment() {

    var createPostInteractionListener: CreatePostInteractionListener? = null
    var loadingDialogFragment = LoadingDialogFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CreatePostInteractionListener)
            createPostInteractionListener = context
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
        return container?.inflate(R.layout.fragment_create_post)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_send_new_post)
            startPostProcess()
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            topicTitle.text = topic.title
            postAuthor.text = UserRepo.getUsername(it.applicationContext)
        }
    }

    private fun startPostProcess() {
        if (isFormValid()){
            postPost()
        } else {
            showErrors()
        }
    }

    private fun showErrors() {
        inputPostContent.error = getString(R.string.error_empty)
    }

    private fun postPost() {
        val createPostModel = CreatePostModel(
            topic.id,
            inputPostContent.text.toString()
        )
        context?.let {context ->
            loadingDialogFragment.show(childFragmentManager, null)
            PostsRepo.createPost(
                context.applicationContext,
                createPostModel,
                {
                    loadingDialogFragment.dismiss()
                    this.createPostInteractionListener?.onPostCreated()
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

    private fun isFormValid() = inputPostContent.text.isNotEmpty()

    override fun onDetach() {
        super.onDetach()
        createPostInteractionListener = null
    }

    interface CreatePostInteractionListener {
        fun onPostCreated()
    }
}