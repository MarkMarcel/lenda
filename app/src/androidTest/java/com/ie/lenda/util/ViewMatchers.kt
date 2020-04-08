package com.ie.lenda.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher

fun childAtPositionWithMatchers(position:Int, vararg childMatcher:Matcher<View>): Matcher<View> {
    return object:BoundedMatcher<View,RecyclerView>(RecyclerView::class.java){
        override fun describeTo(description: Description?) {
            description?.appendText("descendant view for position $position specified by childMatcher")
        }

        override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
            val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position)
            val matcher = hasDescendant(allOf(*childMatcher))
            return viewHolder != null && matcher.matches(viewHolder.itemView)
        }
    }
}