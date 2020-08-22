package ru.gressor.librarieshomeworks.homework1;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

public interface MainView extends MvpView {
    @AddToEndSingle
    void setButtonText(int value);
}
