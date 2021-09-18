package com.rain.rain.retrofit.http

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.rain.rain.retrofit.http.base.Result
import com.rain.rain.retrofit.http.test.Test2ViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: Test2ViewModel by lazy { Test2ViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.viewModelScope.launch {
            viewModel.shareFlowLogin.collect { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        Log.i("=============", "$data")
                        findViewById<TextView>(R.id.tv_content).text = data.toString()
                    }
                    is Result.Error -> {

                    }
                    is Result.Loading -> {
                        Log.i("=============", "isLoading = ${result.isLoading}")
                    }
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.shareFlow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        Log.i("=============", "========== $data")
                        findViewById<TextView>(R.id.tv_content).text = data.toString()
                    }
                    is Result.Error -> {

                    }
                    is Result.Loading -> {
                        Log.i("=============", "isLoading = ${result.isLoading}")
                    }
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.sharedFlow2.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.i("=============", "isLoading = ${result.isLoading}")
                    }
                    is Result.Success -> {
                        val data = result.data
                        Log.i("=============", "========== $result")
                        findViewById<TextView>(R.id.tv_content).text =
                            data.list.toString() + " \n " + data.data?.toString()
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.sharedFlow1.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.i("=============", "isLoading = ${result.isLoading}")
                    }
                    is Result.Success -> {
                        val data = result.data
                        Log.i("=============", "========== $result")
                        findViewById<TextView>(R.id.tv_content).text = data.toString()
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.sharedFlow3.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.i("=============", "isLoading = ${result.isLoading}")
                    }
                    is Result.Success -> {
                        val data = result.data
                        Log.i("=============", "========== $result")
                        findViewById<TextView>(R.id.tv_content).text = data.toString()
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.stateFlow.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.i("=============", "isLoading = ${result.isLoading}")
                    }
                    is Result.Success -> {
                        val data = result.data
                        Log.i("=============", "========== $result")
                        findViewById<TextView>(R.id.tv_content).text = data.toString()
                    }
                    is Result.Error -> {
                    }
                }
            }
        }

    }

    fun roomA(v: View) {
//        viewModel.get2()
        viewModel.viewModelScope.launch {
            viewModel.get3().collect { result ->
                result.request(success = {
                    Log.i("=============", "========== $it")
                    findViewById<TextView>(R.id.tv_content).text = it.toString()
                }, loading = {
                    Log.i("=============", "isLoading = $it")
                }, error = {
                    Log.i("=============", "throwable = $it")
                })
            }
        }
    }

    fun roomB(v: View) {
        viewModel.get1()
    }

    fun requestGet(v: View) {
        viewModel.sub()
    }


    fun request(v: View) {
        viewModel.get()
    }

    fun requestPost(v: View) {
        viewModel.toLogin(mutableMapOf("uPhone" to "13032963326", "uPass" to "asd123456"))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewModelScope.cancel()
    }

}