package com.codepath.apps.restclienttemplate

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet


class TweetsAdapter(val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        //Inflate item layout
        val view = inflater.inflate(R.layout.item_tweet,parent,false)
        return ViewHolder(view)
    }

    //Populating data into the item through holder
    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        // Get data model based on position
        val tweet: Tweet = tweets.get(position)

        // Set item views based on views and data model
        holder.tvUserName.text = tweet.user?.name
        holder.tvAtName.text = tweet.user?.screenName
        holder.tvTweetBody.text = tweet.body
        holder.tvTime.text = tweet.createdAt

        if (tweet.favs == "0"){
        holder.tvFavs.text = "" } else {
            holder.tvFavs.text = tweet.favs
        }
        if (tweet.retweets == "0") {
        holder.tvRetweets.text = "" } else {
            holder.tvRetweets.text = tweet.retweets
        }

        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    // Clean all elements of the recycler
    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvAtName = itemView.findViewById<TextView>(R.id.tvAtName)
        val tvFavs = itemView.findViewById<TextView>(R.id.tvFavs)
        val tvRetweets = itemView.findViewById<TextView>(R.id.tvRetweets)
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
    }
}