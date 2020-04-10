package com.ie.lenda.datastructure.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ie.lenda.datastructure.list.DataStructureListFragmentDirections
import com.ie.lenda.R

class DataStructureListFragment : Fragment() {
    private lateinit var dataStructureList:Set<HashMap<String,Any>>

    private lateinit var dataStructureListView:RecyclerView
    private lateinit var dataStructureListAdapter:DataStructureListAdapter
    private val viewModel by viewModels<DataStructureListViewModel>()

    private fun setupData(){
        dataStructureList = setOf(
            hashMapOf<String,Any>(
                getString(R.string.ds_name_map_key) to "Array"
            ),
            hashMapOf(
                getString(R.string.ds_name_map_key) to "Trees",
                getString(R.string.ds_children_map_key) to setOf(
                    hashMapOf(
                        getString(R.string.ds_name_map_key) to "Binary Tree"
                    )
                )
            )
        )
    }
    private fun setupRecyclerView(root:View){
        val layoutManager = LinearLayoutManager(context)
        dataStructureListAdapter = DataStructureListAdapter(dataStructureList,viewModel)
        dataStructureListView = root.findViewById(R.id.data_structures)
        dataStructureListView.layoutManager = layoutManager
        dataStructureListView.adapter = dataStructureListAdapter
    }

    private fun navigateToDataStructure(name:String){
        findNavController().navigate(
            DataStructureListFragmentDirections.actionHomeToDataStructureFragment(
                name
            )
        )
    }

    private fun setupObserver(){
        viewModel.navigateToDataStructure.observe(
            viewLifecycleOwner, Observer {
                it.getContent()?.let {
                    name -> navigateToDataStructure(name)
                }
            })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_data_structure_list, container, false)
        setupObserver()
        setupData()
        setupRecyclerView(root)
        return root
    }

}
