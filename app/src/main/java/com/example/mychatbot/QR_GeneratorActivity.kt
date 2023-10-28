package com.example.mychatbot

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mychatbot.databinding.ActivityQrGeneratorBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QR_GeneratorActivity : AppCompatActivity() {
    private lateinit var binding:ActivityQrGeneratorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQrGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.QrButton.setOnClickListener {
            val data = binding.editText.text.toString().trim()

            if(data.isEmpty())
            {
                Toast.makeText(this,"Enter some data",Toast.LENGTH_LONG).show()
            }else{
                val writer=QRCodeWriter()
                try {
                    val bitMat=writer.encode(data, BarcodeFormat.QR_CODE,512,512)
                    val width=bitMat.width
                    val height=bitMat.height
                    val bmp=Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
                    for (x in 0 until width){
                        for(y in 0 until height)
                        {
                            bmp.setPixel(x,y,if(bitMat[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    binding.QRCode.setImageBitmap(bmp)
                }catch (e: WriterException){
                    e.printStackTrace()
                }
            }
        }
    }
}