package com.example.eh_ho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.eh_ho.login.SignInFragment
import com.example.eh_ho.login.SignUpFragment
import com.example.eh_ho.topics.TopicsActivity

class LoginActivity : AppCompatActivity(), SignInFragment.SignInInteractionListener, SignUpFragment.SignUpInteractionListener {

    val signInFragment = SignInFragment()
    val signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isFirstTimeCreated(savedInstanceState)) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, signInFragment)
                .commit()
        }
    }

    private fun showTopics() {
        val intent: Intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
    }

    override fun onGoToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signUpFragment)
            .commit()
    }

    override fun onSignIn() {
        // Authentication
        showTopics()
    }

    override fun onGoToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signInFragment)
            .commit()
    }

    override fun onSignUp() {
        // User creation
        showTopics()
    }
}

class Listener : View.OnClickListener {
    override fun onClick(view: View?) {
        Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
    }

}





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

