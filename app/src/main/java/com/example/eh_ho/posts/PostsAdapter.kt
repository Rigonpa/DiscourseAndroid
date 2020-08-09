package com.example.eh_ho.posts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eh_ho.R
import com.example.eh_ho.data.Post
import com.example.eh_ho.data.PostsRepo
import com.example.eh_ho.inflate
import kotlinx.android.synthetic.main.item_post.view.*
import java.util.*

class PostsAdapter(private val clickListener: ((Post) -> Unit)? = null) :
    RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    private var posts = mutableListOf<Post>()

    fun setPosts(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder =
        PostHolder(parent.inflate(R.layout.item_post))

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.post = posts[position]
        holder.itemView.setOnClickListener {
            clickListener?.invoke(it.tag as Post)
        }
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: Post? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let {
                    itemView.postAuthor.text = it.username
                    itemView.postContent.text = it.content
                    setTimeOffset(it.getTimeOffset())
                }
            }

        private fun setTimeOffset(timeOffset: Post.TimeOffset) {
            val quantityString = when (timeOffset.unit) {
                Calendar.YEAR -> R.plurals.years
                Calendar.MONTH -> R.plurals.months
                Calendar.DAY_OF_MONTH -> R.plurals.days
                Calendar.HOUR -> R.plurals.hours
                else -> R.plurals.minutes
            }

            if (timeOffset.amount == 0) {
                itemView.postDate.text =
                    itemView.context.resources.getString(R.string.minutes_zero)
            } else {
                itemView.postDate.text = itemView.context.resources.getQuantityString(
                    quantityString,
                    timeOffset.amount,
                    timeOffset.amount
                )
            }
        }
    }
}














