package com.iutorsay.recipesapplication

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Stephane on 16/12/2018
 */

class AdapterListHomePage(private val userRecipeList: ArrayList<Recipe>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        //? pour gerer le null
        val view = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_home, parent,false);
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        //retourne le nombre d'elements
        return userRecipeList.size;
    }

    //associer chaque elements a une vue
    override fun onBindViewHolder(holder: ViewHolder, positionItem: Int) {
        val itemRecipe: Recipe = userRecipeList[positionItem] //element de la list
        val txtNameItem = holder?.itemView.findViewById(R.id.itemListHomeName) as TextView;

        txtNameItem.text = itemRecipe.name;
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    //val txtNameItem = itemView.findViewById(R.id.itemListHomeName) as TextView;

    init {
        itemView.setOnClickListener {
            println("Test");
            val intent = Intent(itemView.context, MainActivity::class.java);
            itemView.context.startActivity(intent);
        }
    }
}
