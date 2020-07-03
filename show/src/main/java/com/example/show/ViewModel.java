package com.example.show;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.ObservableBoolean;

public class ViewModel extends AndroidViewModel {
   public ObservableBoolean isShow = new ObservableBoolean(false);

    public ViewModel(@NonNull Application application) {
        super(application);
    }
}
