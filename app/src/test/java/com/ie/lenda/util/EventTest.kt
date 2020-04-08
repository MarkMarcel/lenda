package com.ie.lenda.util

import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EventTest{

    @Test
    fun getContent_initialCall_returnsGivenValue(){
        //Given new event is created
        val content = Any()
        val event = Event(content)

        //When it content is requested for the first time
        val requestedContent = event.getContent()

        //Assert that requested content is content passed
        assertEquals(content,requestedContent)

    }

    @Test
    fun getContent_subsequentCall_returnsNull(){
        //Given new event is created
        val content = Any()
        val event = Event(content)

        //When it content is requested for the first time
        event.getContent()

        //Assert that subsequent call to getContent returns null
        assertThat(event.getContent(),(nullValue()))
    }
}