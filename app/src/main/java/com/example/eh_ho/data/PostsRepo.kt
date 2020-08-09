package com.example.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.eh_ho.R
import org.json.JSONObject

object PostsRepo {

    fun getPosts(
        topicId: String,
        context: Context,
        onSuccess: (List<Post>) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRoutes.getPosts(topicId),
            null,
            {
                onSuccess(Post.parsePostsList(it))
            },
            {
                it.printStackTrace()
                val requestError = if (it is NetworkError)
                    RequestError(it, messageResId = R.string.error_not_internet)
                else
                    RequestError(it)
                onError(requestError)
            }
        )
        ApiRequestQueue.getRequestQueue(context).add(request)
    }

    fun createPost(
        context: Context,
        createPostModel: CreatePostModel,
        onSuccess: (CreatePostModel) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val request = PostRequest(
            Request.Method.POST,
            ApiRoutes.createPost(),
            createPostModel.toJson(),
            {
                onSuccess(createPostModel)
            },
            {
                it.printStackTrace()
                onError(obtainAllErrorMessages(it))
            },
            UserRepo.getUsername(context)
        )
        ApiRequestQueue.getRequestQueue(context).add(request)
    }

    private fun obtainAllErrorMessages(error: VolleyError): RequestError {

        val body = String(error.networkResponse.data, Charsets.UTF_8)
        val jsonError = JSONObject(body)
        val errors = jsonError.getJSONArray("errors")
        var messages = ""
        for (i in 0 until errors.length()) {
            messages += "${errors[i]} "
        }
        return RequestError(error, message = messages)
    }


}









