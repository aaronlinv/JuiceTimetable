package com.juice.timetable.ui.unsigned;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UnsignedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UnsignedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}