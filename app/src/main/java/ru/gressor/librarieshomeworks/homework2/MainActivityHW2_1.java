package ru.gressor.librarieshomeworks.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.functions.Consumer;
import ru.gressor.librarieshomeworks.R;

public class MainActivityHW2_1 extends AppCompatActivity {
    public static final String TAG = "TEST_LOG";
    TextView textView;
    EditText editText;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewHW2_1);
        editText = findViewById(R.id.editTextHW2_1);

        disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence s, int start, int count, int after) {
                        Log.d(TAG, "beforeTextChanged(" + s
                                + ", start: " + start
                                + ", count: " + count + ", after: " + after + ")");
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d(TAG, "onTextChanged(" + s
                                + ", start: " + start
                                + ", before: " + before + ", count: " + count + ")");
                        if (!emitter.isDisposed()) {
                            emitter.onNext(s.toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        Log.d(TAG, "afterTextChanged(" + editable.toString() + ")");
                    }
                };

                emitter.setCancellable(
                        () -> editText.removeTextChangedListener(textWatcher));
                editText.addTextChangedListener(textWatcher);
            }
        }).subscribe(string -> textView.setText(string));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null && !disposable.isDisposed()) {
            Log.d(TAG, "dispose()");
            disposable.dispose();
        }
    }
}