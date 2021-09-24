package com.example.recipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.model.FlavorEnum
import com.example.recipes.model.FlavorUiModel
import com.example.recipes.model.ListItemUiModel
import com.example.recipes.model.RecipeUiModel

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {findViewById(R.id.recycler_view)}

    private val listItemsAdapter by lazy { ListItemsAdapter(layoutInflater,
                    object : ListItemsAdapter.OnClickListener {
                        override fun onItemClick(listData: RecipeUiModel) {
                            showSelectionDialog(listData) } }) }

    private val addSavoryItemButton: View by lazy {findViewById(R.id.main_add_savory_button)}
    private val addSweetItemButton: View by lazy {findViewById(R.id.main_add_sweet_button)}

    private val recipeTitleView: TextView by lazy { findViewById(R.id.recipe_title)}
    private val recipeDescriptionView: TextView by lazy { findViewById(R.id.recipe_description)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.adapter = listItemsAdapter
        recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val itemTouchHelper = ItemTouchHelper(listItemsAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView((recyclerView))

        addSavoryItemButton.setOnClickListener {
            addRecipeAndClearForm(FlavorEnum.SAVORY)
        }

        addSweetItemButton.setOnClickListener {
            addRecipeAndClearForm(FlavorEnum.SWEET)
        }


        listItemsAdapter.setData(
                listOf(
                        ListItemUiModel.Flavor(
                                FlavorUiModel(
                                    FlavorEnum.SAVORY)
                        ),
                        ListItemUiModel.Recipe(
                                RecipeUiModel(
                                        FlavorEnum.SAVORY,
                                        "Chile",
                                        "beans, peppers and deer meat"
                                )
                        ),
                        ListItemUiModel.Recipe(
                                RecipeUiModel(
                                        FlavorEnum.SAVORY,
                                        "Sagadina Goulash",
                                        "pork, potatoes, carrots, sourkraut and sour cream"
                                )
                        ),
                        ListItemUiModel.Flavor(
                                FlavorUiModel(
                                        FlavorEnum.SWEET)
                        ),
                        ListItemUiModel.Recipe(
                                RecipeUiModel(
                                        FlavorEnum.SWEET,
                                        "Carrot Cake",
                                        "flour, eggs, carrots, walnuts, cream cheese, sugar"
                                )
                        )

                )
        )
    }

    private fun showSelectionDialog(listData: RecipeUiModel) {
        AlertDialog.Builder(this)
                .setTitle("${listData.title} Directions")
                .setMessage("You will need the following ingredients:  ${listData.recipe}")
                .setPositiveButton("OK") { _, _ -> }
                .show()
    }

    private fun addRecipeAndClearForm(flavor : FlavorEnum) {
        val recipe_title = recipeTitleView.text.toString().trim()
        val recipe_description = recipeDescriptionView.text.toString().trim()
        if (recipe_title.isEmpty() || recipe_description.isEmpty()) return

        listItemsAdapter.addItem(flavor, 1,
                ListItemUiModel.Recipe (RecipeUiModel(
                        flavor,
                        recipe_title,
                        recipe_description)
                ),
        )

        recipeTitleView.text = ""
        recipeDescriptionView.text = ""
    }




}
