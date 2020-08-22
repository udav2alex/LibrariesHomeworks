package ru.gressor.librarieshomeworks.homework1.mvvm_live;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import ru.gressor.librarieshomeworks.R;
import ru.gressor.librarieshomeworks.homework1.MainView;

public class MainActivity extends AppCompatActivity implements MainView {
    private Button button;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw1_activity_main);

        button = findViewById(R.id.hw1_button);
        button.setOnClickListener((view) -> viewModel.onClick());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.setClicksObserver(this, this::setButtonText);
    }

    @Override
    public void setButtonText(int value) {
        button.setText(String.format(Locale.getDefault(),
                "%s %d %s", getResources().getString(R.string.hw1_1_button_text), value, " (mvvm live)"));
    }
}