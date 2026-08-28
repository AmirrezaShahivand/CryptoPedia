package com.example.shahicripto.util

import android.content.Context
import android.widget.Toast
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import retrofit2.HttpException


fun Context.showToast(title : String){
Toast.makeText(this , title , Toast.LENGTH_SHORT).show()
}

fun Completable.asyncRequest() : Completable {

    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}

fun <T : Any> Single<T>.asyncRequest() : Single<T> {

    return  subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}

fun Throwable.userFacingMessage(): String {
    return when (this) {
        is HttpException -> when (code()) {
            402 -> "CoinPaprika این سرویس را برای حساب فعلی فعال نکرده است؛ منبع جایگزین در حال استفاده است"
            429 -> "محدودیت درخواست API؛ کمی بعد دوباره تلاش کنید"
            404 -> "اطلاعات این ارز پیدا نشد"
            else -> "خطای سرویس داده (${code()})"
        }
        is IOException -> "اتصال اینترنت برقرار نیست"
        else -> message?.takeIf { it.isNotBlank() } ?: "خطای نامشخص در دریافت اطلاعات"
    }
}
