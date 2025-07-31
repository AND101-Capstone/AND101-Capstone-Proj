package com.example.capstone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.example.capstone.BuildConfig
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import org.json.JSONArray
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private val TAG = "RapidAPI-Test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // put your RAPIDAPI_KEY=... in local.properties
        val apiKey = BuildConfig.RAPIDAPI_KEY

        fetchTop250Movies(apiKey)
    }
    private fun fetchTop250Movies(apiKey : String) {
        val client = AsyncHttpClient()
        val url = "https://imdb236.p.rapidapi.com/api/imdb/top250-movies"

        val headers = RequestHeaders()
        headers["x-rapidapi-host"] = "imdb236.p.rapidapi.com"
        headers["x-rapidapi-key"] = apiKey

        // optional query params, depends on what endpoint we are going to use
        val params = RequestParams()

        client.get(url, headers, params, object : TextHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, response: String) {
                    val jsonArray = JSONArray(response)
                    val movie116 = jsonArray.getJSONObject(115)

                    val title = movie116.getString("primaryTitle")
                    val year = movie116.optInt("startYear", -1)
                    val rating = movie116.optDouble("averageRating", -1.0)
                    val trailer = movie116.optString("trailer", "N/A")
                    val description = movie116.optString("description", "No description")

                    Log.d(TAG, "🎬 Movie #116: $title ($year)")
                    Log.d(TAG, "⭐ Rating: $rating")
                    Log.d(TAG, "🎞️ Trailer: $trailer")
                    Log.d(TAG, "📝 Description: $description")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.e(TAG, "API failed: $statusCode - $response")
            }
        })
    }

//  Data class for parsing for now, will move to an adapter class

    data class Movie(
        val id: String,
        val primaryTitle: String,
        val originalTitle: String?,
        val description: String?,
        val primaryImage: String?,    //link to image
        val trailer: String?,
        val contentRating: String?,
        val startYear: Int?,
        val runtimeMinutes: Int?,
        val averageRating: Double?,
        val numVotes: Int?,
        val genres: List<String>?
    )
}
