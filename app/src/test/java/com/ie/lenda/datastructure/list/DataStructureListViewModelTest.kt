package com.ie.lenda.datastructure.list


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ie.lenda.getOrAwaitValue
import com.ie.lenda.util.Event
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataStructureListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    val viewModel = DataStructureListViewModel(ApplicationProvider.getApplicationContext())

    @Test
    fun learnDataStructure_setsNavigateToDataStructureEvent() {
        //Given a data structure name
        val dataStructureName = "Tree"

        //When learnDataStructure is called
        viewModel.learnDataStructure(dataStructureName)

        //Then assert that navigateToDataStructure Event is set and the value is dataStructureName
        val navigateToDataStructureEvent:Event<String?> = viewModel.navigateToDataStructure.getOrAwaitValue()
        assertThat(navigateToDataStructureEvent.getContent(),`is`("Tree"))
    }
}