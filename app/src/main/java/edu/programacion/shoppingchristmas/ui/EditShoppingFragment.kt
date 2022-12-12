package edu.programacion.shoppingchristmas.ui

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import edu.programacion.shoppingchristmas.R
import edu.programacion.shoppingchristmas.ShoppingApplication
import edu.programacion.shoppingchristmas.databinding.FragmentEditShoppingBinding
import edu.programacion.shoppingchristmas.model.Shopping
import kotlinx.coroutines.launch
import java.util.*


class EditShoppingFragment : Fragment(), MenuProvider {

    val oldNotes by navArgs<EditShoppingFragmentArgs>() //para recoger los datos enviados por naveg

    lateinit var  binding: FragmentEditShoppingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditShoppingBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //opciones del menu en este fragment
        activity?.addMenuProvider(this)

        //mostrar los datos en el fragment
        binding.editName.setText(oldNotes.data.name)
        binding.editGift.setText(oldNotes.data.gift)
        binding.editMoney.setText(oldNotes.data.money.toString()) //el valor es numero
        binding.editDescription.setText(oldNotes.data.description)

        //crear la funcion para el update
        binding.btnDone.setOnClickListener {
            updateShopping(it)
        }
    }

    private fun updateShopping(it: View?) {

        //almacenamos en una variable los valores nuevos
        val name = binding.editName.text.toString()
        val gift = binding.editGift.text.toString()
        val money = binding.editMoney.text.toString()
        val description = binding.editDescription.text.toString()

        val d = Date()
        val shoppingDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        //crear el objeto Shopping
        val data = Shopping (
            oldNotes.data.id, //recogemos el id ya que no va a cambiar
            name = name,
            gift = gift,
            money = money.toLong(), //pasamos el dato a Long
            description = description,
            date = shoppingDate.toString(),
            iscompleated = oldNotes.data.iscompleated ,// recogemos este dato ya que no se ha modificado
        )

        //lanzamos una corrutina para la tarea de modificar un registro
        lifecycleScope.launch {
            ShoppingApplication.database.shoppingDao().updateShopping(data)
        }

        Toast.makeText(requireContext(),"Compra modificada", Toast.LENGTH_SHORT).show()

        //volver hacia atras en la nevagacion
        Navigation.findNavController(requireView()).navigate(R.id.action_editShoppingFragment_to_homeFragment)
    }


    //empezamos el tratamiento del borrado activando el menu

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.menu_delete){

            //vamos a mostrar el dialog por el final de la pantalla
            //para ello creamos un estilo de ventana
            val bottonSheet = BottomSheetDialog(requireContext(),R.style.BottonSheetStyle)
            bottonSheet.setContentView(R.layout.dialog_delete)

            val textYes = bottonSheet.findViewById<TextView>(R.id.dialogYes)
            val textNo  = bottonSheet.findViewById<TextView>(R.id.dialogNo)

            textYes?.setOnClickListener {
                //lanzamos una corrutina para la tarea de borrar un registro
                lifecycleScope.launch {
                    ShoppingApplication.database.shoppingDao().deleteShopping(oldNotes.data.id!!)
                }

                Toast.makeText(requireContext(),"Nota borrada", Toast.LENGTH_SHORT).show()
                bottonSheet.dismiss()  //cerramos el dialog
                Navigation.findNavController(requireView()).navigate(R.id.action_editShoppingFragment_to_homeFragment)
            }

            textNo?.setOnClickListener {
                bottonSheet.dismiss()
            }

            bottonSheet.show()
        }

        return true
    }

    //eliminar el menu al salir del fragment
    override fun onDestroyView() {
        activity?.removeMenuProvider(this)
        super.onDestroyView()
    }
}