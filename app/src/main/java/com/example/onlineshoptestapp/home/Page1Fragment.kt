package com.example.onlineshoptestapp.home

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.onlineshoptestapp.R
import com.example.onlineshoptestapp.databinding.FragmentPage1Binding
import com.example.onlineshoptestapp.home.category.CarouselRvAdapter
import com.example.onlineshoptestapp.home.category.Category
import com.example.onlineshoptestapp.home.flash.FlashRvAdapter
import com.example.onlineshoptestapp.home.latest.LatestRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Page1Fragment : Fragment() {

    private var _binding: FragmentPage1Binding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPage1Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.profileButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_page1Fragment_to_profileFragment)
        }

        val categories = listOf(
            Category(R.drawable.ic_phones_category, "Phones"),
            Category(R.drawable.ic_cars, "Cars"),
            Category(R.drawable.ic_games, "Games"),
            Category(R.drawable.ic_furniture, "Furniture"),
            Category(R.drawable.ic_kids, "Kids"),
            Category(R.drawable.ic_headphones, "Head phones"),
            Category(R.drawable.ic_phones_category, "Phones"),
            Category(R.drawable.ic_cars, "Cars"),
            Category(R.drawable.ic_games, "Games"),
            Category(R.drawable.ic_furniture, "Furniture"),
            Category(R.drawable.ic_kids, "Kids"),
            Category(R.drawable.ic_headphones, "Head phones"),
        )

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((20 * Resources.getSystem().displayMetrics.density).toInt()))

        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            adapter = CarouselRvAdapter(categories)
        }

        viewModel.viewModelScope.launch {
            viewModel.getLatestGoods()
            val latestGoods = viewModel.state.value.latestGoods!!
            binding.latestRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                clipChildren = false
                clipToPadding = false
                adapter = LatestRvAdapter(latestGoods)
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.getFlashGoods()
            val flashGoods = viewModel.state.value.flashGoods!!
            binding.flashViewPager.apply {
                clipChildren = false
                clipToPadding = false
                offscreenPageLimit = 6
                adapter = FlashRvAdapter(flashGoods)
                setPageTransformer(compositePageTransformer)
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.getLatestGoods()
            val brandsGoods = viewModel.state.value.latestGoods!!
            binding.brandsRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                clipChildren = false
                clipToPadding = false
                adapter = LatestRvAdapter(brandsGoods)
            }
        }


        return view
    }
}