package com.kouki.friends.timeline

import com.kouki.friends.InstantTaskExecutorExtension
import com.kouki.friends.domain.post.Post
import com.kouki.friends.domain.user.User
import com.kouki.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class LoadPostTest {
    @Test
    fun noPostsAvailable() {
        // Arrange
        val viewModel = TimelineViewModel()
        // Act
        viewModel.timelineFor("annaId")
        // Assert
        assertEquals(TimelineState.Posts(emptyList<Post>()), viewModel.timelineState.value)
    }

}