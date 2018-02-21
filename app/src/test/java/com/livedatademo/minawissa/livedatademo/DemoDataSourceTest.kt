package com.livedatademo.minawissa.livedatademo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class DemoDataSourceTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun shouldLoadData() {
        val dataSource = DemoDataSource()
        dataSource.loadNumbers()
        assertEquals(listOf(1, 2, 3, 4), dataSource.numberLiveData.value)
    }

    @Test
    fun shouldUpdateData() {
        val dataSource = DemoDataSource()
        dataSource.loadNumbers()
        dataSource.addItem()
        assertEquals(listOf(1, 2, 3, 4, 5), dataSource.numberLiveData.value)
    }
}