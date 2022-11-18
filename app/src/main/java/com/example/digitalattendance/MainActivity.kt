package com.example.digitalattendance

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {


    private var filePath: Uri? = null
    private var photoFile: Uri? = null
    lateinit var currentPhotoPath: String
    private var mydownload : Long = 0

    companion object {
        const val FILE_NAME = "photo"
        const val REQUEST_FROM_CAMERA = 1;
        const val ReQUEST_FROM_GALLERY = 2;
        const val REQUEST_PERMISSION = 3;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        initUI()


    }


    private fun initUI() {
        val cap = findViewById<View>(R.id.upload) as Button
        val gall = findViewById<View>(R.id.galllery) as Button
        val dow = findViewById<View>(R.id.download) as Button

        cap.setOnClickListener {
            captureImageUsingCamera()

        }
        gall.setOnClickListener {
            pickImageFromGallery()
        }
        dow.setOnClickListener {
            var requst = DownloadManager.Request(
                    Uri.parse("https://firebasestorage.googleapis.com/v0/b/digital-attendance-10221.appspot.com/o/sheet%2Fattendance.csv?alt=media&token=7c7d410f-e064-45a8-a469-8c0351792f16")).setTitle("AttendanceSheet")
                    .setDescription("Attendance Sheet Downloading").setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE).setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Attendance Sheet.csv")


            var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            mydownload = dm.enqueue(requst)
        }

        var br = object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == mydownload){
                    Toast.makeText(applicationContext, "Check your attendance sheet in your downloads..", Toast.LENGTH_LONG).show()
                }
            }
        }
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    @Throws(IOException::class)
    private fun createCapturedPhoto(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_${timestamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }


    }






    private fun pickImageFromGallery() {

        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, ReQUEST_FROM_GALLERY)
            }


        }

    }



    private fun captureImageUsingCamera() {
        Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE).also { cameraa ->
            cameraa.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createCapturedPhoto()
                } catch (ex: IOException) {
                    // If there is error while creating the File, it will be null
                    null
                }
                photoFile?.also {
                    val fileProvider = FileProvider.getUriForFile(
                            this,
                            "${BuildConfig.APPLICATION_ID}.fileprovider",
                            it
                    )
                    cameraa.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
                    startActivityForResult(cameraa, REQUEST_FROM_CAMERA)
                }
            }
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imgProfile = findViewById<View>(R.id.imageView) as ImageView


        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_FROM_CAMERA -> {
                    val uri = Uri.parse(currentPhotoPath)

                    imgProfile.setImageURI(uri)
                    // val imageBitmap = data?.extras?.get("data") as Bitmap
                    //val imageBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                    // imgProfile.setImageBitmap(imageBitmap)

                    //var pi: Bitmap? = data?.getParcelableExtra<Bitmap>("data")
                    //imgProfile.setImageBitmap(pi)
                    //imgProfile.setImageURI(captureImageUsingCamera(),data!!.data)

                    //imgProfile.setImageURI(data!!.data)

                    //FirebaseStorageManager().uploadImage(this,data.data!!)


                }

                ReQUEST_FROM_GALLERY -> {
                    filePath = data!!.data
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                    imgProfile.setImageBitmap(bitmap)

                    FirebaseStorageManager().uploadImage(this, data.data!!)
                    val mTextField = findViewById<View>(R.id.textt) as TextView
                    object : CountDownTimer(60000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            mTextField.setText("seconds remaining: " + millisUntilFinished / 1000)
                        }

                        override fun onFinish() {
                            mTextField.setText("done!")
                        }
                    }.start()

                }
            }
        }
    }
}



