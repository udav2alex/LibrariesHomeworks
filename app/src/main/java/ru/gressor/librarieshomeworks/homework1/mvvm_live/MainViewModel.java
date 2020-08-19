package ru.gressor.librarieshomeworks.homework1.mvvm_live;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import ru.gressor.librarieshomeworks.homework1.MainModel;

public class MainViewModel extends ViewModel {
    private MainModel model = new MainModel();
    private MutableLiveData<Integer> clicksLiveData = new MutableLiveData<>(model.getCounter());

    public void onClick() {
        int counter = model.getCounter();
        model.setCounter(++counter);

        clicksLiveData.setValue(counter);
    }

    public void setClicksObserver(LifecycleOwner owner, Observer<? super Integer> observer) {
        clicksLiveData.observe(owner, observer);
    }
}
