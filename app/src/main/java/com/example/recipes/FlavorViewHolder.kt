package com.example.recipes

import android.view.View
import android.widget.TextView
import com.example.recipes.model.FlavorEnum


import com.example.recipes.model.ListItemUiModel


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

class FlavorViewHolder(containerView: View) : ListItemViewHolder (containerView) {

    private val flavorView: TextView by lazy {containerView.findViewById(R.id.item_flavor_heading)}

    override fun bindData(listItem: ListItemUiModel) {
        require(listItem is ListItemUiModel.Flavor) {"Expected ListItemUiModel.Flavor"}
        val flavorData = listItem.flavor_type

        flavorView.text = when (flavorData.flavor) {
            FlavorEnum.SAVORY -> "Savory"
            FlavorEnum.SWEET -> "Sweet"
            FlavorEnum.NotSure -> "Umknown type of flavor"
        }
    }
}