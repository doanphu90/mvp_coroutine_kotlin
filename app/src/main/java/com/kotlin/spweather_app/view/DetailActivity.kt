package com.kotlin.spweather_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlin.spweather_app.R
import com.kotlin.spweather_app.model.CityDetail
import com.kotlin.spweather_app.model.CurrentCondition
import com.kotlin.spweather_app.model.DetailWeather
import com.kotlin.spweather_app.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var itemCity: CityDetail
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        itemCity = intent.extras?.getParcelable<CityDetail>("SelectCity")!!
        tv_title.text = itemCity.areaName + " - " + itemCity.country
        //
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        ll_title.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        setUpViewModel()
    }

    fun setUpViewModel() {
        viewModel.getResultDetailWeather(this, itemCity.latitude + "," + itemCity.longitude)

        viewModel.showResultWeather().observe(this, Observer {
            var currentCondi = it.data.currentCondition
            tv_humidity.text = currentCondi.get(0).humidity
            tv_temp.text = currentCondi.get(0).tempC + " oC"
            var url = currentCondi.get(0).weatherIconUrl.get(0).value
            CoroutineScope(Dispatchers.Main).launch {
                im_wearther.setImageBitmap(viewModel.getImageWeather(url).await())
            }
        })
    }
}
