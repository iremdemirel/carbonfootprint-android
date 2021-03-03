package com.example.bil496.ui.foundations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoundationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoundationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is foundation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}