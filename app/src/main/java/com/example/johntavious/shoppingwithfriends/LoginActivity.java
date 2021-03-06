package com.example.johntavious.shoppingwithfriends;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A login screen that offers login via email/password.
 * @version 1.0
 */
public final class LoginActivity extends Activity {

    private DataController sqlHandler;

    private List<User> registeredUsers;
    private Map<String, String> admins;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private Button cancelButton;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Intent intent;
    private String email = "";
    private final int MIN_PASSWORD_LENGTH = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqlHandler = new SQLiteController(this);
        registeredUsers = sqlHandler.getUsers();
        admins = sqlHandler.getAdmins();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView,
                                              int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

        Button mEmailSignInButton = (Button) findViewById(
                R.id.email_sign_in_button);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setEnabled(true);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        cancelButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                        intent = new Intent(
                                LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (sqlHandler.isValidUsername(email)) {
            mEmailView.setError("Invalid Username");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            cancelButton.setEnabled(true);
        } else {
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
            cancelButton.setEnabled(true);

        }
    }

    /**
     * Checks if a password has valid formatting.
     * @param password the password to check
     * @return true if formatted properly, false otherwise
     */
    boolean isPasswordValid(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private boolean usingAdmin = false;

        /**
         * Creates a User Login Task.
         * @param email the email of the task
         * @param password the password of the task
         */
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            for (User possibleUser : registeredUsers) {
                if (possibleUser.getName().equalsIgnoreCase(mEmail)) {
                    // Account exists, return true if the password matches.
                    email = possibleUser.getEmail();
                    return possibleUser.getPassword()
                            .equals(mPassword);
                }
            }
            for (Map.Entry<String, String> e: admins.entrySet()) {
                if (e.getKey().equalsIgnoreCase(mEmail)) {
                    usingAdmin = true;
                    return e.getValue().equals(mPassword);
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                finish();
                if (!usingAdmin) {
                    intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    intent.putExtra("user", email);
                    startActivity(intent);
                } else {
                    Intent admin = new Intent(LoginActivity.this, AdminWelcome.class);
                    startActivity(admin);
                }
            } else {
                mPasswordView.setError(
                        getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }
}
