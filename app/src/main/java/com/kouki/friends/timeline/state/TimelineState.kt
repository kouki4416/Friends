package com.kouki.friends.timeline.state

import com.kouki.friends.domain.post.Post

sealed class TimelineState {
    data class Posts(val posts: List<Post>) : TimelineState()

}
