package ru.gressor.librarieshomeworks.homework2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.reactivestreams.Subscription;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import ru.gressor.librarieshomeworks.R;

public class MainActivityHW2_1v2 extends AppCompatActivity {
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewHW2_1);
        editText = findViewById(R.id.editTextHW2_1);

        Flowable.create((FlowableOnSubscribe<String>) emitter -> {
            Log.d("TEST_LOG", "test start");
            if (emitter.isCancelled()) return;

            Log.d("TEST_LOG", "test?");

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    emitter.onNext(editable.toString());
                }
            };

            emitter.setCancellable(() -> {
                Log.d("TEST_LOG", "cancel!");
                editText.removeTextChangedListener(textWatcher);
            });
            editText.addTextChangedListener(textWatcher);

        }, BackpressureStrategy.DROP)
                .throttleLast(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<String>() {
                    Subscription subscription;
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.d("TEST_LOG", "onSubscribe!");
                        subscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("TEST_LOG", s);
                        textView.setText(s);
//                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) { }

                    @Override
                    public void onComplete() { }
                });
    }
}
