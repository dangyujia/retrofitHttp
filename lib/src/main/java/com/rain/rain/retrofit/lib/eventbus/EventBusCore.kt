package com.rain.rain.retrofit.lib.eventbus

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlin.collections.HashMap
import kotlin.collections.forEach
import kotlin.collections.listOfNotNull
import kotlin.collections.set

/**
 * 发送消息的viewModel
 */
class EventBusCore : ViewModel() {

    //正常事件
    private val eventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    //粘性事件
    private val stickyEventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    /**
     * @param eventName 发送对象类名
     * @param isSticky 是否给新的订阅者发送之前的数据
     * @return 返回一个ShareFlow
     */
    private fun getEventFlow(eventName: String, isSticky: Boolean): MutableSharedFlow<Any> {
        return if (isSticky) {
            stickyEventFlows[eventName]
        } else {
            eventFlows[eventName]
        } ?: MutableSharedFlow<Any>(
            replay = if (isSticky) 1 else 0,
            extraBufferCapacity = Int.MAX_VALUE
        ).also {
            if (isSticky) {
                stickyEventFlows[eventName] = it
            } else {
                eventFlows[eventName] = it
            }
        }
    }

    /**
     * @param lifecycleOwner 检测生命周期
     * @param eventName 发送对象类名
     * @param minState 指定可感知的最小生命状态
     * @param dispatcher 线程切换
     * @param isSticky 是否给新的订阅者发送之前的数据
     * 感知生命周期的事件发送
     */
    fun <T : Any> observeEvent(
        lifecycleOwner: LifecycleOwner,
        eventName: String,
        minState: Lifecycle.State,
        dispatcher: CoroutineDispatcher,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
//        EventBusInitializer.logger?.log(Level.WARNING, "observe Event:$eventName")
        lifecycleOwner.launchWhenStateAtLeast(minState) {
            getEventFlow(eventName, isSticky).collect { value ->
                this.launch(dispatcher) {
                    invokeReceived(value, onReceived)
                }
            }
        }
    }

    /**
     * 无感知生命周期的事件发送
     */
    suspend fun <T : Any> observeWithoutLifecycle(
        eventName: String,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
        getEventFlow(eventName, isSticky).collect { value ->
            invokeReceived(value, onReceived)
        }
    }


    fun postEvent(eventName: String, value: Any, timeMillis: Long) {
//        EventBusInitializer.logger?.log(Level.WARNING, "post Event:$eventName")
        listOfNotNull(
            getEventFlow(eventName, false),
            getEventFlow(eventName, true)
        ).forEach { flow ->
            viewModelScope.launch {
                delay(timeMillis)
                flow.emit(value)
            }
        }
    }


    fun removeStickEvent(eventName: String) {
        stickyEventFlows.remove(eventName)
    }

    fun clearStickEvent(eventName: String) {
        stickyEventFlows[eventName]?.resetReplayCache()
    }


    private fun <T : Any> invokeReceived(value: Any, onReceived: (T) -> Unit) {
        try {
            onReceived.invoke(value as T)
        } catch (e: ClassCastException) {
//            EventBusInitializer.logger?.log(
//                Level.WARNING,
//                "class cast error on message received: $value",
//                e
//            )
        } catch (e: Exception) {
//            EventBusInitializer.logger?.log(
//                Level.WARNING,
//                "error on message received: $value",
//                e
//            )
        }
    }


    fun getEventObserverCount(eventName: String): Int {
        val stickyObserverCount = stickyEventFlows[eventName]?.subscriptionCount?.value ?: 0
        val normalObserverCount = eventFlows[eventName]?.subscriptionCount?.value ?: 0
        return stickyObserverCount + normalObserverCount
    }

}