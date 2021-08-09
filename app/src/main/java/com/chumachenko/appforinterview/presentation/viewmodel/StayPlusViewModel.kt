package com.chumachenko.appforinterview.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chumachenko.appforinterview.data.repository.StayPlusRepository
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import com.chumachenko.appforinterview.presentation.support.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StayPlusViewModel @Inject constructor(private val stayPlusRepository: StayPlusRepository) :
    BaseViewModel() {

    private val _stayPlusResponseItem: MutableLiveData<Resource<List<StayPlusItem>>> =
        MutableLiveData()
    val stayPlusResponseItem: LiveData<Resource<List<StayPlusItem>>> = _stayPlusResponseItem

    private val _stayPlusLocalItem: MutableLiveData<Resource<List<StayPlusItem>>> =
        MutableLiveData()
    val stayPlusLocalItem: LiveData<Resource<List<StayPlusItem>>> = _stayPlusLocalItem

    private fun getImagesResponse() {
        launch {
            stayPlusRepository.getStayPlusByLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _stayPlusResponseItem.postValue(Resource.loading()) }
                .subscribe(({
                    _stayPlusResponseItem.value = Resource.success(it)
                }), ({ error ->
                    _stayPlusResponseItem.value = Resource.error()
                    error.printStackTrace()
                }))
        }
    }

    fun getImagesLocal() {
        launch {
            stayPlusRepository.getFromLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _stayPlusLocalItem.postValue(Resource.loading()) }
                .doAfterTerminate {
                    if (stayPlusLocalItem.value?.data?.size ?: 0 == 0)
                        getImagesResponse()
                }
                .subscribe(({
                    _stayPlusLocalItem.value = Resource.success(it)
                }), ({ error ->
                    _stayPlusLocalItem.value = Resource.error()
                    error.printStackTrace()
                }))
        }
    }
}