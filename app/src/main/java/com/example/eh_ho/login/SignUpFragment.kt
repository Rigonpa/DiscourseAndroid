package com.example.eh_ho.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eh_ho.R
import com.example.eh_ho.data.SignUpModel
import com.example.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {
    var signUpInteractionListener: SignUpInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpInteractionListener)
            signUpInteractionListener = context
        else
            throw IllegalArgumentException()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_sign_up)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignUp.setOnClickListener {
            val  signUpModel = SignUpModel(
                inputUser.text.toString(),
                inputEmail.text.toString(),
                inputPassword.text.toString()
            )
            signUpInteractionListener?.onSignUp(signUpModel)
        }

        labelLogIn.setOnClickListener {
            signUpInteractionListener?.onGoToSignIn()
        }
    }

    interface SignUpInteractionListener {
        fun onGoToSignIn()
        fun onSignUp(signUpModel: SignUpModel)
    }
}