package ru.gressor.librarieshomeworks.homework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.gressor.librarieshomeworks.R;

public class MainActivityHW3_1 extends AppCompatActivity {
    private static final String TAG = "HW3_LOG_TAG";
    private static final int PNG_QUALITY = 100;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw3_activity_main);

        init();
    }

    private void init() {
        findViewById(R.id.HW3_1_button).setOnClickListener((view) -> {
            Log.d(TAG, "OnClick");

            if (!hasFileIOPermissions()) {
                Log.d(TAG, "Read/write permissions not granted!");
                Toast.makeText(this, "Read/write permissions not granted!",
                        Toast.LENGTH_LONG).show();
                requestFileIOPermissions();
            } else {
                Log.d(TAG, "Read/write permissions granted!");

                AlertDialog dialog = getCancelDialogBuilder();
                disposable = fileConverter()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> dialog.show())
                        .doOnTerminate(dialog::hide)
                        .subscribe(
                                () -> Toast.makeText(this, "Well done!", Toast.LENGTH_LONG).show(),
                                (throwable) -> Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show());
            }
        });
    }

    private AlertDialog getCancelDialogBuilder() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivityHW3_1.this);
        builder
                .setCancelable(false)
                .setTitle("Cancel converter?")
                .setMessage("You can cancel converter")
                .setPositiveButton("Cancel", (dialogInterface, i) -> {
                    if (disposable != null && !disposable.isDisposed())
                            disposable.dispose();
                });

        return builder.create();
    }

    private Completable fileConverter() {
        return Completable.create(emitter -> {
            try {
                File dir = Environment.getExternalStorageDirectory();
                File file = new File(dir + "/Download", "test.jpg");
                Log.d(TAG, file.getCanonicalPath());

                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    File newFile = new File(file.getParent(), "test.png");

                    try (FileOutputStream out = new FileOutputStream(newFile)) {
                        Thread.sleep(5000);
                        bitmap.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY, out);
                    } catch (IOException e) {
                        Log.d(TAG, "convertFile inner: " + e.toString());
                        emitter.onError(new RuntimeException("convertFile inner: " + e.toString()));
                    } catch (InterruptedException e) {
                        Log.d(TAG, "convertFile inner: " + e.toString());
                    }
                }
            } catch (IOException e) {
                Log.d(TAG, "convertFile outer: " + e.toString());
                emitter.onError(new RuntimeException("convertFile outer: " + e.toString()));
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.computation());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");

        if (requestCode == PERMISSION_REQUEST_CODE) {
            new AlertDialog.Builder(this)
                    .setTitle("Permissions granted!")
                    .setMessage("Please push button again!")
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestFileIOPermissions() {
        Log.d(TAG, "requestFileIOPermissions");

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    private boolean hasFileIOPermissions() {
        Log.d(TAG, "hasFileIOPermissions");

        boolean read = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean write = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return read && write;
    }
}