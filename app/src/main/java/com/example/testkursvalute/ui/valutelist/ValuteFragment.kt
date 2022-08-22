package com.example.testkursvalute.ui.valutelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testkursvalute.R
import com.example.testkursvalute.adapter.OnItemClickCallback
import com.example.testkursvalute.adapter.ValuteAdapter
import com.example.testkursvalute.databinding.FragmentValuteBinding
import com.example.testkursvalute.navigation.MainNavigationFragment
import com.example.testkursvalute.utils.doOnChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_valute.*

@AndroidEntryPoint
class ValuteListFragment : MainNavigationFragment(), OnItemClickCallback {
    private lateinit var binding: FragmentValuteBinding
    private val viewModel: ValuteViewModel by viewModels()
    private var valutesListAdapter = ValuteAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentValuteBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@ValuteListFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()

        viewModel.loadValuteFromApi()
    }

    override fun initializeViews() {
        valuteRatesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = valutesListAdapter
        }
    }


    override fun observeViewModel() {

        viewModel.isLoading.doOnChange(this) {
            valuteListLoading.visibility =
                if (viewModel.isListEmpty() && it) View.VISIBLE else View.GONE

            if (it) {
                valutesListErrorView.visibility = View.GONE
            }
        }

        viewModel.valutesListData.doOnChange(this) {
            valutesListAdapter.updateList(it)

            valutesListErrorView.visibility =
                if (viewModel.isListEmpty()) View.VISIBLE else View.GONE
        }


        viewModel.favouritesStock.doOnChange(this) {
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


    override fun onFavouriteClick(charCode: String) {
        viewModel.updateFavouriteStatus(charCode)
    }


    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }
}