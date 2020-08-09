package com.example.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
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
}