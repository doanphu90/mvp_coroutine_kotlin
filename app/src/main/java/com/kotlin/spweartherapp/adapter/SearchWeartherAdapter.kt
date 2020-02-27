package com.kotlin.spweartherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.spweartherapp.Interface.IOnClickListenItem
import com.kotlin.spweartherapp.R
import com.kotlin.spweartherapp.mvp.model.ResultSearch
import kotlinx.android.synthetic.main.item_layout_result_search.view.*

class SearchWeartherAdapter(
    private var listSearch: MutableList<ResultSearch>,
    private var onClickItem: IOnClickListenItem
) : RecyclerView.Adapter<SearchWeartherAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View, private val itemClick: IOnClickListenItem) :
        RecyclerView.ViewHolder(itemView) {
        fun binData(item: ResultSearch) {
            itemView.tv_city.text = item.areaName
            itemView.tv_country.text = item.country
            itemView.ll_item.setOnClickListener { itemClick.onClickItemListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_result_search, parent, false)
        return SearchViewHolder(v, onClickItem)
    }

    override fun getItemCount(): Int {
//        return listSearch.size
        return Math.min(listSearch.size, 10)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binData(listSearch.get(position))
    }

    fun updateData(liUpdate: List<ResultSearch>) {
        listSearch.clear()
        listSearch.addAll(liUpdate)
        notifyDataSetChanged()
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                var chars: String = constraint.toString()
//                if (chars.isNotEmpty()) {
//                    var filterList: MutableList<ResultSearch> = mutableListOf()
//                    for (item in listSearch) {
//                        if (item.areaName.toLowerCase().contains(chars.toLowerCase())) {
//                            filterList.add(item)
//                        }
//                    }
//                    listSearch = filterList
//                }
//                var filterResult = FilterResults()
//                filterResult.values = listSearch
//                return filterResult
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                listSearch = results!!.values as MutableList<ResultSearch>
//                notifyDataSetChanged()
//            }
//        }
//    }
}