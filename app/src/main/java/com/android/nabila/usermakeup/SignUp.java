package com.android.nabila.usermakeup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nabila.usermakeup.Model.GetUser;
import com.android.nabila.usermakeup.Rest.ApiClient;
import com.android.nabila.usermakeup.Rest.ApiInterface;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    Context mContext;
    ImageView mImageView;
    EditText edtAddIdUser, edtAddNamaUser, edtAddUmur,edtAddJenis,edtAddWarna,edtAddUsername,edtAddPassword;
    Button btAddPhotoId, btAddBack, btAddData,btCapture;
    String imagePath = "";
    TextView tvAddMessage;
    File mFileURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = getApplicationContext();
        mImageView = (ImageView) findViewById(R.id.imageView);

        edtAddNamaUser =  findViewById(R.id.txtNama);
        edtAddUmur = findViewById(R.id.txtUmur);
        edtAddJenis =  findViewById(R.id.txtJenisKulit);
        edtAddWarna = findViewById(R.id.txtWarnaKulit);
        edtAddUsername = findViewById(R.id.txtUsername);
        edtAddPassword = findViewById(R.id.txtPassword);
        tvAddMessage = findViewById(R.id.tvAddMessage);

        btCapture =  findViewById(R.id.btCapture);
        btAddPhotoId =  findViewById(R.id.btAddPhotoId);
        btAddData = findViewById(R.id.buttonSigup);
        btAddBack =  findViewById(R.id.buttonCancel);
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                MultipartBody.Part body = null;
                if (!imagePath.isEmpty()){
                    // Buat file dari image yang dipilih
                    File file = new File(imagePath);

                    // Buat RequestBody instance dari file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

                    // MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("photo_url", file.getName(),
                            requestFile);
                }
                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddNamaUser.getText().toString().isEmpty())?"":edtAddNamaUser.getText().toString());
                RequestBody reqUmur = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddUmur.getText().toString().isEmpty())?"":edtAddUmur.getText().toString());
                RequestBody reqJenis = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddJenis.getText().toString().isEmpty())?"":edtAddJenis.getText().toString());
                RequestBody reqWarna = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddWarna.getText().toString().isEmpty())?"":edtAddWarna.getText().toString());
                RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddUsername.getText().toString().isEmpty())?"":edtAddUsername.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddPassword.getText().toString().isEmpty())?"":edtAddPassword.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                Call<GetUser> mUserCall = mApiInterface.postUser(body, reqNama, reqUmur, reqJenis,
                        reqWarna, reqUsername, reqPassword, reqAction );
                mUserCall.enqueue(new Callback<GetUser>() {
                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        if (response.body().getStatus().equals("failed")){
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "idUser = "+response.body().getResult().get(0).getId_user()+"\n"+
                                    "nama = "+response.body().getResult().get(0).getNama()+"\n"+
                                    "umur = "+response.body().getResult().get(0).getUmur()+"\n"+
                                    "jenisKulit = "+response.body().getResult().get(0).getJenis_kulit()+"\n"+
                                    "warnaKulit = "+response.body().getResult().get(0).getWarna_kulit()+"\n"+
                                    "username = "+response.body().getResult().get(0).getUsername()+"\n"+
                                    "password = "+response.body().getResult().get(0).getPassword()+"\n"+
                                    "photo_url_user = "+response.body().getResult().get(0).getPhotoUrlUser()
                                    +"\n";
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        tvAddMessage.setText("Retrofit Insert Failure \n Status = "+ t.getMessage
                                ());
                    }

                });
            }
        });
        btAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Login.class);
                startActivity(intent);
            }
        });
        btAddPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(
                        galleryIntent,
                        "Pilih foto untuk di-upload");
                startActivityForResult(intentChoose, 10);
            }
        });
        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    captureImage();
            }
        });
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Camera di device anda tidak tersedia", Toast.LENGTH_LONG).show();
                    finish();
        }
    }
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mFileURI = getMediaFileName(mFileURI);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    URI.create(String.valueOf(getMediaFileName(mFileURI))));
            startActivityForResult(takePictureIntent, 100);
        }
    }
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
// this device has a camera
            return true;
        } else {
// no camera on this device
            return false;
        }
    }
    private static File getMediaFileName(File mFileURI) {
// Lokasi External sdcard
        File mediaStorageDir = new
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "CameraDemo");
// Buat directori tidak direktori tidak eksis
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("CameraDemo", "Gagal membuat directory"+ "CameraDemo");
                return null;
            }
        }
// Membuat nama file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = null;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp +
                ".jpg");
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10){
            if (data==null){
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            }

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath =cursor.getString(columnIndex);

                Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
//                Glide.with(mContext).load(new File(imagePath)).into(mImageView);
                cursor.close();
            }else{
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
            }
        }
    }
}
