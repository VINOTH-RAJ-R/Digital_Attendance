package com.example.digitalattendance

import android.app.ProgressDialog
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage

class FirebaseStorageManager {
    //private val mStorageRef = FirebaseStorage.getInstance().reference
    private lateinit var mProgressDialog: ProgressDialog
    private val TAG = "FirebaseStroageManager"
    private lateinit var filePath: Uri
    fun uploadImage(mContext: MainActivity, imageUri: Uri) {



            mProgressDialog = ProgressDialog(mContext)
            mProgressDialog.setTitle("Uploading")
            mProgressDialog.show()

        val uploadTask = FirebaseStorage.getInstance().reference.child("Attendance/image")
            if (uploadTask != null) {
                uploadTask.putFile(imageUri).addOnSuccessListener { p0 ->
                    mProgressDialog.dismiss()
                    Toast.makeText(mContext, "Uploaded, wait for 60second and download the sheet", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { p0 ->
                    mProgressDialog.dismiss()
                    Toast.makeText(mContext, p0.message, Toast.LENGTH_LONG)

                }.addOnProgressListener { p0 ->
                    var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                    mProgressDialog.setMessage("Uploaded ${progress.toInt()}%")


                }


            }

    }

}
