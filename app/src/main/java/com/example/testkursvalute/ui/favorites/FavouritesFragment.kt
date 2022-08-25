package com.example.testkursvalute.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourites.*
import com.example.testkursvalute.adapter.ValutesListAdapter
import com.example.testkursvalute.adapter.OnItemClickCallback
import com.example.testkursvalute.common.MainNavigationFragment
import com.example.testkursvalute.databinding.FragmentFavouritesBinding
import com.example.testkursvalute.util.doOnChange

@AndroidEntryPoint
class FavouritesFragment: MainNavigationFragment(), OnItemClickCallback {
    private lateinit var binding: FragmentFavouritesBinding

    private val viewModel: FavouriteViewModel by viewModels()
    private var favoritesAdapter = ValutesListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@FavouritesFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    override fun initializeViews() {
        favouritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoritesAdapter
        }
    }


    override fun observeViewModel() {

        viewModel.favouriteValutesList.doOnChange(this) {
            favoritesAdapter.updateList(it)

            if (it.isEmpty()) viewModel.isFavouriteEmpty(true)
        }


        viewModel.favouriteStock.doOnChange(this) {
            it?.let {
                showToast(
                    if (it.isFavorite) "${it.name} добавлен в избранное"
                    else "${it.name} удален из избранного"
                )
            }
        }


        viewModel.toastError.doOnChange(this) {
            showToast(it, Toast.LENGTH_LONG)
        }
    }


    override fun onFavouriteClick(charCode: String) {
        viewModel.updateFavouriteStatus(charCode)
    }


    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}