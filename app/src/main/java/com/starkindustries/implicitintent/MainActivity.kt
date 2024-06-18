package com.starkindustries.implicitintent
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.starkindustries.implicitintent.databinding.ActivityMainBinding
import java.util.Arrays
class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.callButton.setOnClickListener()
        {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.setData(Uri.parse("tel: "+binding.phoneNo.toString().trim()))
            startActivity(callIntent)
        }
        binding.messageButton.setOnClickListener()
        {
            val messageInent = Intent(Intent.ACTION_SENDTO)
            messageInent.setData(Uri.parse("smsto:"+Uri.encode(binding.phoneNo.text.toString().trim())))
            messageInent.putExtra(Intent.EXTRA_TEXT,binding.message.toString().trim())
            startActivity(messageInent)
        }
        binding.emailButton.setOnClickListener(){
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.setType("message/rfc882")
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,binding.emailSubject.text.toString().trim())
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.email.text.toString().trim()))
            emailIntent.putExtra(Intent.EXTRA_TEXT,binding.emailMessage.text.toString().trim())
            startActivity(Intent.createChooser(emailIntent,"Open With"))
        }
        binding.sendButton.setOnClickListener()
        {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.setType("text/plain")
            sendIntent.putExtra(Intent.EXTRA_TEXT,binding.message.text.toString().trim())
            startActivity(Intent.createChooser(sendIntent,"Share With"))
        }
        binding.cameraButton.setOnClickListener()
        {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera,101)
        }
        binding.galleryButton.setOnClickListener()
        {
         val gallery = Intent(Intent.ACTION_PICK)
         gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         startActivityForResult(gallery,102)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK)
        {
            if(requestCode==101)
            {
                val bitmappImage:Bitmap = data?.getExtras()?.get("data") as Bitmap
                binding.imageView.setImageBitmap(bitmappImage)
            }
            if(requestCode==102)
            {
                var imageUri: Uri? = data?.getData()
                binding.imageView.setImageURI(imageUri)
            }
        }
    }
}