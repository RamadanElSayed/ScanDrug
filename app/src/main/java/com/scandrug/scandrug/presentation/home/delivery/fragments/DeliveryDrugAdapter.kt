package com.scandrug.scandrug.presentation.home.delivery.fragments
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.scandrug.scandrug.R
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.databinding.CartListItemBinding
import com.scandrug.scandrug.databinding.CompleteListItemBinding
import com.scandrug.scandrug.databinding.DeliveryListItemBinding

class DeliveryDrugAdapter(private val onItemClicked: (DrugDetailsModel, position: Int) -> Unit) :
    RecyclerView.Adapter<DeliveryDrugAdapter.ItemViewHolder>() {

    private var DrugsResponseItemList: ArrayList<DrugDetailsModel> =
        arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = DeliveryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(
            parent.context,
            itemBinding,onItemClicked
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val DrugsResponseItem = DrugsResponseItemList[position]
        holder.bind(DrugsResponseItem,position)
    }

    override fun getItemCount(): Int {
        return DrugsResponseItemList.size
    }

    class ItemViewHolder(
        private val context: Context,
        private val DrugRowBinding: DeliveryListItemBinding,
        private val onItemClicked: (DrugDetailsModel, position: Int) -> Unit
    ) : RecyclerView.ViewHolder(DrugRowBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(drugsResponseItem: DrugDetailsModel, position: Int) {
            DrugRowBinding.tvClientName.text=drugsResponseItem.userName
            DrugRowBinding.tvDelievryName.text="Delivery 1"
            DrugRowBinding.tvAddress.text=drugsResponseItem.clientCity+" "+drugsResponseItem.clientApartment+" "+drugsResponseItem.clientStreet
            DrugRowBinding.cartItem.setOnClickListener { onItemClicked(drugsResponseItem, position) }

        }
    }

    fun addDrugs(DrugsResponseItem: List<DrugDetailsModel>) {
        DrugsResponseItemList= arrayListOf()
        DrugsResponseItemList.clear()
        DrugsResponseItemList.addAll(DrugsResponseItem)
        this.notifyDataSetChanged()
    }
}