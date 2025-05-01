package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.users.User
import com.schooldiary.fragment.BottomSheetFragment2

class UsersAdapter(
    private val items: List<User>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    private var onClick: ((Int) -> Unit)? = null

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userRole: TextView = itemView.findViewById(R.id.user_role)
        val className: TextView = itemView.findViewById(R.id.class_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val item = items[position]
        holder.userName.text = item.name
        holder.userRole.text = item.roles[0]
        holder.itemView.setOnClickListener {
            onClick?.let { it(position) }
            val bottomSheet = BottomSheetFragment2()
            bottomSheet.show(fragmentManager, bottomSheet.tag)
        }
    }

    override fun getItemCount() = items.size

    fun setOnclickListener(action: (Int) -> Unit) {
        onClick = action
    }
}
