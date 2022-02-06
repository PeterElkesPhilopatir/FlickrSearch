package com.peter.flickrsearch.framework.ui.image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.peter.flickrsearch.R
import com.peter.flickrsearch.databinding.FragmentImageBinding
import com.peter.flickrsearch.framework.ui.search.SearchViewModel


class ImageFragment : Fragment() {
    private lateinit var binding: FragmentImageBinding
    private lateinit var viewModel : PhotoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setData(ImageFragmentArgs.fromBundle(requireArguments()).photo)
        return binding.root
    }

}