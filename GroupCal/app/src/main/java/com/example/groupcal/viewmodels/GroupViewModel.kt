package com.example.groupcal.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.models.Group
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GroupViewModel(val repo : GroupRepository) : ViewModel() {


    private val disposable = CompositeDisposable()
    private var onError = ""

    private var _groups: MutableLiveData<List<Group>> = MutableLiveData()
    val groups: LiveData<List<Group>> = _groups

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )



    fun getGroups() {

        coroutineScope.launch {
            var getGroups = repo.getGroupsfromApi()
            try {
                var listResult = getGroups.await()
                _groups.value = listResult
            }catch (t : Throwable){

            }
        }


    }

}