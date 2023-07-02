package com.example.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleview.model.CatUiModel
import com.example.recycleview.model.ListItemUiModel
import com.example.recycleview.viewholder.CatViewHolder
import com.example.recycleview.viewholder.ListItemViewHolder
import com.example.recycleview.viewholder.TitleViewHolder

private const val VIEW_TYPE_TITLE = 0
private const val VIEW_TYPE_CAT = 1

class ListItemsAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: GlideImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<ListItemViewHolder>() {
    val swipeToDeleteCallback = SwipeToDeleteCallback()

    // this is the list of cats that we will display
    private val listData = mutableListOf<ListItemUiModel>()
    fun setData(newListData: List<ListItemUiModel>) {
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun addItem(position: Int, item: ListItemUiModel) {
        listData.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_TITLE -> {
            val view = layoutInflater.inflate(R.layout.item_title, parent, false)
            TitleViewHolder(view)
        }

        VIEW_TYPE_CAT -> {
            val view = layoutInflater.inflate(R.layout.item_cat, parent, false)
            CatViewHolder(
                view,
                imageLoader,
                object : CatViewHolder.OnClickListener {
                    override fun onClick(catData: CatUiModel) =
                        onClickListener.onItemClick(catData)
                })
        }

        else -> throw IllegalArgumentException("Unknown view type requested: $viewType")
    }


    override fun getItemCount() = listData.size

    // this is where we determine which view type to use
    override fun getItemViewType(position: Int) = when (listData[position]) {
        is ListItemUiModel.Title -> VIEW_TYPE_TITLE
        is ListItemUiModel.Cat -> VIEW_TYPE_CAT
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    interface OnClickListener {
        fun onItemClick(catData: CatUiModel)
    }

    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) = if (viewHolder is CatViewHolder) {
            makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) or makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
        } else {
            0
        }

        override fun onSwiped(
            viewHolder:
            RecyclerView.ViewHolder, direction: Int
        ) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }
    }
}
