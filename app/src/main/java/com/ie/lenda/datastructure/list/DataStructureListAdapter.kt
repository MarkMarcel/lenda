package com.ie.lenda.datastructure.list

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ie.lenda.R

class DataStructureListAdapter(
    private val dataStructureList:Set<HashMap<String,Any>> = emptySet(),
    private val viewModel:DataStructureListViewModel
): RecyclerView.Adapter<DataStructureListAdapter.DataStructureListAdapterViewHolder>() {

    private lateinit var context:Context

    class DataStructureListAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val dataStructureNameTextView: TextView = itemView.findViewById(R.id.data_structure_group_name)
        val expandCollapseIcon: AppCompatImageView = itemView.findViewById(R.id.expand_collapse_icon)
        val dataStructureChildListView:RecyclerView = itemView.findViewById(R.id.data_structure_children)
        val clickArea:RelativeLayout = itemView.findViewById(R.id.click_area)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataStructureListAdapterViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val dataStructureListItemView = inflater.inflate(R.layout.data_structure_list_item,parent,false)
        return DataStructureListAdapterViewHolder(dataStructureListItemView)
    }

    override fun getItemCount(): Int = dataStructureList.size

    private fun setupChildren(dataStructureChildListView:RecyclerView, dataStructureChildren:Set<HashMap<String,Any>>){
            val adapter = DataStructureListAdapter(dataStructureChildren,viewModel)
            val layoutManager = LinearLayoutManager(context)
            dataStructureChildListView.layoutManager = layoutManager
            dataStructureChildListView.adapter = adapter
    }

    private fun showHideChildren(holder: DataStructureListAdapterViewHolder,animation:ObjectAnimator){
            when(holder.dataStructureChildListView.visibility){
                View.VISIBLE -> {
                    animation.reverse()
                    holder.dataStructureChildListView.visibility = View.GONE
                }
                View.GONE -> {
                    animation.start()
                    holder.dataStructureChildListView.visibility = View.VISIBLE
                }
            }


    }

    private fun handleClicks(dataStructure:HashMap<String,Any>,holder: DataStructureListAdapterViewHolder){
        val expandCollapseIconRotationAnimation = ObjectAnimator.ofFloat(holder.expandCollapseIcon,View.ROTATION,0f,-180f).apply {
            duration = 300
            repeatMode = ObjectAnimator.REVERSE
        }
        holder.clickArea.setOnClickListener {
            if(dataStructure.containsKey(context.getString(R.string.ds_children_map_key))){
                showHideChildren(holder,expandCollapseIconRotationAnimation)
                return@setOnClickListener
            }
            viewModel.learnDataStructure(dataStructure[context.getString(R.string.ds_name_map_key)] as String)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: DataStructureListAdapterViewHolder, position: Int) {
        dataStructureList.elementAt(position).let {
            holder.dataStructureNameTextView.text = it[context.getString(R.string.ds_name_map_key)] as String
            if(it.containsKey(context.getString(R.string.ds_children_map_key))) {
                holder.expandCollapseIcon.visibility = View.VISIBLE
                setupChildren(
                    holder.dataStructureChildListView,
                    it[context.getString(R.string.ds_children_map_key)] as Set<HashMap<String, Any>>
                )
            }
            handleClicks(it,holder)
        }
    }
}