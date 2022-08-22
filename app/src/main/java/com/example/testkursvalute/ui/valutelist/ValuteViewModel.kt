package com.example.testkursvalute.ui.valutelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkursvalute.api.Result
import com.example.testkursvalute.data.local.database.ValuteEntity
import com.example.testkursvalute.data.repo.valutes.ValuteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ValuteViewModel @Inject constructor(private val repository: ValuteRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    val valutesListData = repository.allValutesList

    private val _favouritesStock = MutableLiveData<ValuteEntity?>()
    val favouritesStock: LiveData<ValuteEntity?> = _favouritesStock

    fun isListEmpty(): Boolean {
        return valutesListData.value?.isEmpty() ?: true
    }

    fun loadValuteFromApi() {
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
                is Result.Success -> _favouritesStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
                else -> {}
            }
        }
    }
}