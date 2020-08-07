package com.example.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eh_ho.R
import com.example.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_topics_error.*

class ErrorFragment: Fragment() {

    var errorTopicsInteractionListener: ErrorTopicsInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ErrorTopicsInteractionListener)
            errorTopicsInteractionListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_topics_error)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonBad.setOnClickListener {
            this.errorTopicsInteractionListener?.retry()
        }
    }
}

interface ErrorTopicsInteractionListener {
    fun retry()
}