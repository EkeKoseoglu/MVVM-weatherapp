package tr.com.homesoft.weatherapp.util.extensions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

fun AppCompatActivity.isPermissionGranted(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.shouldShowPermissionRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermission(permission: String, requestId: Int) =
    ActivityCompat.requestPermissions(this, arrayOf(permission), requestId)

fun Activity.batchRequestPermissions(permissions: Array<String>, requestId: Int) =
    ActivityCompat.requestPermissions(this, permissions, requestId)