package com.a123;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.a123.application.MyApp;
import com.a123.custome.CustomActivity;
import com.a123.model.AppointmentData;
import com.a123.utills.AppConstant;
import com.baoyz.actionsheet.ActionSheet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

public class PictureActivity extends CustomActivity implements ActionSheet.ActionSheetListener, CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private ImageView img_property_image;
    private TextView tv_capture_pic, tv_submit;
    private EditText edt_description;
    private static int SELECT_PICTURE = 1;
    Bitmap bitmap;
    private File mFileTemp;
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CROP_ICON = 0x3;
    private String fileString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        setResponseListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("");
        actionBar.setTitle("");
        setupUiElement();

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted");
                //File write logic here

            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
            }
            //do your check here
        }

        setImagePath("pic");
    }


    private void setupUiElement() {
        setTouchNClick(R.id.tv_capture_pic);
        setTouchNClick(R.id.tv_submit);

        img_property_image = (ImageView) findViewById(R.id.img_property_image);

        tv_capture_pic = (TextView) findViewById(R.id.tv_capture_pic);
        tv_submit = (TextView) findViewById(R.id.tv_submit);

        edt_description = (EditText) findViewById(R.id.edt_description);

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_capture_pic) {
            setTheme(R.style.ActionSheetStyleiOS7);
            showActionSheet();
        } else if (v.getId() == R.id.tv_submit) {
            submitId(fileString);
        }

    }


    private void setImagePath(String name) {
        File sdIconStorageDir = new File(
                Environment.getExternalStorageDirectory()
                        + "/Casa/Pictures/");
        sdIconStorageDir.mkdirs();
        mFileTemp = new File(Environment.getExternalStorageDirectory()
                + "/Casa/Pictures/", name + ".jpg");

    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (mFileTemp != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        mFileTemp);
//                Uri photoURI = Uri.parse(mFileTemp.getAbsolutePath().toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);
            }
        }
    }


    private void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }


    private void submitId(String path) {
        RequestParams p = new RequestParams();
        p.put("client_id", MyApp.getApplication().readUser().get(0).getId());
        p.put("email", MyApp.getApplication().readUser().get(0).getEmail());
        String contentType = RequestParams.APPLICATION_OCTET_STREAM;
        try {
            p.put("client_id_path", mFileTemp, contentType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //p.put("client_id_path", bitmap);
        p.put("client_id_discription", edt_description.getText().toString());
        p.put("socialLoginType", MyApp.getApplication().readUser().get(0).getSocialLoginType());
        p.put("appVersion", MyApp.getApplication().readUser().get(0).getAppVersion());
        p.put("deviceType", MyApp.getApplication().readUser().get(0).getDeviceType());

        postCall(getContext(), AppConstant.BASE_URL + "submitId", p, "Submitting...", 1);
    }


    @Override


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
            /*
             * The case if image selected from device gallery
			 */
                case REQUEST_CODE_GALLERY:

                    try {
                        InputStream inputStream = getContentResolver()
                                .openInputStream(data.getData());
                        FileOutputStream fileOutputStream = new FileOutputStream(
                                mFileTemp);
                        copyStream(inputStream, fileOutputStream);
                        fileOutputStream.close();
                        inputStream.close();
                        cropImage(rotateImage(getContext(), mFileTemp));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

			/*
             * The case if image is captured from camera
			 */
                case REQUEST_CODE_TAKE_PICTURE:

                    cropImage(rotateImage(getContext(), mFileTemp));
                    break;

                case REQUEST_CROP_ICON:
                    if (data != null) {
                        Bitmap photo = data.getExtras().getParcelable("data");

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        try {
                            mFileTemp.createNewFile();
                            FileOutputStream fo = new FileOutputStream(mFileTemp);
                            fo.write(bytes.toByteArray());
                            fo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;

            }
        }
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private void cropImage(File mFileTemp) {
        long length = mFileTemp.length() / 1024; // Size in KB

//        if (length > 600) {
//            mFileTemp = saveBitmapToFile(mFileTemp);
//
//        }
        fileString = mFileTemp.getAbsolutePath();
        File file = new File(fileString);
        Uri imageUri = Uri.fromFile(file);


        Picasso.with(this).load(imageUri).into(img_property_image);
        //Picasso.with(getContext()).load(imageUri).apply(RequestOptions.bitmapTransform(new BlurTransformation(25))).into(img_bg);
    }


    public static File rotateImage(Context context, final File path) {
        Bitmap b = decodeFileFromPath(context, path.getAbsolutePath());
        try {
            ExifInterface ei = new ExifInterface(path.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Log.d("Orientation", "Orientation value : " + orientation);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
                default:
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        FileOutputStream out1 = null;
//        File file;
        try {
//            String state = Environment.getExternalStorageState();
//            if (Environment.MEDIA_MOUNTED.equals(state)) {
//                file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
//                Log.d("PictureUtils","uses environmment media mounted : "+Environment.getExternalStorageDirectory());
//                Log.d("PicturesUtils","file path :"+file.getPath());
//                Log.d("PicturesUtils","file name :"+file.getName());
//            }
//            else {
//                file = new File(context.getFilesDir() , "image" + new Date().getTime() + ".jpg");
//                Log.d("PictureUtils","uses getFilesDire : "+context.getFilesDir());
//            }
            out1 = new FileOutputStream(path);
            b.compress(Bitmap.CompressFormat.JPEG, 90, out1);
//            imgRotatedPath = file.getAbsolutePath();
            //imgFromCameraOrGallery.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out1.close();
            } catch (Throwable ignore) {

            }
        }
        return path;
    }

    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    public static Bitmap decodeFileFromPath(Context context, String path) {
        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = context.getContentResolver().openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            int inSampleSize = 1024;
            if (o.outHeight > inSampleSize || o.outWidth > inSampleSize) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(inSampleSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = context.getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                try {
                    // We need to recyle unused bitmaps
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    stream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);

                    img_property_image = (ImageView) findViewById(R.id.img_property_image);
                    img_property_image.setImageBitmap(bitmap);

                    File sdIconStorageDir = new File(
                            Environment.getExternalStorageDirectory()
                                    + "/Casa/Pictures/");
                    sdIconStorageDir.mkdirs();
                    mFileTemp = new File(Environment.getExternalStorageDirectory()
                            + "/Casa/Pictures/", "pic"+ ".jpg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (stream != null)
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
    }
*/

    public void showActionSheet() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("Take Photo", "Choose Photo")
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    private Context getContext() {
        return PictureActivity.this;
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        if (index == 0) {
            Toast.makeText(getContext(), "Open up the Camera", Toast.LENGTH_SHORT).show();
        } else if (index == 1) {
            /*Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, SELECT_PICTURE);*/
            openGallery();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

        if (callNumber == 1) {
            if (o.optString("status").equals("1")) {

                MyApp.popMessage("Successful", o.optString("message"), getContext());
                finish();

            } else {
                MyApp.popMessage("Error", o.optString("message"), getContext());
            }
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }
}
