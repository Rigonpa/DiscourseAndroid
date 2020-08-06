package com.example.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eh_ho.R
import com.example.eh_ho.data.CreateTopicModel
import com.example.eh_ho.data.RequestError
import com.example.eh_ho.data.TopicsRepo
import com.example.eh_ho.inflate
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_topic.*

const val TAG_LOADING_DIALOG = "loading_dialog"

class CreateTopicFragment : Fragment() {

    var createTopicInteractionListener: CreateTopicInteractionListener? = null
    private val loadingDialogFragment : LoadingDialogFragment by lazy {
        val message = getString(R.string.label_creating_topic)
        LoadingDialogFragment.newInstance(message)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CreateTopicInteractionListener)
            this.createTopicInteractionListener = context
        else
            throw IllegalArgumentException("Context does not implement ${CreateTopicInteractionListener::class.java.canonicalName}")
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
        return container?.inflate(R.layout.fragment_create_topic)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_topic, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> createTopic()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createTopic() {
        if (isFormValid()) {
            postTopic()
        } else {
            showErrors()
        }
    }

    private fun postTopic() {
        enableLoadingDialog()
        val model = CreateTopicModel(
            inputTitle.text.toString(),
            inputContent.text.toString()
        )
        context?.let {context ->
            TopicsRepo.addTopic(
                context.applicationContext,
                model,
                {
                    enableLoadingDialog(false)
                    createTopicInteractionListener?.onTopicCreated()
                },
                {
                    enableLoadingDialog(false)
                    handleError(it)
                }
            )
        }
    }

    private fun enableLoadingDialog(enabled: Boolean = true) {
        if (enabled) {
            loadingDialogFragment.show(childFragmentManager, TAG_LOADING_DIALOG)
        } else
            loadingDialogFragment.dismiss()
    }

    private fun handleError(error: RequestError) {
        val message = if (error.messageResId != null)
            getString(error.messageResId)
        else error.message ?: getString(R.string.error_default)

        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showErrors() {
        if (inputTitle.text.isEmpty())
            inputTitle.error = getString(R.string.error_empty)
        if (inputContent.text.isEmpty())
            inputContent.error = getString(R.string.error_empty)
    }

    private fun isFormValid() = inputTitle.text.isNotEmpty() && inputContent.text.isNotEmpty()

    interface CreateTopicInteractionListener {
        fun onTopicCreated()
    }
}