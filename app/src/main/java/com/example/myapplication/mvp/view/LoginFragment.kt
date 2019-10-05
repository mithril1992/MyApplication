package com.example.myapplication.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.mvp.LoginPage
import com.example.myapplication.mvp.presenter.LoginPresenter

class LoginFragment : Fragment(), LoginPage.View {
    override var presenter: LoginPage.Presenter = LoginPresenter(this)
    override val userId: String
        get() = userIdTextEdit.text.toString()

    override var userIdErrorMessage: String
        get() = userIdErrorMessageTextView.text.toString()
        set(value) { userIdErrorMessageTextView.text = value}

    override val password: String
        get() = passwordTextEdit.text.toString()

    override var passwordErrorMessage: String
        get() = passwordErrorMessageTextView.text.toString()
        set(value) { passwordErrorMessageTextView.text = value }

    private lateinit var userIdTextEdit: EditText
    private lateinit var userIdErrorMessageTextView: TextView

    private lateinit var passwordTextEdit: EditText
    private lateinit var passwordErrorMessageTextView: TextView

    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mvp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userIdTextEdit = view.findViewById(R.id.login_edit_user_id)
        userIdErrorMessageTextView = view.findViewById(R.id.login_error_user_id)

        passwordTextEdit = view.findViewById(R.id.login_edit_password)
        passwordErrorMessageTextView = view.findViewById(R.id.login_error_password)

        loginButton = view.findViewById<Button>(R.id.login_button).also {
            it.setOnClickListener {
                presenter.login()
            }
        }
    }

    override fun navigate(@IdRes navigation: Int) {
        val view = this.view ?: return
        Navigation.findNavController(view).navigate(navigation)
    }
}