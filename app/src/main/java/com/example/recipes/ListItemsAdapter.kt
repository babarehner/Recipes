package com.example.recipes

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.model.ListItemUiModel
import com.example.recipes.model.RecipeUiModel
import com.example.recipes.RecipeViewHolder
import com.example.recipes.model.FlavorEnum


/**
 * Project Name: Recipes
 *
 * Copyright 9/23/21 by Mike Rehner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

private const val VIEW_TYPE_FLAVOR = 0
private const val VIEW_TYPE_RECIPE = 1

class ListItemsAdapter( private val layoutInflater: LayoutInflater,
                        private val onClickListener: OnClickListener) :
        RecyclerView.Adapter<ListItemViewHolder>() {

    val swipeToDeleteCallback = SwipeToDeleteCallback()

    private val listData = mutableListOf<ListItemUiModel>()

    // why use when??
    override fun getItemViewType(position: Int) = when (listData[position]){
                                                    is ListItemUiModel.Flavor -> VIEW_TYPE_FLAVOR
                                                    is ListItemUiModel.Recipe -> VIEW_TYPE_RECIPE
    }


    fun setData(listData : List<ListItemUiModel>) {
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    fun addItem(flavor: FlavorEnum, position : Int, item: ListItemUiModel) {
        //THIS 'require' IS A WAY OF GETTING AROUND 'NESTED CLASS' ACCESSED via INSTANCE REFERENCE
        // My UI classes are inside a sealed ListItemUiModel class
//        require(item is ListItemUiModel.Recipe) {"Expected ListItemUiModel.recipe"}
//        val insertionIndex = listData.indexOf (when (item.recipe.flavor ) {
//            FlavorEnum.SWEET ->  "Sweet"
//            FlavorEnum.SAVORY -> "Savory"
//            FlavorEnum.NotSure -> 3
//        })
        // crashes here because insertionIndex does not pick up the index of 'Sweet' or 'Savory'
        val insertionIndex = 1      // temp to get program running
        listData.add(insertionIndex, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder =
            when (viewType) {
                VIEW_TYPE_FLAVOR -> { val view = layoutInflater.inflate(R.layout.item_flavor,
                        parent, false)
                FlavorViewHolder(view)}
                VIEW_TYPE_RECIPE -> { val view = layoutInflater.inflate(R.layout.item_recipe,
                        parent, false)
                RecipeViewHolder(view,
                        object : RecipeViewHolder.OnClickListener {
                            override fun onClick(recipeData: RecipeUiModel) =
                                    onClickListener.onItemClick(recipeData)
                        })}
                else -> throw IllegalAccessException("Unknown view type requested: $viewType")
            }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    interface OnClickListener {
        fun onItemClick(listData: RecipeUiModel)
    }

    // Swiping action- also attach to recylerAdapter in Main
    inner class SwipeToDeleteCallback:
            ItemTouchHelper.SimpleCallback(
                    0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder): Boolean = false

        override fun getMovementFlags(recyclerView: RecyclerView,
                                      viewHolder: RecyclerView.ViewHolder) =
                if (viewHolder is RecipeViewHolder) {
                    makeMovementFlags(
                            ItemTouchHelper.ACTION_STATE_IDLE,
                            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    )or makeMovementFlags (
                            ItemTouchHelper.ACTION_STATE_SWIPE,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
                } else { 0 }

            override  fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                removeItem(position)
        }

    }

}