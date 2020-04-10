package com.ie.lenda.datastructure.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ie.lenda.util.Event

class DataStructureListViewModel(application: Application):AndroidViewModel(application) {
    private val _learnDataStructure = MutableLiveData<Event<String?>>()
    var navigateToDataStructure = _learnDataStructure

    fun learnDataStructure(dataStructureName:String){
        _learnDataStructure.value = Event(dataStructureName)
    }
}