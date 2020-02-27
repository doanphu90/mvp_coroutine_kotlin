package com.kotlin.spweartherapp

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.kotlin.spweartherapp.activity.DetailActivity
import com.kotlin.spweartherapp.mvp.model.ResultSearch
import com.kotlin.spweartherapp.mvp.presenter.PresenterSearch
import com.kotlin.spweartherapp.mvp.view.IViewMain
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.stubbing.Answer


class PresenterSearchTest {
    @Mock
    lateinit var mContext: WeartherApplication
    private val mContextScope = TestCoroutineScope()
    private lateinit var mPresenterTest: PresenterSearch
    @Spy
    lateinit var iViewMainTest: IViewMain

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mContext = mock(WeartherApplication::class.java)
        mPresenterTest = PresenterSearch(
            mContextScope.coroutineContext, mContextScope.coroutineContext,
            iViewMainTest
        )
    }

    @Test
    fun searchResultTest() {
        val query: String = "sin"
        mContextScope.async {
            verify(mPresenterTest).callAPiSearchCity(query)
        }
    }

    @Test
    fun searchResultTest1() {
        val query: String = "zfsgdfh"
        mContextScope.async {
            verify(mPresenterTest).callAPiSearchCity(query)
        }
    }

    @Test
    fun afterAsyncReturnResult() {
        val query: String = "sin"
        mContextScope.launch {
            verify(mPresenterTest).searchCityByKey(query)
        }
    }

    @Test
    fun runFunResul(){
        mContextScope.runBlockingTest {
            var key = "Sin"
            mPresenterTest.searchCityByKey(key)
        }
    }

    @Test
    fun historyTest() {
        mContextScope.async {
            verify(mPresenterTest).getCitySearchHistory()
        }
    }
}