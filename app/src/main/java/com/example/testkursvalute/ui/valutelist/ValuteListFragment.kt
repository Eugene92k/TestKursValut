package com.example.testkursvalute.ui.valutelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.example.testkursvalute.adapter.ValutesListAdapter
import com.example.testkursvalute.adapter.OnItemClickCallback
import com.example.testkursvalute.common.MainNavigationFragment
import com.example.testkursvalute.databinding.FragmentValuteListBinding
import com.example.testkursvalute.util.doOnChange

import kotlinx.android.synthetic.main.fragment_valute_list.*

@AndroidEntryPoint
class ValuteListFragment : MainNavigationFragment(), OnItemClickCallback {
    private lateinit var binding: FragmentValuteListBinding
    private val viewModel: ValuteListViewModel by viewModels()
    private var valutesListAdapter = ValutesListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentValuteListBinding.inflate(inflater, container, false)
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

        //загрузка данных
        viewModel.loadValutesFromApi()
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