package edu.programacion.shoppingchristmas.ui

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import edu.programacion.shoppingchristmas.R
import edu.programacion.shoppingchristmas.ShoppingApplication
import edu.programacion.shoppingchristmas.databinding.FragmentCreateShoppingBinding
import edu.programacion.shoppingchristmas.model.Shopping
import kotlinx.coroutines.launch
import java.util.*


class CreateShoppingFragment : Fragment() {

    lateinit var binding: FragmentCreateShoppingBinding
    var priority: String = "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateShoppingBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDone.setOnClickListener {
            createShopping()
        }
    }

    private fun createShopping() {
        val name = binding.editName.text.toString()
        val gift = binding.editGift.text.toString()
        val description = binding.editDescription.text.toString()
        val money = binding.editMoney.text.toString()
        val d = Date()
        val shoppingDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        Log.i("info", "create notes: $shoppingDate")

        //crear Shopping
        val data = Shopping(
            null,
            name = name,
            gift = gift,
            description = description,
            money = money.toLong(),
            date = shoppingDate.toString(),
            iscompleated = false
        )
        //lanzamos una courutine para la tarea de añadir un registro
        lifecycleScope.launch{
            ShoppingApplication.database.shoppingDao().insertShopping(data)
        }

        Toast.makeText(requireContext(), "Nota añadida correctamente", Toast.LENGTH_SHORT).show()

        //volver al fragmet Homefragment
        Navigation.findNavController(requireView()).navigate(R.id.action_createShoppingFragment_to_homeFragment)
    }

}