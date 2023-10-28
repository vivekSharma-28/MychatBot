package com.example.mychatbot.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mychatbot.R
import com.example.mychatbot.model.MessageModel


class MessageAdaptor(private val list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<MessageAdaptor.MessageViewHolder>() {

    inner class MessageViewHolder(view: View) : ViewHolder(view) {
        val msgText: TextView = view.findViewById(R.id.show_message)
        val imageContainer: LinearLayout? =view.findViewById(R.id.imageCard)
        val image: ImageView? = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: View?
        val from = LayoutInflater.from(parent.context)
        view = if (viewType == 0) from.inflate(R.layout.chatrightitem, parent, false)
        else from.inflate(R.layout.chatleftitem, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val message = list[position]
        return if (message.isUser) 0 else 1
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val message = list[position]

        if (!message.isUser) {
            holder.imageContainer?.visibility = View.GONE
        }

        if (message.isImage) {
            holder.imageContainer?.visibility = View.VISIBLE
            Glide.with(holder.itemView.context).load(message.message).into(holder.image!!)
        } else holder.msgText.text = message.message

    }

    override fun getItemCount(): Int {
        return list.size
    }
}