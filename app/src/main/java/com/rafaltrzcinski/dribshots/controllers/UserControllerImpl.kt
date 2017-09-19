package com.rafaltrzcinski.dribshots.controllers

import com.rafaltrzcinski.dribshots.di.Injector

class UserControllerImpl : UserController {

    private val sharedPrefs = Injector.component.getSharedPrefs()

    init {
        setAccessToken("25b7b8a70de02ffee1700d6a5e8c31b002eacc57e962fde54851cc50a9dadc92")
    }

    companion object {
        val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    private fun setAccessToken(accessToken: String) =
            sharedPrefs.edit().putString(ACCESS_TOKEN, accessToken).apply()

    override fun getAccessToken(): String = sharedPrefs.getString(ACCESS_TOKEN, "")
}