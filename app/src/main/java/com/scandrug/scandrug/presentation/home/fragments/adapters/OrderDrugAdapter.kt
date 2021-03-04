package com.scandrug.scandrug.presentation.home.fragments.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.scandrug.scandrug.R
import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel
import com.scandrug.scandrug.databinding.CartListItemBinding

class OrderDrugAdapter() :
    RecyclerView.Adapter<OrderDrugAdapter.ItemViewHolder>() {

    private var DrugsResponseItemList: ArrayList<DrugDetailsModel> =
        arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = CartListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(
            parent.context,
            itemBinding,
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val DrugsResponseItem = DrugsResponseItemList[position]
        holder.bind(DrugsResponseItem)
    }

    override fun getItemCount(): Int {
        return DrugsResponseItemList.size
    }

    class ItemViewHolder(
        private val context: Context,
        private val DrugRowBinding: CartListItemBinding,
    ) : RecyclerView.ViewHolder(DrugRowBinding.root) {
        fun bind(drugsResponseItem: DrugDetailsModel) {
            DrugRowBinding.tvProductName.text=drugsResponseItem.drugName
            DrugRowBinding.tvPrice.text=drugsResponseItem.drugPrice
            DrugRowBinding.tvTable.text=drugsResponseItem.tabletNumber
            DrugRowBinding.tvMg.text=drugsResponseItem.tabletWeight
            DrugRowBinding.dateId.text=drugsResponseItem.dateOrder

            if(drugsResponseItem.orderStatus==1)
            {
                DrugRowBinding.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.gray_selected
                    )
                )
                DrugRowBinding.divider2.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.gray_selected
                    )
                )
                DrugRowBinding.imageView2.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                DrugRowBinding.imageView3.setBackgroundResource(R.drawable.ic_right)
                DrugRowBinding.imageView4.setBackgroundResource(R.drawable.ic_right)



            }
            else if(drugsResponseItem.orderStatus==2)
            {
                DrugRowBinding.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_primary
                    )
                )
                DrugRowBinding.divider2.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.gray_selected
                    )
                )

                DrugRowBinding.imageView2.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                DrugRowBinding.imageView3.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                DrugRowBinding.imageView4.setBackgroundResource(R.drawable.ic_right)
            }
            else if(drugsResponseItem.orderStatus==3)
            {
                DrugRowBinding.divider.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_primary
                    )
                )
                DrugRowBinding.divider2.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_primary
                    )
                )
                DrugRowBinding.imageView2.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                DrugRowBinding.imageView3.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
                DrugRowBinding.imageView4.setBackgroundResource(R.drawable.ic_baseline_check_circle_24)
            }

        }
    }

    fun addDrugs(DrugsResponseItem: List<DrugDetailsModel>) {
        DrugsResponseItemList= arrayListOf()
        DrugsResponseItemList.clear()
        DrugsResponseItemList.addAll(DrugsResponseItem)
        this.notifyDataSetChanged()
    }
}