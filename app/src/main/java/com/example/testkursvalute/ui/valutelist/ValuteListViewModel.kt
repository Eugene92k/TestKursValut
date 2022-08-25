package com.example.testkursvalute.ui.valutelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.testkursvalute.api.Result
import com.example.testkursvalute.data.local.database.ValutesListEntity
import com.example.testkursvalute.data.repo.valutesList.ValutesListRepository
import javax.inject.Inject


@HiltViewModel
class ValuteListViewModel @Inject constructor(private val repository: ValutesListRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    val valutesListData = repository.allValutesList

    private val _favouriteStock = MutableLiveData<ValutesListEntity?>()
    val favouriteStock: LiveData<ValutesListEntity?> = _favouriteStock

    fun isListEmpty(): Boolean {
        return valutesListData.value?.isEmpty() ?: true
    }


    fun loadValutesFromApi() {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                repository.valutesList()
                _isLoading.postValue(false)
            }
        }
    }


    fun updateFavouriteStatus(charCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.updateFavouriteStatus(charCode)) {
                is Result.Success -> _favouriteStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }
}