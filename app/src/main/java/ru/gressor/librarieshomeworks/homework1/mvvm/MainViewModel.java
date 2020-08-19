package ru.gressor.librarieshomeworks.homework1.mvvm;

import androidx.lifecycle.ViewModel;

import java.util.Observable;
import java.util.Observer;

import ru.gressor.librarieshomeworks.homework1.MainModel;

public class MainViewModel extends ViewModel {
    private MainModel model = new MainModel();
    private MainObservable observable = new MainObservable();

    public void onClick() {
        int counter = model.getCounter();
        model.setCounter(++counter);

        observable.changeData(counter);
    }

    public void subscribe(Observer observer) {
        observable.addObserver(observer);
        observable.changeData(model.getCounter());
    }

    public void unsubscribe(Observer observer) {
        observable.deleteObserver(observer);
    }

    static class MainObservable extends Observable {

        public void changeData(int counter) {
            setChanged();
            notifyObservers(counter);
        }
    }
}
