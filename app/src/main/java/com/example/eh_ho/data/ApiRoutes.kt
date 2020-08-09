package com.example.eh_ho.data

import android.net.Uri

object ApiRoutes {

    fun signIn(username: String) =
        uriBuilder()
            .appendPath("users")
            .appendPath("${username}.json")
            .build()
            .toString()

    fun signUp() =
        uriBuilder()
            .appendPath("users")
            .build()
            .toString()

    fun getTopics() =
        uriBuilder()
            .appendPath("latest.json")
            .build()
            .toString()

    fun createTopic() =
        uriBuilder()
            .appendPath("posts.json")
            .build()
            .toString()

    fun getPosts(topicId: String) = "https://mdiscourse.keepcoding.io/t/${topicId}/posts.json"
//        uriBuilder()
//            .path("t")
//            .path(topicId)
//            .path("posts.json")
//            .build()
//            .toString()


    private fun uriBuilder() =
        Uri.Builder()
            .scheme("https")
            .authority("mdiscourse.keepcoding.io")
}