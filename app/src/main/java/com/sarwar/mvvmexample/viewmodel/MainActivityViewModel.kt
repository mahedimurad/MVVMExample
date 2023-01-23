package com.sarwar.mvvmexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private var _counter = 0

    private var _counterMutableLiveData : MutableLiveData<Int> = MutableLiveData()
    val counterLiveData : LiveData<Int> get() =  _counterMutableLiveData

    fun increaseCounter(){
        _counter++
        _counterMutableLiveData.postValue(_counter)
    }


}