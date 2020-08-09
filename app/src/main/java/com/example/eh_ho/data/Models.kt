package com.example.eh_ho.data

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

data class Topic(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val date: Date = Date(),
    val posts: Int = 0,
    val views: Int = 0
) {

    // Añadiendo contexto estático dentro de cualquier clase
    companion object {
        // No necesitamos crear un objeto Dream para invocar los siguiente métodos:
        fun parseTopicList(response: JSONObject): List<Topic> {
            val objectLst = response.getJSONObject("topic_list")
                .getJSONArray("topics")

            val topics = mutableListOf<Topic>()

            for (i in 0 until objectLst.length()) {
                val parsedTopic = parseTopic(objectLst.getJSONObject(i))
                topics.add(parsedTopic)
            }
            return topics
        }


        fun parseTopic(jsonObject: JSONObject): Topic {
            val date = jsonObject.getString("created_at")
                .replace("Z", "+0000")

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            val dateFormatted = dateFormat.parse(date) ?: Date()

            return Topic(
                id = jsonObject.getInt("id").toString(),
                title = jsonObject.getString("title").toString(),
                date = dateFormatted,
                posts = jsonObject.getInt("posts_count"),
                views = jsonObject.getInt("views")
            )
        }
    }


    val MINUTE_MILLIS = 1000L * 60
    val HOUR_MILLIS = MINUTE_MILLIS * 60
    val DAY_MILLIS = HOUR_MILLIS * 24
    val MONTH_MILLIS = DAY_MILLIS * 30
    val YEAR_MILLIS = MONTH_MILLIS * 12

    data class TimeOffset(val amount: Int, val unit: Int)

    /**
     * Fecha de creación de tópico '01/01/2020 10:00:00'
     * @param Date Fecha de consulta '01/01/2020 10:10:00'
     * @return { unit: "Minutos", amount: 10}
     **/
    fun getTimeOffset(dateToCompare: Date = Date()): TimeOffset {
        val current = dateToCompare.time
        val diff = current - this.date.time

        val years = diff / YEAR_MILLIS
        if (years > 0) return TimeOffset(
            years.toInt(),
            Calendar.YEAR
        )

        val months = diff / MONTH_MILLIS
        if (months > 0) return TimeOffset(
            months.toInt(),
            Calendar.MONTH
        )

        val days = diff / DAY_MILLIS
        if (days > 0) return TimeOffset(
            days.toInt(),
            Calendar.DAY_OF_MONTH
        )

        val hours = diff / HOUR_MILLIS
        if (hours > 0) return TimeOffset(
            hours.toInt(),
            Calendar.HOUR
        )

        val minutes = diff / MINUTE_MILLIS
        if (minutes > 0) return TimeOffset(
            minutes.toInt(),
            Calendar.MINUTE
        )

        return TimeOffset(0, Calendar.MINUTE)
    }
}

data class Post(
    val id: Int,
    val username: String,
    val creationDate: Date = Date(),
    val content: String
) {
    companion object {
        fun parsePostsList(response: JSONObject): List<Post> {
            val posts = response
                .getJSONObject("post_stream")
                .getJSONArray("posts")
            val postsList = mutableListOf<Post>()
            for (i in 0 until posts.length()) {
                postsList.add(parsePost(posts[i] as JSONObject))
            }
            return postsList
        }

        private fun parsePost(postObject: JSONObject): Post {

            val date = postObject.getString("created_at")
                .replace("Z", "+0000")
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            val formattedDate = dateFormatter.parse(date)
            val postContent = postObject.getString("cooked")
                .removePrefix("<p>")
                .removeSuffix("</p>")

            return Post(
                postObject.getInt("id"),
                postObject.getString("username"),
                formattedDate,
                postContent
            )
        }
    }

    data class TimeOffset(val amount: Int, val unit: Int)

    fun getTimeOffset(dateToCompare: Date = Date()): TimeOffset {
        val currentDate = dateToCompare.time
        val timeDiff = currentDate - this.creationDate.time // In miliseconds

        val yearAmount = timeDiff / (12 * 30 * 24 * 60 * 60 * 1000L)
        if (yearAmount > 1)
            return TimeOffset(yearAmount.toInt(), Calendar.YEAR)


        val monthAmount = timeDiff / (30 * 24 * 60 * 60 * 1000L)
        if (monthAmount > 1)
            return TimeOffset(monthAmount.toInt(), Calendar.MONTH)

        val dayAmount = timeDiff / (24 * 60 * 60 * 1000L)
        if (dayAmount > 1)
            return TimeOffset(dayAmount.toInt(), Calendar.DAY_OF_MONTH)

        val hourAmount = timeDiff / (60 * 60 * 1000L)
        if (hourAmount > 1)
            return TimeOffset(hourAmount.toInt(), Calendar.HOUR)

        val minuteAmount = timeDiff / (60 * 1000L)
        if (minuteAmount > 1)
            return TimeOffset(minuteAmount.toInt(), Calendar.MINUTE)

        return TimeOffset(0, Calendar.MINUTE)
    }

}































