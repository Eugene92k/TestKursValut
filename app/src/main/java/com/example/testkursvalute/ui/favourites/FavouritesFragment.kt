package com.example.testkursvalute.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testkursvalute.adapter.OnItemClickCallback
import com.example.testkursvalute.adapter.ValuteAdapter
import com.example.testkursvalute.databinding.FragmentFavouritiesBinding
import com.example.testkursvalute.navigation.MainNavigationFragment
import com.example.testkursvalute.utils.doOnChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourities.*


@AndroidEntryPoint
class FavouritesFragment: MainNavigationFragment(), OnItemClickCallback {
    private lateinit var binding: FragmentFavouritiesBinding

    private val viewModel: FavouriteViewModel by viewModels()
    private var favoritesAdapter = ValuteAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFavouritiesBinding.inflate(inflater, container, false)
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
                    if (it.isFavourite) "${it.name} добавлен в избранное"
                    else "${it.name} удален из избранного"
                )
            }
        }


        viewModel.toastError.doOnChange(this) {
            showToast(it, Toast.LENGTH_LONG)
        }
    }


    override fun onFavouriteClick(id: String) {
        viewModel.updateFavouriteStatus(id)
    }


    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}