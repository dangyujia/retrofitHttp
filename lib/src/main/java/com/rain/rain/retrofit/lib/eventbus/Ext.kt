package com.rain.rain.retrofit.lib.eventbus

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//监听App Scope 事件
inline fun <reified T> LifecycleOwner.observeEvent(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .observeEvent(
            this,
            T::class.java.name,
            minActiveState,
            dispatcher,
            isSticky,
            onReceived
        )
}

//监听Fragment Scope 事件
inline fun <reified T> observeEvent(
    scope: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .observeEvent(
            scope,
            T::class.java.name,
            minActiveState,
            dispatcher,
            isSticky,
            onReceived
        )
}

//Fragment 监听Activity Scope 事件
inline fun <reified T> observeEvent(
    scope: ComponentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .observeEvent(
            scope,
            T::class.java.name,
            minActiveState,
            dispatcher,
            isSticky,
            onReceived
        )
}


inline fun <reified T> observeEvent(
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) {
    coroutineScope.launch {
        ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
            .observeWithoutLifecycle(
                T::class.java.name,
                isSticky,
                onReceived
            )
    }
}

inline fun <reified T> observeEvent(
    scope: ViewModelStoreOwner,
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) {
    coroutineScope.launch {
        ViewModelProvider(scope).get(EventBusCore::class.java)
            .observeWithoutLifecycle(
                T::class.java.name,
                isSticky,
                onReceived
            )
    }
}


inline fun <reified T> getEventObserverCount(event: Class<T>): Int {
    return ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .getEventObserverCount(event.name)
}

inline fun <reified T> getEventObserverCount(scope: ViewModelStoreOwner, event: Class<T>): Int {
    return ViewModelProvider(scope).get(EventBusCore::class.java)
        .getEventObserverCount(event.name)
}


//移除事件
inline fun <reified T> removeStickyEvent(event: Class<T>) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .removeStickEvent(event.name)
}

inline fun <reified T> removeStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .removeStickEvent(event.name)
}


// 清除事件缓存
inline fun <reified T> clearStickyEvent(event: Class<T>) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .clearStickEvent(event.name)
}

inline fun <reified T> clearStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .clearStickEvent(event.name)
}



fun <T> LifecycleOwner.launchWhenStateAtLeast(
    minState: Lifecycle.State,
    block: suspend CoroutineScope.() -> T
) {
    lifecycleScope.launch {
        lifecycle.whenStateAtLeast(minState, block)
    }
}

//Application范围的事件
inline fun <reified T> postEvent(event: T, timeMillis: Long = 0L) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}

//限定范围的事件
inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}