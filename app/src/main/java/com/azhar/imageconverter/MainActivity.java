package com.azhar.imageconverter;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText inputURL, etBase64;
    ImageView imageSelfie;
    AppCompatButton btnConvert;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Tunggu sebentar yaa");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("sedang menampilkan data...");
        progressDialog.setCancelable(true);

        inputURL = findViewById(R.id.inputURL);
        etBase64 = findViewById(R.id.etBase64);
        imageSelfie = findViewById(R.id.imageSelfie);
        btnConvert = findViewById(R.id.btnConvert);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strURL = inputURL.getText().toString();
                String strConvert = Tools.getBase64FromImageURL(strURL);

                if (strURL.equals("")) {
                    Toast.makeText(MainActivity.this, "Masukan URL Gambar!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    Glide.with(MainActivity.this)
                            .load(Tools.getBitmapFromImageURL(strURL))
                            .placeholder(R.drawable.ic_photo_camera)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable final GlideException e,
                                                            final Object model, final Target<Drawable> target,
                                                            final boolean isFirstResource) {
                                    progressDialog.dismiss();
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(final Drawable resource,
                                                               final Object model,
                                                               final Target<Drawable> target,
                                                               final DataSource dataSource,
                                                               final boolean isFirstResource) {
                                    progressDialog.dismiss();
                                    return false;
                                }
                            })
                            .into(imageSelfie);

                    etBase64.setText(strConvert);
                }
            }
        });
    }
}