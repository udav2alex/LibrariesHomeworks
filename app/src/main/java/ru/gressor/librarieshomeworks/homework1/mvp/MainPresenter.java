package ru.gressor.librarieshomeworks.homework1.mvp;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.gressor.librarieshomeworks.homework1.MainModel;
import ru.gressor.librarieshomeworks.homework1.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    MainModel model = new MainModel();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        int counter = model.getCounter();
        getViewState().setButtonText(counter);
    }

    public void onClick() {
        int counter = model.getCounter();
        model.setCounter(++counter);
        getViewState().setButtonText(counter);
    }
}
