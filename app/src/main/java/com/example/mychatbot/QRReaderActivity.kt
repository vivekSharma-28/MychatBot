package com.example.mychatbot

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mychatbot.databinding.ActivityQrreaderBinding
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.io.InputStream


@Suppress("DEPRECATION")
class QRReaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrreaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrreaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appCompatButton.setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java))
        }

        binding.QRGallery.setOnClickListener {
            pickGallery()
        }

        val bundle = intent.extras
        if (bundle != null) {
            binding.textView.text = bundle.getString("data")
        }

    }


    private fun pickGallery() {
        val iGallery = Intent(Intent.ACTION_PICK)
        iGallery.type = "image/*"
        startActivityForResult(iGallery, Utils.Gallery_Code)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri = data?.data
                val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                try {
                    val contents: String?
                    val intArray = IntArray(selectedImage.width * selectedImage.height)
                    selectedImage.getPixels(
                        intArray,
                        0,
                        selectedImage.width,
                        0,
                        0,
                        selectedImage.width,
                        selectedImage.height
                    )
                    val source: LuminanceSource = RGBLuminanceSource(
                        selectedImage.width,
                        selectedImage.height,
                        intArray
                    )
                    val bitmap = BinaryBitmap(HybridBinarizer(source))
                    val reader = MultiFormatReader()
                    val result = reader.decode(bitmap)
                    contents = result.text
                    binding.textView.text=contents
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }
}