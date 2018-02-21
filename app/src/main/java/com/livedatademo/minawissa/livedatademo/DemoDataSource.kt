package com.livedatademo.minawissa.livedatademo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData


class DemoDataSource {

    private val numbers = mutableListOf(1, 2, 3, 4)

    private val _numbersLiveDate = MutableLiveData<List<Int>>()
    val numberLiveData: LiveData<List<Int>>
        get() = _numbersLiveDate

    fun loadNumbers() {
        _numbersLiveDate.postValue(numbers)
    }

    fun addItem() {
        val lastItem = numbers[numbers.size - 1]
        val newItem = lastItem + 1
        numbers.add(newItem)
        _numbersLiveDate.postValue(numbers)
    }
}