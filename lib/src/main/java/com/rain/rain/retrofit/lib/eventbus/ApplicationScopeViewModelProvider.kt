package com.rain.rain.retrofit.lib.eventbus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.rain.rain.retrofit.lib.Comm

object ApplicationScopeViewModelProvider : ViewModelStoreOwner {

    private val eventViewModelStore: ViewModelStore = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore {
        return eventViewModelStore
    }

    private val mApplicationProvider: ViewModelProvider by lazy {
        ViewModelProvider(
            ApplicationScopeViewModelProvider,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Comm.getApplication())
        )
    }

    fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mApplicationProvider[modelClass]
    }
}