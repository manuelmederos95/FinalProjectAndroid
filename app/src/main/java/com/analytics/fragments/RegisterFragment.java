package com.analytics.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.analytics.model.Response;
import com.analytics.model.User;
import com.analytics.network.NetworkUtil;
import com.analytics.utils.Constants;
import com.analytics.utils.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.analytics.R;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RegisterFragment extends Fragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private EditText mEtName;
    private EditText mEtLastName;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button   mBtRegister;
    private TextView mTvLogin;
    private TextInputLayout mTiLastName;
    private TextInputLayout mTiName;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private ProgressBar mProgressbar;

    private CompositeSubscription mSubscriptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        return view;
    }

    private void initViews(View v) {

        mEtName = (EditText) v.findViewById(R.id.et_name);
        mEtLastName = (EditText) v.findViewById(R.id.et_lastname);
        mEtEmail = (EditText) v.findViewById(R.id.et_email);
        mEtPassword = (EditText) v.findViewById(R.id.et_password);
        mBtRegister = (Button) v.findViewById(R.id.btn_register);
        mTvLogin = (TextView) v.findViewById(R.id.tv_login);
        mTiLastName = (TextInputLayout) v.findViewById(R.id.ti_lastname);
        mTiName = (TextInputLayout) v.findViewById(R.id.ti_name);
        mTiEmail = (TextInputLayout) v.findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout) v.findViewById(R.id.ti_password);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);

        mBtRegister.setOnClickListener(view -> register());
        mTvLogin.setOnClickListener(view -> goToLogin());
    }

    private void register() {

        setError();

        String lastname = mEtLastName.getText().toString();
        String name = mEtName.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!Validation.validateFields(lastname)) {

            err++;
            mTiLastName.setError("Last Name should not be empty !");
        }

        if (!Validation.validateFields(name)) {

            err++;
            mTiName.setError("First Name should not be empty !");
        }

        if (!Validation.validateEmail(email)) {

            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!Validation.validateFields(password)) {

            err++;
            mTiPassword.setError("Password should not be empty !");
        }

        if (!Validation.validatePassword(password)) {

            err++;
            mTiPassword.setError("Password must be between 8-100 characters long !");
        }

        if (err == 0) {


            mProgressbar.setVisibility(View.VISIBLE);
            registerProcess(name, lastname, email,password, password);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void setError() {

        mTiLastName.setError(null);
        mTiName.setError(null);
        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

    private void registerProcess(String firstname, String lastname, String email, String password, String password2) {

        Log.println(Log.INFO,"FIRSTNAME",firstname );
        Log.println(Log.INFO,"LASTNAME",lastname );
        Log.println(Log.INFO,"EMAIL",email );
        Log.println(Log.INFO,"PASSWORD",password );
        Log.println(Log.INFO,"PASSWORD2",password2 );


        mSubscriptions.add(NetworkUtil.getRetrofit(Constants.SIGN_URL).signUp(firstname,lastname,email,password, password2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));

    }

    private void handleResponse(Response response) {

        mProgressbar.setVisibility(View.GONE);
        showSnackBarMessage(response.getMsg());

    }

    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                //String errorBody = ((HttpException) error).response().errorBody().string();
                //Response response = gson.fromJson(errorBody,Response.class);
                //showSnackBarMessage(response.getMsg());
                Log.println(Log.ERROR,"ERROR",error.toString() );
                showSnackBarMessage("Network Error !");

            } catch (Exception e) {
                e.printStackTrace();
                showSnackBarMessage("Network Error !");
            }
        } else {
            error.printStackTrace();
            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    private void goToLogin(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        ft.replace(R.id.fragmentFrame, fragment, LoginFragment.TAG);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
