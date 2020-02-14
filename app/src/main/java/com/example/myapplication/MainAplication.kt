package com.example.myapplication.com.example.myapplication

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
//import sun.jvm.hotspot.utilities.IntArray


class MainAplication: Application() {
    init {
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}