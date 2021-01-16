package com.example.foodivore.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentHomeBinding
import com.example.foodivore.repository.model.Food
import com.example.foodivore.ui.main.home.adapter.FoodRecyclerAdapter
import com.google.android.material.textview.MaterialTextView

class HomeFragment : Fragment() {

    private lateinit var homeDataBinding: FragmentHomeBinding

//    private val homeViewModel: HomeViewModel by lazy {
//        ViewModelProvider(
//            this,
//            HomeVMFactory(HomeImpl(HomeRepoImpl()), ProductImpl(ProductRepoImpl()))
//        ).get(HomeViewModel::class.java)
//    }

    private lateinit var recyclerFoodCatalogue: RecyclerView
    private lateinit var foodRecyclerAdapter: FoodRecyclerAdapter

    private var dummyData = arrayListOf<Food.Catalogue>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        dummyData.add(
            Food.Catalogue(
                "Title 1",
                arrayListOf(
                    Food.Detail("", "Food 1", 121),
                    Food.Detail("", "Food 2", 122),
                    Food.Detail("", "Food 3", 123),
                    Food.Detail("", "Food 4", 124)
                )
            )
        )

        dummyData.add(
            Food.Catalogue(
                "Title 2",
                arrayListOf(
                    Food.Detail("", "Food 1", 121),
                    Food.Detail("", "Food 2", 122),
                    Food.Detail("", "Food 3", 123),
                    Food.Detail("", "Food 4", 124)
                )
            )
        )

        setupViews(homeDataBinding.root)

        return homeDataBinding.root
    }

    private fun setupViews(view: View) {
        recyclerFoodCatalogue = view.findViewById(R.id.recycler_food_trend_home)
        recyclerFoodCatalogue.layoutManager = LinearLayoutManager(requireContext())
        foodRecyclerAdapter = FoodRecyclerAdapter(requireContext(), dummyData)
        recyclerFoodCatalogue.adapter = foodRecyclerAdapter
    }
}