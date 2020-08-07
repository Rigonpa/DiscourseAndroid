package com.example.eh_ho.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eh_ho.R
import com.example.eh_ho.data.SignInModel
import com.example.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_create_topic.*
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    var signInInteractionListener: SignInInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //loginActivity = context as LoginActivity
        if (context is SignInInteractionListener)
            signInInteractionListener = context
        else
            throw IllegalArgumentException("Context does not implement ${SignInInteractionListener::class.java.canonicalName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_sign_in, container, false)
        return container?.inflate(R.layout.fragment_sign_in)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labelCreateAccount.setOnClickListener {
            signInInteractionListener?.onGoToSignUp()
        }
        buttonLogin.setOnClickListener {
            if (isFormValid()) {
                val signInModel = SignInModel(
                    inputUser.text.toString(),
                    inputPassword.text.toString()
                )
                signInInteractionListener?.onSignIn(signInModel)
            } else {
                showErrors()
            }
        }
    }

    private fun showErrors() {
        if (inputUser.text.isEmpty())
            inputUser.error = getString(R.string.error_empty)
        if (inputPassword.text.isEmpty())
            inputPassword.error = getString(R.string.error_empty)
    }

    private fun isFormValid() = inputUser.text.isNotEmpty() && inputPassword.text.isNotEmpty()

    override fun onDetach() {
        super.onDetach()
        signInInteractionListener = null
    }

    interface SignInInteractionListener {
        fun onGoToSignUp()
        fun onSignIn(signInModel: SignInModel)
    }
}