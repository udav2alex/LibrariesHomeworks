package ru.gressor.librarieshomeworks.homework1.mvvm;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import ru.gressor.librarieshomeworks.R;
import ru.gressor.librarieshomeworks.homework1.MainView;

public class MainActivity extends AppCompatActivity implements MainView, Observer {
    private Button button;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw1_activity_main);

        button = findViewById(R.id.hw1_button);
        button.setOnClickListener((view) -> viewModel.onClick());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.subscribe(this);
    }

    @Override
    protected void onStop() {
        viewModel.unsubscribe(this);
        super.onStop();
    }

    @Override
    public void setButtonText(int value) {
        button.setText(String.format(Locale.getDefault(),
                "%s %d %s", getResources().getString(R.string.hw1_1_button_text), value, " (mvvm)"));
    }

    @Override
    public void update(Observable observable, Object o) {
        setButtonText((Integer)o);
    }
}