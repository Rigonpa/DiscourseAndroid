package com.example.eh_ho.login

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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
            if (isFormValid()) {
                val signUpModel = SignUpModel(
                    inputUser.text.toString(),
                    inputEmail.text.toString(),
                    inputPassword.text.toString()
                )
                signUpInteractionListener?.onSignUp(signUpModel)
            } else {
                showErrors()
            }
        }

        labelLogIn.setOnClickListener {
            signUpInteractionListener?.onGoToSignIn()
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    private fun showErrors() {
        if (inputEmail.text.isEmpty())
            inputEmail.error = getString(R.string.error_empty)
        if (inputUser.text.isEmpty())
            inputUser.error = getString(R.string.error_empty)
        if (inputPassword.text.isEmpty())
            inputPassword.error = getString(R.string.error_empty)
        if (inputConfirmPassword.text.isEmpty())
            inputConfirmPassword.error = getString(R.string.error_empty)
        if (inputPassword.text.toString() != inputConfirmPassword.text.toString())
            inputConfirmPassword.error = getString(R.string.error_password_different)
        if (!isValidEmail(inputEmail.text.toString()))
            inputEmail.error = getString(R.string.error_no_valid_email)
    }

    private fun isFormValid() =
        inputEmail.text.isNotEmpty()
                && inputUser.text.isNotEmpty()
                && inputPassword.text.isNotEmpty()
                && inputConfirmPassword.text.isNotEmpty()
                && inputPassword.text.toString() == inputConfirmPassword.text.toString()
                && isValidEmail(inputEmail.text.toString())

    override fun onDetach() {
        super.onDetach()
        signUpInteractionListener = null
    }

    interface SignUpInteractionListener {
        fun onGoToSignIn()
        fun onSignUp(signUpModel: SignUpModel)
    }
}