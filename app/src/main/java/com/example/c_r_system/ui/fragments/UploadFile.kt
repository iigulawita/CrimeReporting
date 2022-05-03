package com.example.c_r_system.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import coil.load
import com.example.c_r_system.databinding.FragmentUploadFileBinding
import com.example.c_r_system.ui.activities.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.*


class UploadFile : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentUploadFileBinding? = null
    private val binding get() = _binding!!
    private val PICK_IMAGE_REQUEST = 1


    private lateinit var mainActivity: MainActivity
    private val items = arrayOf("", "Galle", "Colombo", "Uganda")
    private var mImageUri: Uri? = null

    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mUploadTask: StorageTask<*>? = null
    private var city:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadFileBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mainActivity = activity as MainActivity
        mainActivity.hideFabBottomAppBar()
        binding.buttonChooseImage.setOnClickListener {
            openFileChooser()
        }
        binding.buttonUpload.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress()) {
                Toast.makeText(requireContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile()
            }
        }
        setupSpinner()
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            /* Log.d("UploadFile", mImageUri.toString())
           mImageView?.setImageURI(mImageUri)*/
            binding.imageView.load(mImageUri) {
                crossfade(false)
                crossfade(1000)
            }
        }
    }


    private fun getFileExtension(uri: Uri): String? {
        val cR: ContentResolver = (requireContext().contentResolver as ContentResolver)
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        if (mImageUri != null) {
            val fileReference = mStorageRef!!.child(
                System.currentTimeMillis()
                    .toString() + "." + getFileExtension(mImageUri!!)
            )
            mUploadTask = fileReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->

                    if(city == ""){
                        Toast.makeText(context, "Please select a city", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val handler = Handler()
                        handler.postDelayed(Runnable { binding.progressBar.progress = 0 }, 500)

                        val upload = Upload(
                            binding.editTextFileName.text.toString().trim { it <= ' ' },
                            taskSnapshot.uploadSessionUri.toString(),
                            city
                        )
                        val uploadId = mDatabaseRef!!.push().key
                        mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                        Toast.makeText(requireContext(), "Upload successful", Toast.LENGTH_LONG).show()

                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    binding.progressBar.progress = progress.toInt()
                }
        } else {
            Toast.makeText(requireContext(), "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinner() {


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        )
        binding.spinner1.adapter = adapter
        binding.spinner1.setOnItemSelectedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity.showFabBottomAppBar()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       
        when(position){
            0 -> city = items[position]
            1 -> city = items[position]
            2 -> city = items[position]
            3 -> city = items[position]
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
//        Toast.makeText(context, "Please select a city", Toast.LENGTH_SHORT).show()
    }

}






