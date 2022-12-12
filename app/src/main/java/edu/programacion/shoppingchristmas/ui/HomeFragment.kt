package edu.programacion.shoppingchristmas.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import edu.programacion.shoppingchristmas.R
import edu.programacion.shoppingchristmas.ShoppingApplication
import edu.programacion.shoppingchristmas.adapter.ShoppingAdapter
import edu.programacion.shoppingchristmas.adapter.ShoppingOnClickListener
import edu.programacion.shoppingchristmas.databinding.FragmentHomeBinding
import edu.programacion.shoppingchristmas.model.Shopping
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), ShoppingOnClickListener{

    lateinit var  binding : FragmentHomeBinding
    private lateinit var mGridLayoutManager: GridLayoutManager
    private lateinit var mAdapter: ShoppingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //se indica a donde se va a navegar
        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createShoppingFragment)
        }

        getAllShopping()
        setupRecyclerView()
        setFilter()


    }

    private fun setFilter() {
        //primera opcion sin filtro
        binding.allNotes.setOnClickListener {
            getAllShopping()
            setupRecyclerView()
        }
        binding.filterComple.setOnClickListener {
            getCompleatedShopping()
            setupRecyclerView()
        }
        binding.filterPending.setOnClickListener {
            getPendingShopping()
            setupRecyclerView()
        }
    }

    private fun getPendingShopping() {
        lifecycleScope.launch{
            val shoppingList = ShoppingApplication.database.shoppingDao().getPendingShopping()
            mAdapter.setShopping(shoppingList)
        }
    }

    private fun getCompleatedShopping() {
        lifecycleScope.launch {
            val shoppingComple = ShoppingApplication.database.shoppingDao().getCompleatedShopping()
            mAdapter.setShopping(shoppingComple)
        }
    }



    private fun setupRecyclerView() {
        mAdapter = ShoppingAdapter(mutableListOf(),this)
        mGridLayoutManager = GridLayoutManager(requireContext(),2)

        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayoutManager
            adapter = mAdapter
        }
    }

    private fun getAllShopping() {
        lifecycleScope.launch {
            val shoppingList = ShoppingApplication.database.shoppingDao().getAllShopping()
            mAdapter.setShopping(shoppingList)
        }
    }


    //estos son los miembros de la interface onClick

    override fun onCompleatedShopping(shopping: Shopping) {
        //modificar el valor de iscompleated
        shopping.iscompleated = !shopping.iscompleated
        //actualizar las datos
        lifecycleScope.launch {
            val shoppingList = ShoppingApplication.database.shoppingDao().updateShopping(shopping)
            mAdapter.update(shopping)
        }
    }

    override fun onClickShopping(shopping: Shopping) {
        val action = HomeFragmentDirections.actionHomeFragmentToEditShoppingFragment(shopping)
        Navigation.findNavController(requireView()).navigate(action)
    }
}