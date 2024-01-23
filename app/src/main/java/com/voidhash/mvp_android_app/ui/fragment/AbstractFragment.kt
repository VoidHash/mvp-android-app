package com.voidhash.mvp_android_app.ui.fragment

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment

abstract class AbstractFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    protected abstract fun getLayout(): Int

    protected abstract fun onInject()
}