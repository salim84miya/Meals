package com.example.meals.network

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.meals.utils.InternetConnectivity

open class BaseActivity:AppCompatActivity() {

    private var alertDialog: AlertDialog? = null
    private lateinit var networkManager: NetworkConnectivityCheck
    protected var isConnected: Boolean = true // Track the network status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AlertDialog and NetworkManager
        alertDialog = InternetConnectivity.createAlertDialog(this)
        networkManager = NetworkConnectivityCheck(this)

        // Observe network status changes
        networkManager.observe(this, Observer { isConnected ->
            this.isConnected = isConnected

            if (isConnected) {
                alertDialog?.let {
                    if (it.isShowing) it.hide()
                }
            } else {
                alertDialog?.let {
                    if (!it.isShowing) it.show()
                }
            }

            // If there's no internet, return early to prevent further code execution
            if (!isConnected) return@Observer
            onNetworkAvailable()
        })
    }

    /**
     * Override this method in derived activities to execute code when network is available.
     */
    open fun onNetworkAvailable() {
        // This will be overridden in derived activities
    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog = null // Avoid memory leaks
    }
}