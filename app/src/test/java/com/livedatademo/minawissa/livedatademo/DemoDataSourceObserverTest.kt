package com.livedatademo.minawissa.livedatademo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DemoDataSourceObserverTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock lateinit var observer: Observer<List<Int>>
    @Mock lateinit var lifeCycleOwner: LifecycleOwner

    lateinit var lifeCycle: LifecycleRegistry
    val dataSource = DemoDataSource()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        lifeCycle = LifecycleRegistry(lifeCycleOwner)
        `when`(lifeCycleOwner.lifecycle).thenReturn(lifeCycle)
        lifeCycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @Test
    fun shouldObserveInitialData() {
        dataSource.numberLiveData.observe(lifeCycleOwner, observer)
        dataSource.loadNumbers()
        verify(observer).onChanged(listOf(1, 2, 3, 4))
    }
}