package com.kotlin.spweartherapp

import android.graphics.Bitmap
import com.kotlin.spweartherapp.mvp.presenter.PresenterDetail
import com.kotlin.spweartherapp.mvp.view.IViewDetail
import com.kotlin.spweartherapp.mvp.view.IViewMain
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class PresenterDetailTest {
    @Mock
    lateinit var mContext: WeartherApplication
    private val mContextScope = TestCoroutineScope()
    private lateinit var mPresenterDetailTest: PresenterDetail
    @Spy
    lateinit var iViewDetailTest: IViewDetail

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mPresenterDetailTest = PresenterDetail(
            mContextScope.coroutineContext,
            mContextScope.coroutineContext, iViewDetailTest
        )
    }

    @Test
    fun resultDetail() {
        mContextScope.async {
            val latLong: String = "21.033,105.850"
            mPresenterDetailTest.getApiDetail(latLong)
        }
    }

    @Test
    fun resultDetail1() {
        mContextScope.async {
            val latLong: String = "sdvgzfbh,105.850"
            mPresenterDetailTest.getApiDetail(latLong)
        }
    }

    @Test
    fun resultDetail2() {
        mContextScope.async {
            val latLong: String = "10.750,106.667"
            mPresenterDetailTest.getApiDetail(latLong)
        }
    }

    @Test
    fun resultImageWearther() {
        val imag =
            "http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0002_sunny_intervals.png"
        mContextScope.async {
            mPresenterDetailTest.getImageWeather(imag)
        }
    }

    @Test
    fun resultImageWearther1() {
        val imag =
            "http://abc.zyc"
        mContextScope.async {
            mPresenterDetailTest.getImageWeather(imag)
        }
    }

    @Test
    fun runFunFetchResult(){
        mContextScope.runBlockingTest {
            val latLong: String = "21.033,105.850"
            mPresenterDetailTest.getApiDetail(latLong)
        }
    }
}