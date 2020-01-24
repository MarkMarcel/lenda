package com.ie.lenda.datastructures

class ArrayDataStructure(size:Int) {
    private var array = Array(size) { 0}

    fun insert(value:Int, index:Int){
        array[index] = value
    }

    fun get(index:Int):Int{
        return array[index]
    }
}