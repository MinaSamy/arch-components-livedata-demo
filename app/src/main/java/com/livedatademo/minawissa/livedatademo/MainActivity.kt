package com.livedatademo.minawissa.livedatademo

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val dataSource = DemoDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt = findViewById<TextView>(R.id.text_view_numbers)


        /*dataSource.numberLiveData.observe(this, object : Observer<List<Int>> {
            override fun onChanged(numbers: List<Int>?) {
                Log.e("Numbers","Receive")
                txt.text = numbers?.toString()
            }

        })*/

        val mappedData = Transformations.map(dataSource.numberLiveData, { numbers ->
            numbers.map { "Item ${it}" }
        })

        mappedData.observe(this, object : Observer<List<String>> {
            override fun onChanged(items: List<String>?) {
                txt.text = items?.toString()
            }
        })


        val sumLiveData = Transformations.switchMap(dataSource.numberLiveData,
                { numbers ->
                    MutableLiveData<Int>().apply {
                        postValue(numbers.sum())
                    }
                })

        sumLiveData.observe(this, object : Observer<Int> {
            override fun onChanged(sum: Int?) {
                Log.e("Sum", sum.toString())
            }

        })

        dataSource.loadNumbers()

        val addItemButton = findViewById<Button>(R.id.button_add_item)
        addItemButton.setOnClickListener({ dataSource.addItem() })

        val merger = MediatorLiveData<Int>()
        merger.addSource(dataSource.numberLiveData, { numbers -> numbers?.forEach { merger.postValue(it) } })
        merger.addSource(sumLiveData, { sum -> merger.postValue(sum) })

        merger.observe(this, object : Observer<Int> {
            override fun onChanged(number: Int?) {
                Log.i("Merger", number.toString())
            }

        })

        val source1 = MutableLiveData<Int>()
        val source2 = MutableLiveData<Int>()

        val merger2 = MediatorLiveData<Int>()
        merger2.addSource(source1, { number -> merger.postValue(number) })
        merger2.addSource(source2, { number -> merger.postValue(number) })

    }

    override fun onStart() {
        super.onStart()
        Log.e("Numbers", "Start")
    }

    override fun onStop() {
        super.onStop()
        dataSource.addItem()
    }
}
