package com.example.eh_ho.data

import android.net.Uri
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

object ApiRoutes {

    fun signIn(username: String) =
        uriBuilder()
            .appendPath("users")
            .appendPath("${username}.json")
            .build()
            .toString()

    private fun uriBuilder() =
        Uri.Builder()
            .scheme("https")
            .authority("mdiscourse.keepcoding.io")
}