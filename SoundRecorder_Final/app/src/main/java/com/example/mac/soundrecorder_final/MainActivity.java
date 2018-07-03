package com.example.mac.soundrecorder_final;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;


import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;


import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity {
    public Recorder recorder = new Recorder();
    boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ShakeListener shakeListener = new ShakeListener(this);
        final PhoneListener phoneListener = new PhoneListener(this);
        final Vibrators vibrators = new Vibrators(this);
        final TextView inform = (TextView) findViewById(R.id.inform);
        final TextView status = (TextView) findViewById(R.id.status);
        final Button bn_start = (Button) findViewById(R.id.bn_start);
        bn_start.getBackground().setAlpha(100);
        final Button bn_end = (Button) findViewById(R.id.bn_end);
        final Button bn_open = (Button) findViewById(R.id.bn_open);
        final Button bn_apply = (Button) findViewById(R.id.bn_apply);
        bn_start.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("start_bn pressed!");
                firstTime = false;
                shakeListener.start();
                phoneListener.start();
                vibrators.start();
                bn_start.setClickable(false);
                bn_end.setClickable(true);
                status.setText("监听已启动");
                shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
                    public void onShake() {
                        recorder.onChange(inform, vibrators);
                    }
                });

                phoneListener.setOnPhoneListener(new PhoneListener.OnPhoneListener() {
                    @Override
                    public void onPhone() {
                        System.out.println("电话功能启动");
                        recorder.onChange(inform, vibrators);
                    }
                });
            }
        });
        bn_end.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("end_bn pressed!");
                if (firstTime) {
                    bn_start.setClickable(true);
                    bn_end.setClickable(false);
                    firstTime = false;
                } else {
                    recorder.stop(inform, vibrators);
                    shakeListener.stop();
                    phoneListener.stop();
                    vibrators.stop();
                    status.setText("监听已关闭");
                    bn_start.setClickable(true);
                    bn_end.setClickable(false);

                }
            }
        });
        bn_apply.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
                permissonItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "内存读", R.drawable.permission_ic_storage));
                permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "内存写", R.drawable.permission_ic_storage));
                permissonItems.add(new PermissionItem(Manifest.permission.RECORD_AUDIO, "麦克风", R.drawable.permission_ic_sensors));
                permissonItems.add(new PermissionItem(Manifest.permission.VIBRATE, "振动", R.drawable.permission_ic_sensors));
                System.out.println("bn-apply pressed!");
                HiPermission.create(MainActivity.this)
                        .title("第一次使用时请注意")
                        .permissions(permissonItems)
                        .msg("务必打开所有权限以免影响使用")
                        .animStyle(R.style.PermissionAnimScale)
                        .style(R.style.PermissionDefaultBlueStyle)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.i(TAG, "onClose");
                                showToast("用户关闭权限申请");
                            }

                            @Override
                            public void onFinish() {
                                showToast("所有权限申请完成");
                            }

                            @Override
                            public void onDeny(String permisson, int position) {
                                Log.i(TAG, "onDeny");
                            }

                            @Override
                            public void onGuarantee(String permisson, int position) {
                                Log.i(TAG, "onGuarantee");
                            }
                        });

            }
        });

        bn_open.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bn_file pressed!");
//                String storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
//                System.out.println(storagePath);
//                File storageDir = new File(storagePath);
//                Uri  photoURI = FileProvider.getUriForFile(view.getContext(), "com.example.mac.soundrecorder_final.fileprovider", storageDir );
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType(“image/*”);//选择图片
                //intent.setType(“audio/*”); //选择音频
                //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType(“video/*;image/*”);//同时选择视频和图片
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }

        });

        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE, "电话", R.drawable.permission_ic_phone));
        permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "内存写", R.drawable.permission_ic_storage));
        permissonItems.add(new PermissionItem(Manifest.permission.RECORD_AUDIO, "麦克风", R.drawable.permission_ic_sensors));
        System.out.println("bn-apply pressed!");
        HiPermission.create(MainActivity.this)
                .title("第一次使用时请注意")
                .permissions(permissonItems)
                .msg("务必打开所有权限以免影响使用")
                .animStyle(R.style.PermissionAnimScale)
                .style(R.style.PermissionDefaultBlueStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.i(TAG, "onClose");
                        showToast("用户关闭权限申请");
                    }

                    @Override
                    public void onFinish() {
                        showToast("所有权限申请完成");
                    }

                    @Override
                    public void onDeny(String permisson, int position) {
                        Log.i(TAG, "onDeny");
                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
                        Log.i(TAG, "onGuarantee");
                    }
                });
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
    String path;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();

                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);

                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);

                Toast.makeText(MainActivity.this, path+"222222", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {


        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;


        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];


                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {


                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];


                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }


                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};


                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {


        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};


        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}



