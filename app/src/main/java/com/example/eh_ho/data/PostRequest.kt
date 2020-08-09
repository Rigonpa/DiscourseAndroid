package com.example.eh_ho.data

import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class PostRequest(
    method: Int,
    url: String,
    body: JSONObject?,
    listener: (response: JSONObject) -> Unit,
    errorListener: (errorResponse: VolleyError) -> Unit,
    private val username: String? = null,
    private val useApiKey: Boolean = true
) :
    JsonObjectRequest(method, url, body, listener, errorListener) {
    override fun getHeaders(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"

        if (useApiKey)
            headers["Api-Key"] =
                "699667f923e65fac39b632b0d9b2db0d9ee40f9da15480ad5a4bcb3c1b095b7a"

        if (username != null)
            headers["Api-Username"] = username
        return headers
    }
}