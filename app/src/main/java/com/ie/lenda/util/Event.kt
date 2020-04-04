package com.ie.lenda.util

class Event<out T>(private val content:T){
    private var handled = false
    fun getContent():T?{
        return if(!handled){
            handled = true
            content
        }
        else null
    }

}