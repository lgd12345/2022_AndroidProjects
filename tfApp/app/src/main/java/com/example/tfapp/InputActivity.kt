package com.example.tfapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.tfapp.databinding.ActivityInputBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class InputActivity : AppCompatActivity() {
    val getGalleryImage: Int = 200
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    lateinit var selectedImgUri: Uri
    private lateinit var binding: ActivityInputBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()

        binding.selectImg.setOnClickListener {
            val dlglogin = AlertDialog.Builder(this)

            dlglogin.setTitle("이미지 가져오기")

            dlglogin.setPositiveButton("사진촬영") { dialog, which ->
                startCapture()
            }
            dlglogin.setNegativeButton("앨범선택") { dlglogin, which ->
                var intent = Intent(Intent.ACTION_GET_CONTENT)
//                모든이미지
                intent.setType("image/*")
                startActivityForResult(intent, getGalleryImage)
            }
            dlglogin.setNeutralButton("취소") { dialog, which ->
                null
            }
            dlglogin.show()
        }
//        메인으로
        binding.fab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
//        결과확인
        binding.resultBtn.setOnClickListener {
            val stream = ByteArrayOutputStream()
            val bitmap = (binding.selectImg!!.drawable as BitmapDrawable).bitmap
            val scale = (1024 / bitmap.width.toFloat())
            val image_w = (bitmap.width * scale).toInt()
            val image_h = (bitmap.height * scale).toInt()
            val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()
            val intent = Intent(this@InputActivity, ResultActivity::class.java)
            intent.putExtra("image", byteArray)
            startActivity(intent)
        }
    }

    //    카메라 설정
    val requestCameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val calRatio = inSampleSize(
            Uri.fromFile(File(filePath)),
            150,
            150
        )
        val option = BitmapFactory.Options()
        option.inSampleSize = calRatio

        val bitmap = BitmapFactory.decodeFile(filePath, option)
        bitmap?.let {
            binding.selectImg.setImageBitmap(bitmap)
        } ?: let {
            Log.d("pjh", "(in camera)bitmap is null")
        }
    }
// 높이와 넓이 받음..
    private fun inSampleSize(fileUri: Uri, reqWidth: Int, reqHeigt: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)

            inputStream!!.close()
            inputStream = null

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeigt || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWith: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeigt && halfWith / inSampleSize >= reqWidth)
                inSampleSize *= 2
        }
        return inSampleSize
    }


    //    사진 촬영 권한 주기
    private fun todo() {
        // TODO : 기능 구현
        Toast.makeText(this, "완료", Toast.LENGTH_SHORT).show()
    }
//      TedPermission 깃에 있는 거 가져와서 쓴것 퍼미션이 허용되면 작업하라
    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    todo()
                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    Toast.makeText(
                        this@InputActivity,
                        "권한을 허가해주세요",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
            .setPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .check()

    }

    //    사진 촬영본 캡쳐
    // Date() 날짜 시간으로 스트링 만듬
//    @Throws(IOException::class)
    private fun startCapture() {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir((Environment.DIRECTORY_PICTURES))
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )

        filePath = file.absolutePath

        val photoUri: Uri = FileProvider.getUriForFile(
            this,
            "com.example.tfapp.fileprovider",
            file
        )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

        requestCameraLauncher.launch(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap? = null
//        사진찍었을 때 이렇게 해라
//        밑에도 중복되는 내용 없어도 됨
//        if (requestCode == getGalleryImage && resultCode == RESULT_OK && data != null && data != null) {
//            selectedImgUri = data.data!!
//            binding.selectImg.setImageURI(selectedImgUri)
//        }
//        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
//            val file = File(currentPhotoPath)
//            if (Build.VERSION.SDK_INT < 28) {
//                val bitmap = MediaStore.Images.Media
//                    .getBitmap(contentResolver, Uri.fromFile(file))
//                binding.selectImg.setImageBitmap(bitmap)
//            }
//            else{
//                val decode = ImageDecoder.createSource(this.contentResolver,
//                    Uri.fromFile(file))
//                val bitmap = ImageDecoder.decodeBitmap(decode)
//                binding.selectImg.setImageBitmap(bitmap)
//            }
//        }
        if (resultCode == Activity.RESULT_OK) {
            // 갤러리 선택
//            주소를 받아서 비트맵생성하고 주소 넣어줌 사진 넣어주는 코드
            if (requestCode == getGalleryImage && data != null) {
                selectedImgUri = data.data!!
                bitmap = MediaStore.Images.Media
                    .getBitmap(contentResolver, selectedImgUri)
                binding.selectImg.setImageBitmap(bitmap)
            }
            // 사진촬영
            else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val file = File(currentPhotoPath)
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media
                        .getBitmap(contentResolver, Uri.fromFile(file))
                    binding.selectImg.setImageBitmap(bitmap)
                } else {
                    bitmap = MediaStore.Images.Media
                        .getBitmap(contentResolver, Uri.fromFile(file))
                    binding.selectImg.setImageBitmap(bitmap)
                }
            }
        }

    }
}