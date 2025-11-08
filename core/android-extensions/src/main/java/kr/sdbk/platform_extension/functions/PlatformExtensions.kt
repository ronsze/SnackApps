package kr.sdbk.platform_extension.functions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

//  플랫폼 기능을 활용하는 함수

fun showToast(context: Context, msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, msg, duration).show()
}

fun showToast(context: Context, @StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, ContextCompat.getString(context, msg), duration).show()
}
