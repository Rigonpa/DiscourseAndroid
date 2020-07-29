package com.example.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.eh_ho.R
import com.example.eh_ho.TopicsRepo
import com.example.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_create_topic.*

class CreateTopicFragment : Fragment() {

    var createTopicInteractionListener: CreateTopicInteractionListener? = null

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
            // Creation Topic
            TopicsRepo.addTopic(
                inputTitle.text.toString(),
                inputContent.text.toString()
            )
            createTopicInteractionListener?.onTopicCreated()
        } else {
            // Errors
            showErrors()
        }
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