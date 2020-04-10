package com.ie.lenda.datastructure.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ie.lenda.R
import com.ie.lenda.util.childAtPositionWithMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class DataStructureListFragmentTest{
    private lateinit var dataStructureList:Set<HashMap<String,Any>>
    private val context = getApplicationContext<Context>()

    @Before
    fun setupData(){
        dataStructureList = setOf(
            hashMapOf<String,Any>(
                context.getString(R.string.ds_name_map_key) to "Array"
            ),
            hashMapOf(
                context.getString(R.string.ds_name_map_key) to "Trees",
                context.getString(R.string.ds_children_map_key) to setOf(
                    hashMapOf(
                        context.getString(R.string.ds_name_map_key) to "Binary Tree"
                    )
                )
            )
        )
    }
    @Test
    fun confirmViewsDisplayCorrectDataStructureNames(){
        //Given the list of data structures

        //When fragment is resumed
        launchFragmentInContainer<DataStructureListFragment>(Bundle(),R.style.AppTheme)

        //Then assert
        for((position,dataStructure) in dataStructureList.withIndex()){
            onView(withId(R.id.data_structures)).check(
                matches(
                    childAtPositionWithMatchers(
                        position,
                        withId(R.id.data_structure_group_name),
                        withText(dataStructure[context.getString(R.string.ds_name_map_key)] as String)
                    )
                )
            )
        }

    }

    @Test
    fun arrowShowsWhenDataStructureHasChildren(){
        launchFragmentInContainer<DataStructureListFragment>(Bundle(),R.style.AppTheme)

        //Then assert
        for((position,dataStructure) in dataStructureList.withIndex()){
            dataStructure[context.getString(R.string.ds_children_map_key)]?.let {
                onView(withId(R.id.data_structures)).check(
                    matches(
                        childAtPositionWithMatchers(
                            position,
                            withId(R.id.expand_collapse_icon),
                            isDisplayed()
                        )
                    )
                )
            }

        }
    }

    @Test
    fun clickDataStructureWithChild_showChildren(){
        //Given fragment is launched
        launchFragmentInContainer<DataStructureListFragment>(Bundle(),R.style.AppTheme)

        //Then assert
        for((position,dataStructure) in dataStructureList.withIndex()){
            //Given dataStructure has children
            dataStructure[context.getString(R.string.ds_children_map_key)]?.let {
                //When data structure is clicked
                onView(withId(R.id.data_structures))
                    .perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                            position,
                            click()
                        )
                    )

                //Assert children are shown
                onView(withId(R.id.data_structures)).check(
                    matches(
                        childAtPositionWithMatchers(
                            position,
                            withId(R.id.data_structure_children),
                            isDisplayed()
                        )
                    )
                )
            }

        }
    }
}