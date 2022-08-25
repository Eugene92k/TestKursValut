package com.example.testkursvalute.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.testkursvalute.api.Result
import com.example.testkursvalute.data.local.database.ValutesListEntity
import com.example.testkursvalute.data.repo.favourites.FavouritesRepository
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: FavouritesRepository) :
    ViewModel() {

    private val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    val favouriteValutesList: LiveData<List<ValutesListEntity>> = repository.favouriteValutes


    private val _favouriteStock = MutableLiveData<ValutesListEntity?>()
    val favouriteStock: LiveData<ValutesListEntity?> = _favouriteStock


    private val _favouriteEmpty = MutableLiveData<Boolean>()
    val favouriteEmpty: LiveData<Boolean> = _favouriteEmpty


    fun updateFavouriteStatus(charCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.updateFavouriteStatus(charCode)) {
                is Result.Success -> _favouriteStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }


    fun isFavouriteEmpty(empty: Boolean) {
        _favouriteEmpty.postValue(empty)
    }
}