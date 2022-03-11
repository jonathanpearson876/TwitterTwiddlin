package com.codepath.apps.restclienttemplate.models

import android.util.Log
import com.codepath.apps.restclienttemplate.TimeFormatter
import org.json.JSONArray
import org.json.JSONObject

class Tweet {
    var body: String =""
    var createdAt: String =""
    var favs: String =""
    var retweets: String =""
    var user: User? = null
    var time: String = ""

    companion object{
        fun fromJson(jsonObject: JSONObject) : Tweet {
            val tweet = Tweet()
            tweet.body = jsonObject.getString("text")
            tweet.createdAt = jsonObject.getString("created_at")
            tweet.createdAt = getFormattedTimestamp(tweet)
            tweet.favs = jsonObject.getString("favorite_count")
            tweet.retweets = jsonObject.getString("retweet_count")
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"))
            return tweet
        }

        fun getFormattedTimestamp(tweet:Tweet) : String {
            val times = TimeFormatter.getTimeDifference(tweet.createdAt)
            return times
        }

        fun fromJsonArray(jsonArray: JSONArray): List<Tweet> {
            val tweets = ArrayList<Tweet>()
            for (i in 0 until jsonArray.length()) {
                tweets.add(fromJson(jsonArray.getJSONObject(i)))
            }

            return tweets
        }
    }


}