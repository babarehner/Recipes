package com.example.recipes

import android.view.View
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.fragment.app.FragmentContainerView
import com.example.recipes.model.ListItemUiModel
import com.example.recipes.model.RecipeUiModel


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

class RecipeViewHolder (private val containerView: View,
                        private val onClickListener: OnClickListener) :
                        ListItemViewHolder(containerView) {

    private val titleView: TextView by lazy {containerView.findViewById(R.id.item_recipe_title)}

    override fun bindData(listItem: ListItemUiModel) {
        // Not sure why this is required ?? in this syntax??
        require(listItem is ListItemUiModel.Recipe) { "Expected ListIemUiModel.Recipe"}

        // titleViw.text = listItem.recipe.title
        val recipeData = listItem.recipe

        containerView.setOnClickListener { onClickListener.onClick( recipeData )}

        titleView.text = recipeData.title

    }

    // nested interface
    interface OnClickListener {
        fun onClick(recipeData: RecipeUiModel)
    }
}