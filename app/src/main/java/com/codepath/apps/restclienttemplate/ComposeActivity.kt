package com.codepath.apps.restclienttemplate

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button
    lateinit var etCountDown: EditText
    lateinit var client: TwitterClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)
        etCountDown = findViewById(R.id.etTweetCount)

        client = TwitterApplication.getRestClient(this)

        btnTweet.setOnClickListener{

            //Grab content of edittext (etCompose)
            val tweetContent = etCompose.text.toString()

            //1. Make sure tweet isn't empty
            if(tweetContent.isEmpty()){
                Toast.makeText(this,"Empty tweets not allowed",Toast.LENGTH_SHORT).show()
            } else
            //2. Make sure tweet is under character count
            if(tweetContent.length>280){
                Toast.makeText(this,"Tweet limit is 280 characters",Toast.LENGTH_SHORT).show()

            } else {
                client.publishTweet(tweetContent,object:JsonHttpResponseHandler(){
                    override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                        Log.i(TAG,"Successfully published tweet!")

                        val tweet = Tweet.fromJson(json.jsonObject)

                        val intent = Intent()
                        intent.putExtra("tweet",tweet)
                        setResult(RESULT_OK,intent)
                        finish()

                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG,"Failed to publish tweet",throwable)
                    }

                })
            }

        }

        etCompose.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val char = etCompose.length()
                val displaychar = 280 - char
                etCountDown.setText(displaychar.toString())
                if (displaychar<0){
                   etCountDown.setTextColor(Color.parseColor("#FF0000"))
                    btnTweet.isEnabled = false
                    btnTweet.setTextColor(Color.parseColor("#FFFFFF"))
                    btnTweet.setBackgroundColor(Color.parseColor("#F1F1F1"))
                } else {
                    etCountDown.setTextColor(Color.parseColor("#000000"))
                    btnTweet.isEnabled = true
                    btnTweet.setTextColor(Color.parseColor("#000000"))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    companion object{
        val TAG = "ComposeActivity"
    }
}