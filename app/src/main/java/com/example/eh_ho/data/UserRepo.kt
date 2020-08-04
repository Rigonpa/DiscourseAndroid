package com.example.eh_ho.data

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.eh_ho.BuildConfig
import com.example.eh_ho.R

const val PREFERENCES_SESSION = "session"
const val PREFERENCES_USERNAME = "username"

object UserRepo {
    fun signIn(
        context: Context,
        signInModel: SignInModel,
        success: (SignInModel) -> Unit,
        error: (RequestError) -> Unit
    ) {

        // 1. Crear el request
        val request = JsonObjectRequest(
            Request.Method.GET,
//            "https://mdiscourse.keepcoding.io/users/${signInModel.username}.json",
            ApiRoutes.signIn(signInModel.username),
            null,
            {
                // 5. Notificar que la petición fue exitosa
                success(signInModel)
                // 6. Guardar la sesión
                saveSession(
                    context,
                    signInModel.username
                )
            },
            { err ->
                // 5.1 Procesar error
                err.printStackTrace()

                val errorObject = if (err is ServerError && err.networkResponse.statusCode == 404) {
                    RequestError(err, messageResId = R.string.error_not_registered)
                } else if (err is NetworkError) {
                    RequestError(err, messageResId = R.string.error_not_internet)
                } else {
                    RequestError(err)
                }
                error(errorObject)

            }
        )

        // 2. Encolar la petición
//        val requestQueue = Volley.newRequestQueue(context)
        ApiRequestQueue.getRequestQueue(context).add(request)

        // 3. Otorgar permisos de accesos a internet

    }

    fun signUp(
        context: Context,
        signUpModel: SignUpModel,
        onSuccess: ((SignUpModel) -> Unit),
        onError: ((RequestError) -> Unit)
    ) {
        val request = PostRequest(
            Request.Method.POST,
            ApiRoutes.signUp(),
            signUpModel.toJson(),
            { response ->
                val successStatus = response?.getBoolean("success") ?: false
                if (successStatus)
                    onSuccess(signUpModel)
                else
                    onError(RequestError(message = response?.getString("message")))

            },
            { err ->
                err.printStackTrace()
                val requestError = if (err is NetworkError)
                    RequestError(err, messageResId = R.string.error_not_internet)
                else
                    RequestError(err)
                onError(requestError)
            }
        )

        ApiRequestQueue.getRequestQueue(context).add(request)
    }

    private fun saveSession(context: Context, username: String) {
        val preferences = context.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)
        preferences
            .edit()
            .putString(PREFERENCES_USERNAME, username)
            .apply()
    }

    fun logout(context: Context) {
        val preferences = context.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)
        preferences
            .edit()
            .putString(PREFERENCES_USERNAME, null)
            .apply()
    }

    fun isLogged(context: Context): Boolean {
        val preferences = context.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)
        val username = preferences.getString(PREFERENCES_USERNAME, null)
        return username != null
    }
}