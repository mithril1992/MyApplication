package com.example.myapplication.mvvm.viewmodel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.mvvm.model.ConcreteLoginDAO

class LoginViewModel(application: Application): AndroidViewModel(application) {
    val userId = ObservableField<String>("")
    val userIdErrorMessage = ObservableField<String>("")

    var password = ObservableField<String>("")
    var passwordErrorMessage = ObservableField<String>("")

    fun login(view: View) {
        val userId = this.userId.get()!!
        val password = this.password.get()!!

        validateUserId(userId)
        validatePassword(password)

        if(!ConcreteLoginDAO.login(userId, password)) {
            return
        }

        Navigation.findNavController(view).navigate(R.id.action_from_mvvm_to_success)
    }

    private fun validateUserId(userId: String) {
        if(userId == "") {
            userIdErrorMessage.set("ユーザー名を入力してください")
            return
        }

        userIdErrorMessage.set("")
    }

    private fun validatePassword(userId: String) {
        if(userId == "") {
            passwordErrorMessage.set("パスワードを入力してください")
            return
        }

        passwordErrorMessage.set("")
    }
}