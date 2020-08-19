package ru.gressor.librarieshomeworks.homework1.mvp;

import android.os.Bundle;
import android.widget.Button;

import java.util.Locale;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

import ru.gressor.librarieshomeworks.R;
import ru.gressor.librarieshomeworks.homework1.MainView;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    Button button;

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw1_activity_main);

        button = findViewById(R.id.hw1_button);
        button.setOnClickListener((view) -> presenter.onClick());
    }

    @Override
    public void setButtonText(int value) {
        button.setText(String.format(Locale.getDefault(), "%s %d",
                getResources().getString(R.string.hw1_1_button_text), value));
    }
}