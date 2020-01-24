package com.ie.lenda.datastructurestest

import com.ie.lenda.datastructures.ArrayDataStructure
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class ArrayDataStructureTest {
    private lateinit var array: ArrayDataStructure

    @Before
    fun createArray(){
        this.array = ArrayDataStructure(10)
    }
    @Test
    fun addsItemAtIndex(){
        val value = 2
        val index = 2

        array.insert(value,index)
        assertEquals(value,array.get(index))
    }
}