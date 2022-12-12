package edu.programacion.shoppingchristmas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.programacion.shoppingchristmas.R
import edu.programacion.shoppingchristmas.databinding.ViewShoppingBinding
import edu.programacion.shoppingchristmas.model.Shopping


class ShoppingAdapter(private var shoppingList: MutableList<Shopping>,
                      private var listener: ShoppingOnClickListener)
    : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = shoppingList.get(position)
        holder.bind(item)

        holder.setListener(item)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    fun setShopping(shoppings: MutableList<Shopping>) {
        this.shoppingList = shoppings
        notifyDataSetChanged()
    }

    fun update(shopping: Shopping) {
        val index = shoppingList.indexOf(shopping)
        //preguntamos por el index
        if(index != -1){
            shoppingList.set(index, shopping)
            notifyItemChanged(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val binding = ViewShoppingBinding.bind(view)

        fun bind(shopping: Shopping){
            binding.textName.text = shopping.name
            binding.textGift.text = shopping.gift
            binding.textMoney.text = shopping.money.toString()
            binding.textDate.text = shopping.date
            binding.viewCompleted.isChecked = shopping.iscompleated
        }

        fun setListener(item: Shopping) {
            binding.viewCompleted.setOnClickListener { listener.onCompleatedShopping(item) }
            binding.root.setOnClickListener { listener.onClickShopping(item) }

        }
    }
}