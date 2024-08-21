package com.example.meals.network

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.meals.utils.InternetConnectivity

open class BaseFragment :Fragment(){


    private var alertDialog: AlertDialog? = null
    private lateinit var networkManager: NetworkConnectivityCheck
    protected var isConnected: Boolean = true // Track the network status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AlertDialog and NetworkManager
        alertDialog = InternetConnectivity.createAlertDialog(requireActivity())
        networkManager = NetworkConnectivityCheck(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe network status changes
        networkManager.observe(viewLifecycleOwner, Observer { isConnected ->
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
     * Override this method in derived fragments to execute code when network is available.
     */
    open fun onNetworkAvailable() {
        // This will be overridden in derived fragments
    }

    override fun onDestroyView() {
        super.onDestroyView()
        alertDialog = null // Avoid memory leaks
    }
}