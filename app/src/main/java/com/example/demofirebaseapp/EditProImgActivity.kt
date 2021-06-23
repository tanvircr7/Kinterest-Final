package com.example.demofirebaseapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_edit_pro_img.*
import kotlinx.android.synthetic.main.activity_image_firebase.*
import kotlinx.android.synthetic.main.activity_upload.*
import java.util.*
import kotlin.collections.HashMap


class EditProImgActivity : AppCompatActivity() {

    private var uri: Uri? = Uri.parse(" ")
    private var myUri: String = " "
    lateinit private var databaseReference: DatabaseReference
    lateinit private var storageReference: StorageReference
    lateinit private var uploadTask: StorageTask<UploadTask.TaskSnapshot>
    lateinit private var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pro_img)

        auth = FirebaseAuth.getInstance()
        //  Users may change WARNING!!!
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pic")

        closeProImgBtn.setOnClickListener {
            Intent(this,ProfileActivity::class.java).also{
                startActivity(it)
            }
        }

        saveProImgBtn.setOnClickListener {
            uploadProfileImage()
        }

        profileChangeBtn.setOnClickListener {
//            CropImage.activity().setAspectRatio(1,1).start(this)
            CropImage.activity()
                .setAspectRatio(1,1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
        }

        getUserinfo()

    }

    private fun getUserinfo() {
        databaseReference.child(auth.currentUser!!.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists() && (snapshot.childrenCount > 0)){
                    if(snapshot.hasChild("image")){
                        val image = snapshot.child("image").getValue().toString()
                        Picasso.with(applicationContext).load(image).into(dpImg)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Error :: Data Retrieving Failed!!",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
//            editProfileImg.setImageURI(result.uri)
            if (resultCode === RESULT_OK) {
                uri = result.uri
                dpImg.setImageURI(uri)
            } else if (resultCode === CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun uploadProfileImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("set your profile pic")
        progressDialog.setMessage("Please, wait")
        progressDialog.show()

        if(uri != null){
            val fileRef = storageReference.child(auth.currentUser!!.uid+".jpg")
            uploadTask = fileRef.putFile(uri!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { exception ->
                        throw exception
                    }
                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener {
                if(it.isSuccessful){
                    val downloadUrl: Uri? = it.getResult() // what does getResult do? exactly?
                    myUri  = downloadUrl.toString()

                    var useMap = HashMap<String, Any>()
                    useMap.put("image", myUri)

                    databaseReference.child(auth.currentUser!!.uid).updateChildren(useMap)
                    progressDialog.dismiss()
                }
            }
        }
        else {
            Toast.makeText(this, "Image not select", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }
}