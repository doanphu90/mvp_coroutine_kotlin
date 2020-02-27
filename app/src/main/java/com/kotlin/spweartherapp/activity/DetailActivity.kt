package com.kotlin.spweartherapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kotlin.spweartherapp.R
import com.kotlin.spweartherapp.mvp.model.CurrentCondition
import com.kotlin.spweartherapp.mvp.model.ResultSearch
import com.kotlin.spweartherapp.mvp.presenter.PresenterDetail
import com.kotlin.spweartherapp.mvp.view.IViewDetail
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailActivity : AppCompatActivity(), IViewDetail, CoroutineScope {
    val TAG: String = "DetailActivity"
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private var presenterDetail: PresenterDetail? = null
    private lateinit var itemCity: ResultSearch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        job = Job()
        itemCity = intent.extras?.getParcelable<ResultSearch>("SelectCity")!!
        tv_title.text = itemCity.areaName + " - " + itemCity.country
        //
        presenterDetail = PresenterDetail(coroutineContext, Dispatchers.IO, this)
        presenterDetail!!.getDetail(itemCity.latitude + "," + itemCity.longitude)

        ll_top.setOnClickListener {
            onBackPressed()
        }
    }

    override fun showHidePro(isChecked: Boolean) {
//        if (isChecked) {
//            pro_detail.visibility = View.VISIBLE
//        } else {
//            pro_detail.visibility = View.GONE
//        }
    }

    override fun fetchDetail(cur: List<CurrentCondition>) {
        Log.d(TAG, "Resul Detail: ${cur.size}")
        CoroutineScope(coroutineContext).launch {
            if (cur.size > 0) {
                var url = cur[0].weatherIconUrl
                im_wearther.setImageBitmap(presenterDetail?.getImageWeather(url)?.await())
                tv_humidity.text = cur.get(0).humidity
                tv_temp.text = cur.get(0).tempC + " oC"
            } else {
                Log.d(TAG, "Resul Detail: ${cur.size}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
