package com.example.testkursvalute.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkursvalute.data.local.database.ValuteEntity
import com.example.testkursvalute.data.repo.favourites.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.testkursvalute.api.Result


@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: FavouritesRepository) :
    ViewModel() {

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    val favouriteValutesList: LiveData<List<ValuteEntity>> = repository.favouriteValues


    private val _favouriteStock = MutableLiveData<ValuteEntity?>()
    val favouriteStock: LiveData<ValuteEntity?> = _favouriteStock


    private val _favouriteEmpty = MutableLiveData<Boolean>()
    val favouriteEmpty: LiveData<Boolean> = _favouriteEmpty


    fun updateFavouriteStatus(charCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.updateFavouriteStatus(charCode)) {
                is Result.Success -> _favouriteStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
                else -> {}
            }
        }
    }


    fun isFavouriteEmpty(empty: Boolean) {
        _favouriteEmpty.postValue(empty)
    }
}