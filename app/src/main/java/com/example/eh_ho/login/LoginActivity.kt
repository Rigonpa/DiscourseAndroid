package com.example.eh_ho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.eh_ho.data.RequestError
import com.example.eh_ho.data.SignInModel
import com.example.eh_ho.data.SignUpModel
import com.example.eh_ho.data.UserRepo
import com.example.eh_ho.login.SignInFragment
import com.example.eh_ho.login.SignUpFragment
import com.example.eh_ho.topics.TopicsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.container
import kotlinx.android.synthetic.main.fragment_create_topic.*

class LoginActivity : AppCompatActivity(), SignInFragment.SignInInteractionListener,
    SignUpFragment.SignUpInteractionListener {

    val signInFragment = SignInFragment()
    val signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isFirstTimeCreated(savedInstanceState)) {
            checkSession()
        }
    }

    private fun checkSession() {
        if (UserRepo.isLogged(this.applicationContext)) {
            showTopics()
        } else {
            onGoToSignIn()
        }
    }

    private fun showTopics() {
        val intent: Intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onGoToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signUpFragment)
            .commit()
    }

    override fun onSignIn(signInModel: SignInModel) {
        // Authentication
        enableLoading()
        UserRepo.signIn(
            this.applicationContext,
            signInModel,
            {
                showTopics()
            }, {
                enableLoading(false)
                handleError(it)
            })

    }

    private fun handleError(error: RequestError) {
        if (error.messageResId != null)
            Snackbar.make(container, error.messageResId, Snackbar.LENGTH_LONG).show()
        else if (error.message != null)
            Snackbar.make(container, error.message, Snackbar.LENGTH_LONG).show()
        else
            Snackbar.make(container, R.string.error_default, Snackbar.LENGTH_LONG).show()
    }

    override fun onGoToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signInFragment)
            .commit()
    }

    override fun onSignUp(signUpModel: SignUpModel) {
        enableLoading()
        UserRepo.signUp(this.applicationContext, signUpModel, {
            enableLoading(false)
            Snackbar.make(container, R.string.message_sign_up, Snackbar.LENGTH_LONG).show()
        }, {
            enableLoading(false)
            handleError(it)
        })
//        simulateLoading()
    }

    private fun enableLoading(enabled: Boolean = true) {
        if (enabled) {
            fragmentContainer.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentContainer.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }
}