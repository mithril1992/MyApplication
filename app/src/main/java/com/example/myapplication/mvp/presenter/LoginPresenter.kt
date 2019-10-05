package com.example.myapplication.mvp.presenter

import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.mvp.LoginPage
import com.example.myapplication.mvvm.model.ConcreteLoginDAO

class LoginPresenter(override val view: LoginPage.View): LoginPage.Presenter {
    override fun login() {
        val userId = view.userId
        val password = view.password

        validateUserId(userId)
        validatePassword(password)

        if(!ConcreteLoginDAO.login(userId, password)) {
            return
        }

        view.navigate(R.id.action_from_mvp_to_success)
    }

    private fun validateUserId(userId: String) {
        if(userId == "") {
            view.userIdErrorMessage = "ユーザー名を入力してください"
            return
        }

        view.userIdErrorMessage = ""
    }

    private fun validatePassword(userId: String) {
        if(userId == "") {
            view.passwordErrorMessage = "パスワードを入力してください"
            return
        }

        view.passwordErrorMessage = ""
    }
}