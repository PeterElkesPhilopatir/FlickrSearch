package com.peter.flickrsearch.framework.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.peter.flickrsearch.business.models.ApiStatus
import com.peter.flickrsearch.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    lateinit var adapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.apply {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel!!.setQuery(query)
                    return true
                }

                override fun onQueryTextChange(query: String): Boolean {
                    return true
                }
            })
            adapter = PhotosAdapter(OnClickListener {
                viewModel!!.displayPropertyDetails(it)
            })
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = PhotosLoadStateAdapter()
            )
        }

        viewModel.apply {
            status.observe(viewLifecycleOwner) {
                if (status.value == ApiStatus.EMPTY)
                    Toast.makeText(context, "EMPTY", Toast.LENGTH_SHORT).show()
            }
            selectedItem.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(
                        SearchFragmentDirections.actionMainFragmentToImageFragment(
                            it
                        )
                    )
                    viewModel.displayPropertyDetailsComplete()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                adapter.submitData(it)
            }
        }
        return binding.root
    }

}