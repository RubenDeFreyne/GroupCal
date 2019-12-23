package com.example.groupcal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.models.Group
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.rxkotlin.addTo

class GroupViewModel(val repo : GroupRepository) : ViewModel() {


    private val disposable = CompositeDisposable()
    private var onError = ""

    private var _groups: MutableLiveData<MutableList<Group>> = MutableLiveData()
    val groups: LiveData<MutableList<Group>> = _groups




    fun getGroups() {
        val list = repo.getGroupsFromDb().subscribe(
            {
                _groups.value = it.map { g -> g.toGroup() }.toMutableList()
            },
            {
                onError = it.toString()
            }
        ).addTo(disposable)


    }

}