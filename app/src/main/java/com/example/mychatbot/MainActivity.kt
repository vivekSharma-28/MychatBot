package com.example.mychatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mychatbot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.generateImage.setOnClickListener {
            startActivity(Intent(this,ImageGenerateActivity::class.java))
        }

        binding.chatWithBot.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }

        binding.QRScanner.setOnClickListener{
            startActivity(Intent(this,QRReaderActivity::class.java))
        }

        binding.QRGenerator.setOnClickListener {
            startActivity(Intent(this,QR_GeneratorActivity::class.java))
        }

    }
}