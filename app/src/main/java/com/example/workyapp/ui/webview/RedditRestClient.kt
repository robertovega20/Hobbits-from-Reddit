package com.example.workyapp.ui.webview

import android.content.Context
import android.util.Log
import org.json.JSONException

/**
 * Created by Pratik Agrawal on 11/20/2015.
 */
class RedditRestClient internal constructor(var context: Context, var code: String?) {

    companion object {
        private const val CLIENT_ID = "YOUR CLIENT_ID"
        private const val CLIENT_SECRET = ""
        private const val BASE_URL = "https://www.reddit.com/api/v1/"
        private const val REDIRECT_URI = "http://localhost"
    }

    var token: String? = null

    @Throws(JSONException::class)
    fun getToken(relativeUrl: String, grant_type: String?, device_id: String?) {
       /* client.setBasicAuth(CLIENT_ID, CLIENT_SECRET)
        val requestParams = RequestParams()
        requestParams.put("code", code)
        requestParams.put("grant_type", grant_type)
        requestParams.put("redirect_uri", REDIRECT_URI)
        post(relativeUrl, requestParams, object : JsonHttpResponseHandler() {

            fun onSuccess(statusCode: Int, headers: Array<Header?>?, response: JSONObject) {
                try {
                    token = response.getString("access_token").toString()
                    val edit = pref.edit()
                    edit.putString("token", token)
                    edit.commit()
                    Log.i("Access_token", pref.getString("token", "")!!)
                } catch (j: JSONException) {
                    j.printStackTrace()
                }
            }

            fun onFailure(
                statusCode: Int,
                headers: Array<Header?>?,
                throwable: Throwable?,
                errorResponse: JSONObject?
            ) {
                Log.i("statusCode", "" + statusCode)
            }
        })
*/
    }
}