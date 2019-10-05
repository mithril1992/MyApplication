package com.example.myapplication.mvp

import androidx.annotation.IdRes

interface LoginPage {
    interface View {
        var presenter: Presenter

        val userId: String
        var userIdErrorMessage: String

        val password: String
        var passwordErrorMessage: String

        fun navigate(@IdRes navigation: Int)
    }

    interface Presenter {
        val view: View
        fun login()
    }
}