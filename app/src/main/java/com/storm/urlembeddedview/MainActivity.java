package com.storm.urlembeddedview;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.storm.URLConstants;
import com.storm.URLEmbeddedData;
import com.storm.URLEmbeddedView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edtURL;
    private Button btnURL;
    private URLEmbeddedView urlEmbeddedView;


    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission. POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission. POST_NOTIFICATIONS)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            //showInContextUI(...);
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    android.Manifest.permission. POST_NOTIFICATIONS);
        }

        edtURL = findViewById(R.id.edt_url);
        btnURL = findViewById(R.id.btn_url);
        urlEmbeddedView = findViewById(R.id.uev);

        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = edtURL.getText().toString();
                urlEmbeddedView.setURL(edtURL.getText().toString(), new URLEmbeddedView.OnLoadURLListener() {
                    @Override
                    public void onLoadURLCompleted(URLEmbeddedData data) {
                        String lastUrl = ((url.startsWith(URLConstants.PROTOCOL) || url.startsWith(URLConstants.PROTOCOL_S)) ? "" : URLConstants.PROTOCOL) + url;
                        urlEmbeddedView.title(data.getTitle() != null ? data.getTitle() : lastUrl);
                        urlEmbeddedView.description(data.getDescription());
                        urlEmbeddedView.host(data.getHost());
                        urlEmbeddedView.thumbnail(data.getThumbnailURL());
                        urlEmbeddedView.favor(data.getFavorURL());
                    }
                });
            }
        });
    }
}
