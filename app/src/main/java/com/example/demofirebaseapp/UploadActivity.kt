package com.example.demofirebaseapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {

    private val IMAGE_REQUEST = 1
    private var uri: Uri? = Uri.parse(" ")
    lateinit private var databaseReference: DatabaseReference
    lateinit private var storageReference: StorageReference
    private lateinit var uploadTask: StorageTask<UploadTask.TaskSnapshot>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        databaseReference = FirebaseDatabase.getInstance().getReference("Upload")
        storageReference = FirebaseStorage.getInstance().getReference("Upload")

        chooseImgBtn.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it,IMAGE_REQUEST)
            }
        }

        saveImgBtn.setOnClickListener {
//            if (uploadTask.isInProgress) Toast.makeText(
//                applicationContext,
//                "Uploading in progress",
//                Toast.LENGTH_LONG
//            ).show()

                val imageName = textInputEdittext.text.toString().trim()

                if (imageName.isEmpty()) {
                    textInputEdittext.setError("Enter the image name")
                    textInputEdittext.requestFocus()
                    return@setOnClickListener
                }

                val ref = storageReference.child(
                    System.currentTimeMillis().toString() + "." + uri?.let { it1 ->
                        getFileExtension(
                            it1
                        )
                    })
                val uploadTask = ref.putFile(uri!!)

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Toast.makeText(application, "image storing FAILED!!", Toast.LENGTH_LONG).show()
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(application, "image stored successfully", Toast.LENGTH_LONG)
                        .show()

                    val urlTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                    while (!urlTask.isSuccessful);

                    val downloadUrl: Uri? = urlTask.result

                    //val upload = Upload(imageName, downloadUrl.toString())
                    val upload = Upload()
                    upload.setImageName(imageName)
                    upload.setImageUrl(downloadUrl.toString())

                    val uploadId: String? = databaseReference.push().key
                    if (uploadId != null) {
                        databaseReference.child(uploadId).setValue(upload)
                    };

            }

        }

        displayImgBtn.setOnClickListener {
            Intent(this,ImageFirebaseActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode==IMAGE_REQUEST){
            uri = data?.data
            Picasso.with(this).load(uri).into(ivUpload);
        }
    }
    // Getting the extension of the image
    public fun getFileExtension(imageUri: Uri): String? {
        val contentResolver = getContentResolver()
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri))
    }

}