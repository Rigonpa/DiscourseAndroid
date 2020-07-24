package com.example.eh_ho

object TopicsRepo {
    val topics: MutableList<Topic> = mutableListOf()
    get() {
        if(field.isEmpty())
            field.addAll(createDummyTopics())
        return field
    }

    //    fun createDummyTopics(count: Int = 10): List<Topic> {
    fun createDummyTopics(count: Int = 10) =
        (0..count).map {
            Topic(
                title = "Title $it",
                content = "Content $it"
            )
        }
    /*
    val dummies = mutableListOf<Topic>()

    for (i in 0..count) {
        val topic =  Topic(
            title = "Topic $i",
            content = "Content $i"
        )
        dummies.add(topic)
    }
    return dummies
     */

//        val lambda: (Int) -> Topic = {
//            Topic(
//                title = "Title $it",
//                content = "Content $it"
//            )
//        }
//        return (0..count).map(lambda)

//        return (0..count).map {
//            Topic(
//                title = "Title $it",
//                content = "Content $it"
//            )
//        }

//    }
}