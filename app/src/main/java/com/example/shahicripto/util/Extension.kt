package com.example.shahicripto.util

import android.content.Context
import android.widget.Toast
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun Context.showToast(title : String){
Toast.makeText(this , title , Toast.LENGTH_SHORT).show()
}

fun Completable.asyncRequest() : Completable {

    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}

fun <T> Single<T>.asyncRequest() : Single<T> {

    return  subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}