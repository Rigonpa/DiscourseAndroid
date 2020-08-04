package com.example.eh_ho.data

import org.json.JSONObject

data class SignInModel(val username: String, val password: String)

data class SignUpModel(val username: String, val email: String, val password: String) {
    fun toJson() =
        JSONObject()
            .put("name", username) // {"name" : "username"}
            .put("username", username)
            .put("email", email)
            .put("password", password)
            .put("active", true)
            .put("approved", true)

}