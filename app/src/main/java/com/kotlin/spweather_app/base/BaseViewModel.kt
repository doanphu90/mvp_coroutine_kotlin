package com.kotlin.spweather_app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.spweather_app.api.BaseResponse
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel:ViewModel() {
    val eventLoading = MutableLiveData<Event<Boolean>>()
    val eventError = MutableLiveData<Event<BaseResponse>>()
    val eventFailure = MutableLiveData<Event<Throwable>>()
    val disposables = CompositeDisposable()

    fun showLoading(value: Boolean) {
        eventLoading.value = Event(value)
    }

    fun showError(baseResponse: BaseResponse) {
        eventError.value = Event(baseResponse)
    }

    fun showFailure(throwable: Throwable) {
        eventFailure.value = Event(throwable)
    }
}