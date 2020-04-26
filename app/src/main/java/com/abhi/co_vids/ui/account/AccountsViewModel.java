package com.abhi.co_vids.ui.account;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abhi.co_vids.Register;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.core.content.ContextCompat.startActivity;

public class AccountsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AccountsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is your account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}