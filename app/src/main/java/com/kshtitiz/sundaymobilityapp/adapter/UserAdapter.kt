package com.kshtitiz.sundaymobilityapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kshtitiz.sundaymobilityapp.R
import com.kshtitiz.sundaymobilityapp.adapter.UserAdapter.MyViewHolder
import com.kshtitiz.sundaymobilityapp.modal.User

class UserAdapter(var userList: MutableList<User>, private val listener: ClickListener) :
    RecyclerView.Adapter<MyViewHolder>() {


    //as for attached to the class , can be called like static
    companion object {
        var mClickListener: ClickListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    fun addItem(user: User) {
        userList.add(user)
        notifyDataSetChanged()
    }


    fun removeUserAtPosition(position: Int) {
        userList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.userName.text = this.userList.get(index = position).login
        holder.userType.text = this.userList.get(index = position).type
        Glide.with(holder.itemView).load(this.userList.get(index = position).avatar_url)
            .centerCrop().placeholder(R.mipmap.ic_launcher).error(
                R.drawable.ic_error_image
            )
            .fallback(R.mipmap.ic_launcher).into(holder.imageUser)

        mClickListener = listener
        holder.imageUser.setOnClickListener {
            mClickListener!!.clickRow(this.userList.get(index = position), position)
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.tv_user)
        val imageUser: ImageView = view.findViewById(R.id.iv_user_image)
        val userType: TextView = view.findViewById(R.id.tv_user_type)
    }

    interface ClickListener {
        fun clickRow(user: User, position: Int)
    }

}