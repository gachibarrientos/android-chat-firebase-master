package edu.galileo.android.androidchat.register;

import android.content.Intent;
import android.media.UnsupportedSchemeException;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity implements LoginView {


    @Bind(R.id.btnSignup)
    Button btnSignUp;
    @Bind(R.id.editTxtEmail)
    EditText inputEmail;
    @Bind(R.id.editTxtPassword)
    EditText inputPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle(R.string.register_title);
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.onResume();
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
    public void enableInputs() {
    setInputs(true);
    }

    @Override
    public void disableInputs() {
    setInputs(false);
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
    @OnClick(R.id.btnSignup)
    public void handleSignUp() {
        loginPresenter.registerNewUser(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

    @Override
    public void handleSignIn() {
        throw new UnsupportedOperationException("Operación no valida en Registro");
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
    }

    @Override
    public void loginError(String error) {
        throw new UnsupportedOperationException("Operación no valida en Registro");
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_useradded, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(msgError);
    }
    private void setInputs(boolean enabled){
        btnSignUp.setEnabled(true);
        inputEmail.setEnabled(true);
        inputPassword.setEnabled(true);
    }
}
