package edu.galileo.android.androidchat.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.contactlist.ui.ContactListActivity;
import edu.galileo.android.androidchat.login.LoginPresenter;
import edu.galileo.android.androidchat.login.LoginPresenterImpl;
import edu.galileo.android.androidchat.login.ui.LoginView;
import edu.galileo.android.androidchat.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity
                           implements LoginView {
    @Bind(R.id.btnSignin)           Button btnSignIn;
    @Bind(R.id.btnSignup)           Button btnSignUp;
    @Bind(R.id.editTxtEmail)        EditText inputEmail;
    @Bind(R.id.editTxtPassword)     EditText inputPassword;
    @Bind(R.id.progressBar)         ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer) RelativeLayout container;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticatedUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.onResume();
        loginPresenter.checkForAuthenticatedUser();
    }

    @Override
    protected void onPause() {
        loginPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    @OnClick(R.id.btnSignup)
    public void handleSignUp() {
      startActivity(new Intent(this, RegisterActivity.class));

    }

    @Override
    @OnClick(R.id.btnSignin)
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void navigateToMainScreen() {
        Intent i = new Intent(this, ContactListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void newUserError(String error) {
        throw new UnsupportedOperationException("Operación no valida en Login");
    }

    @Override
    public void newUserSuccess() {
            throw new UnsupportedOperationException("Operación no valida en Login");

    }

    private void setInputs(boolean enabled){
        btnSignIn.setEnabled(true);
        btnSignUp.setEnabled(true);
        inputEmail.setEnabled(true);
        inputPassword.setEnabled(true);
    }
}
