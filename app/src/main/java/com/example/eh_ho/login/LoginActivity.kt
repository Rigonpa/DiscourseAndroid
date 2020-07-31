package com.example.eh_ho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.eh_ho.data.RequestError
import com.example.eh_ho.data.SignInModel
import com.example.eh_ho.data.UserRepo
import com.example.eh_ho.login.SignInFragment
import com.example.eh_ho.login.SignUpFragment
import com.example.eh_ho.topics.TopicsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

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
//        simulateLoading(signInModel)
        UserRepo.signIn(this.applicationContext, signInModel, {
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

    override fun onSignUp() {
        enableLoading()
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

//    private fun simulateLoading(signInModel: SignInModel) {
//        val runnable = Runnable {
//            Thread.sleep(3000)
//            viewLoading.post {
//                UserRepo.signIn(this.applicationContext, signInModel.username)
//                showTopics()
//            }
//        }
//        Thread(runnable).start()

//        val task = object: AsyncTask<Long, Void, Boolean>() {
//            override fun doInBackground(vararg time: Long?): Boolean {
//                Thread.sleep(time[0] ?: 3000)
//                return true
//            }
//
//            override fun onPostExecute(result: Boolean?) {
//                super.onPostExecute(result)
//                showTopics()
//            }
//        }
//
//        task.execute(5000)
//    }

}

//
//class Listener : View.OnClickListener {
//    override fun onClick(view: View?) {
//        Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
//    }
//
//}


//      Formas de implantar un setOnClickListener, de la más extensa a la más comprimida:
//        val button: Button = findViewById(R.id.button_login)
//        val inputUsername: EditText = findViewById(R.id.inputUser)

//      3B. Uso de lambda - Modo corto
//        button.setOnClickListener {
//            Toast.makeText(it?.context, "Welcome to LAMMBDAA SHORT ${inputUsername.text}", Toast.LENGTH_SHORT).show()
//        }

//      3A. Uso de función anónima - lambda - Explícito
//        val listenerLambda: (View) -> Unit = {
//            Toast.makeText(it?.context, "Welcome to LAMMBDAA", Toast.LENGTH_SHORT).show()
//        }
//        button.setOnClickListener(listenerLambda)


//      2. Uso de clase anónima
//        val listener : View.OnClickListener = object : View.OnClickListener {
//            override fun onClick(view: View?) {
//                Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
//            }
//        }
//        button.setOnClickListener(listener)

//      1B. Uso de Interfaz - Modo dios
//        button.setOnClickListener(View.OnClickListener {
//            Toast.makeText(it.context, "Welcomecome", Toast.LENGTH_SHORT).show()
//        })
//      1A. Uso de interfaz - Modo tradicional, explícito
//        button.setOnClickListener(Listener())
//    }

