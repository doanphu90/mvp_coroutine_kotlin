package com.kotlin.spweather_app

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.kotlin.spweather_app.api.ApiBuilder
import com.kotlin.spweather_app.api.ApiInterface
import com.kotlin.spweather_app.model.ResulSearch
import com.kotlin.spweather_app.viewmodel.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @Mock
    private lateinit var mContext: WeatherApplication
    private lateinit var viewModel: HomeViewModel
    private var apiInterface: ApiInterface? = null
    private lateinit var disposables: CompositeDisposable
    private val searchResponse = MutableLiveData<ResulSearch>()
    private val mContextScope = TestCoroutineScope()

    @Before
    fun initStart() {
        disposables = CompositeDisposable()
        mContext = mock(WeatherApplication::class.java)
        MockitoAnnotations.initMocks(this)
        viewModel = mock(HomeViewModel::class.java)
    }

    @Test
    fun testBiuldApiWithRetrofit() {
        val query = "abc"
        apiInterface = ApiBuilder.getClient(mContext)?.create(ApiInterface::class.java)
        disposables.add(
            apiInterface?.getResultSearch(query)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ t: ResulSearch? -> searchResponse.value = t })!!
        )
    }

    @Test
    fun testCallApi() {
        val query = "abc"
        viewModel.getResultSearch(mContext, query)
    }

    @Test
    fun getData() {
        viewModel.showResultSearch()
    }

    /*test call api city weather*/

    @Test
    fun testCallApiCityWeather() {
        var latLong = "123.01,456.002"
        viewModel.getResultDetailWeather(mContext, latLong)
    }

    @Test
    fun getDataCityWeather() {
        viewModel.showResultWeather()
    }

    @Test
    fun getUrlIconWeather() {
        val urlIcon =
            "http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0001_sunny.png"
        mContextScope.async {
            viewModel.getImageWeather(urlIcon)
        }
    }

    @Test
    fun getUrlIconWeather2() {
        val urlIcon = "http://abc.com"
        runBlockingTest {
            mContextScope.launch { viewModel.getImageWeather(urlIcon).await() }
        }
    }
}