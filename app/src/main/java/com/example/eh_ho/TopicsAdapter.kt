package com.example.eh_ho

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicsAdapter(val topicClickListener: ((Topic) -> Unit)? = null) : RecyclerView.Adapter<TopicsAdapter.TopicHolder>() {

    private val topics = mutableListOf<Topic>()
    private val listener: ((View) -> Unit) = {
        val tag: Topic = it.tag as Topic
        topicClickListener?.invoke(tag)
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): TopicHolder {
        val view = LayoutInflater.from(list.context).inflate(R.layout.item_topic, list, false)

        return TopicHolder(view)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topics[position]
        holder.topic = topic
//        holder.itemView.findViewById<TextView>(R.id.label_topic).setText(topic.title)
        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    inner class TopicHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var topic: Topic? = null
        set(value) {
            field = value
            itemView.tag = field
            itemView.labelTopic.text = field?.title // Viva las extensions
//            itemView.findViewById<TextView>(R.id.label_topic).text = field?.title
        }
    }
}