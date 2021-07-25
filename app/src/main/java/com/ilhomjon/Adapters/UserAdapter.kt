package com.ilhomjon.Adapters

import Entity.User
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.rxkotlin.databinding.ItemRvBinding

class UserAdapter(var onItemCLickListener: OnItemCLickListener):ListAdapter<User, UserAdapter.Vh>(MyDiffUtil()) {

    inner class Vh(var itemRv: ItemRvBinding):RecyclerView.ViewHolder(itemRv.root){

        fun onBind(user: User){
            itemRv.tv1.text = user.userName
            itemRv.tv2.text = user.password

            itemRv.root.setOnClickListener {
                onItemCLickListener.onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyDiffUtil:DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }

    }

    interface OnItemCLickListener{
        fun onItemClick(user: User)
    }

}