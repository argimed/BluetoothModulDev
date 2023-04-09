package com.example.bt_def

import android.widget.ImageButton
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

fun Fragment.changeButtonColor(button: ImageButton, color: Int){
    val drawable = button.drawable
    DrawableCompat.setTint(drawable, color)
    button.setImageDrawable(drawable)
}

fun Fragment.checkBtPermissions(): Boolean{
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}