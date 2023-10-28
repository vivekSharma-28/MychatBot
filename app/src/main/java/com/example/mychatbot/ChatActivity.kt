package com.example.mychatbot

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychatbot.adaptor.MessageAdaptor
import com.example.mychatbot.api.ApiUtilities
import com.example.mychatbot.databinding.ActivityChatBinding
import com.example.mychatbot.model.MessageModel
import com.example.mychatbot.model.request.ChatRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private var list = ArrayList<MessageModel>()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adaptor: MessageAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        adaptor = MessageAdaptor(list)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager=layoutManager

        binding.backBtn.setOnClickListener { finish() }

        binding.sendbtn.setOnClickListener {
            if (binding.userMsg.text!!.isEmpty()) {
                Toast.makeText(this, "PLease ask your question", Toast.LENGTH_LONG).show()
            } else {
                callApi()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun callApi() {
        list.add(
            MessageModel(
                isUser = true, isImage = false, message = binding.userMsg.text.toString()
            )
        )

        adaptor.notifyItemChanged(list.size - 1)

        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size - 1)

        val apiInterface = ApiUtilities.getApiInterface()
        val requestBody = RequestBody.create(
            MediaType.parse("application/json"), Gson().toJson(
                ChatRequest(
                    250, "text-davinci-003", binding.userMsg.text.toString(), 0.7
                )
            )
        )

        val contentType = "application/json"
        val authorization = "Bearer ${Utils.Api_Key}"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface.getChat(
                    contentType, authorization, requestBody
                )
                val textResponse = response.choices.first().text
                list.add(MessageModel(isUser = false, isImage = false, message = textResponse))

                withContext(Dispatchers.Main) {
                    adaptor.notifyItemChanged(list.size - 1)
                    binding.recyclerView.recycledViewPool.clear()
                    binding.recyclerView.smoothScrollToPosition(list.size - 1)
                }
                binding.userMsg.text!!.clear()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ChatActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}