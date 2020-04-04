package com.ie.lenda.datastructure.list

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ie.lenda.R
import com.ie.lenda.util.Event

class DataStructureListAdapter(
    private val dataStructureList:Set<HashMap<String,Any>> = emptySet(),
    private val viewModel:DataStructureListViewModel
): RecyclerView.Adapter<DataStructureListAdapter.DataStructureListAdapterViewHolder>() {

    class DataStructureListAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val dataStructureNameTextView: TextView = itemView.findViewById(R.id.data_structure_group_name)
        val expandCollapseIcon: AppCompatImageView = itemView.findViewById(R.id.expand_collapse_icon)
        val dataStructureChildListView:RecyclerView = itemView.findViewById(R.id.data_structure_list)
        val clickArea:RelativeLayout = itemView.findViewById(R.id.click_area)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataStructureListAdapterViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val dataStructureListItemView = inflater.inflate(R.layout.data_structure_list_item,parent,false)
        return DataStructureListAdapterViewHolder(dataStructureListItemView)
    }

    override fun getItemCount(): Int = dataStructureList.size

    private fun setupChildren(dataStructureChildListView:RecyclerView, dataStructureChildren:Set<HashMap<String,Any>>){
            val context = dataStructureChildListView.context
            val adapter = DataStructureListAdapter(dataStructureChildren,viewModel)
            val layoutManager = LinearLayoutManager(context)
            dataStructureChildListView.layoutManager = layoutManager
            dataStructureChildListView.adapter = adapter
    }

    private fun showHideChildren(holder: DataStructureListAdapterViewHolder){
            when(holder.dataStructureChildListView.visibility){
                View.VISIBLE -> {
                    ObjectAnimator.ofFloat(holder.expandCollapseIcon,View.ROTATION,180f,360f).apply {
                        duration = 300
                        repeatMode = ObjectAnimator.REVERSE
                        start()
                    }
                    holder.dataStructureChildListView.visibility = View.GONE
                }
                View.GONE -> {
                    ObjectAnimator.ofFloat(holder.expandCollapseIcon,View.ROTATION,180f).apply {
                        duration = 300
                        repeatMode = ObjectAnimator.REVERSE
                        start()
                    }
                    holder.dataStructureChildListView.visibility = View.VISIBLE
                }
            }


    }

    private fun handleClicks(dataStructure:HashMap<String,Any>,holder: DataStructureListAdapterViewHolder){
        holder.clickArea.setOnClickListener {
            if(dataStructure.containsKey("dataStructures")){
                showHideChildren(holder)
              return@setOnClickListener
            }
            viewModel.navigateToDataStructure.value = Event(dataStructure["name"] as String)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: DataStructureListAdapterViewHolder, position: Int) {
        dataStructureList.elementAt(position).let {
            holder.dataStructureNameTextView.text = it["name"] as String
            if(it.containsKey("dataStructures")) {
                holder.expandCollapseIcon.visibility = View.VISIBLE
                setupChildren(
                    holder.dataStructureChildListView,
                    it["dataStructures"] as Set<HashMap<String, Any>>
                )
            }
            handleClicks(it,holder)
        }
    }
}