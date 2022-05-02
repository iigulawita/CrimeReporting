package com.example.c_r_system.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import coil.load
import com.example.c_r_system.databinding.FragmentUploadFileBinding
import com.example.c_r_system.ui.activities.MainActivity
import java.io.File


class UploadFile : Fragment(){

    private  var mImageView: ImageView? = null
    private var _binding: FragmentUploadFileBinding? = null
    private val binding get() = _binding!!
    private val PICK_IMAGE_REQUEST = 1

    private val mButtonChooseImage: Button? = null
    private val mButtonUpload: Button? = null
    private val mTextViewShowUploads: TextView? = null
    private val mEditTextFileName: EditText? = null
    private lateinit var mainActivity: MainActivity

    private val mProgressBar: ProgressBar? = null
    private lateinit var mImageUri: Uri


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadFileBinding.inflate(inflater, container, false)
        init()

        return binding.root
    }

    private fun init() {
        mainActivity = activity as MainActivity
        mainActivity.hideFabBottomAppBar()
        mImageView = ImageView(context)
        binding.buttonChooseImage.setOnClickListener {
            openFileChooser()
        }



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


         override fun onDestroyView() {
             super.onDestroyView()
             mainActivity.showFabBottomAppBar()
             _binding = null
         }

     }


