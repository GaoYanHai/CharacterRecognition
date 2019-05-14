package com.dashang.www.characterrecognition.module;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dashang.www.characterrecognition.R;
import com.dashang.www.characterrecognition.utils.SharedPreferencesUtil;
import com.dashang.www.characterrecognition.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    @BindView(R.id.iv_image)
    ImageView mIv_image;
    @BindView(R.id.bt_distinguish)
    Button mBt_distinguish;
    @BindView(R.id.tv_result)
    TextView mTv_result;
    @BindView(R.id.bt_takephoto)
    Button mBt_takephoto;
    @BindView(R.id.tb_copy)
    ToggleButton mTb_copy;
    @BindView(R.id.sw_contrast)
    Switch mSw_contrast;
    private MainPresenter mPresenter;
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private File mTmpFile;
    private Uri imageUrl;
    private static final String TAG = "MainActivity";
    private int IMAGE_REQUEST_CODE = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSw_contrast.setChecked(SharedPreferencesUtil.getBoolean(this,"SWITCH_STATE",false));
        mPresenter = new MainPresenter(this);
        //使textview中的内容可以滑动
        mTv_result.setMovementMethod(ScrollingMovementMethod.getInstance());
        mPresenter.getAccessToken();
        mBt_distinguish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到相册中选取图片
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
                mTb_copy.setClickable(true);
                mTb_copy.setChecked(false);
            }
        });
        mBt_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                mTb_copy.setClickable(true);
                mTb_copy.setChecked(false);
            }
        });
        mTb_copy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //按钮改变了状态
                    copyText();
                }
            }
        });

        mSw_contrast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e(TAG, "onCheckedChanged:  状态改变了"+b );
                SharedPreferencesUtil.putBoolean(MainActivity.this,"SWITCH_STATE",b);
            }
        });

    }

    private void takePhoto() {
        if (!hasPermission()) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image";
        if (new File(path).exists()) {
            try {
                new File(path).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mTmpFile = new File(path, filename + ".jpg");
        mTmpFile.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String authority = getPackageName() + ".provider";
            //应用之间共享文件
            imageUrl = FileProvider.getUriForFile(this, authority, mTmpFile);
            Log.e(TAG, "takePhoto: " + imageUrl);
        } else {
            imageUrl = Uri.fromFile(mTmpFile);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }


    @Override
    public void updateUI(String s) {
        mTv_result.setText(s);
    }

    @Override
    public void dataError(Throwable e) {
        mTv_result.setText(e.toString());

    }

    //检查是否有权限
    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    // 授予权限的结果
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        return;
                    }
                }
                takePhoto();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap photo = BitmapFactory.decodeFile(mTmpFile.getAbsolutePath());
            mPresenter.getRecognitionResultByImage(photo,this);
            mIv_image.setImageBitmap(photo);
        }

        if (requestCode == IMAGE_REQUEST_CODE) {
            if (data != null) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    mPresenter.getRecognitionResultByImage(bitmap,this);
                    mIv_image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deletePhoto() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/image");
//        File[] files=file.listFiles();
//        if (files == null){
//            Log.e("error","空目录");
//
//        }
        //获取目录下的文件的绝对路径
//        List<String> s = new ArrayList<>();
//        for(int i =0;i<files.length;i++){
//            s.add(files[i].getAbsolutePath());
//        }

        if (file.isDirectory()) {
            //处理目录
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                File file1 = new File(files[i].getAbsolutePath());

                if (file1.delete()) {
                    Log.e(TAG, "deletePhoto: 删除成功");
                } else {
                    Log.e(TAG, "deletePhoto: 删除失败");
                }
//                Log.e(TAG, "deletePhoto getName: "+files[i].getName() );
//                Log.e(TAG, "deletePhoto getPath: "+files[i].getPath() );
//                Log.e(TAG, "deletePhoto getAbsolutePath: "+files[i].getAbsolutePath() );
            }
        }

    }


    public void copyText(){
        ClipboardManager clipboardManager = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, mTv_result.getText());
        if (!TextUtils.isEmpty(clipData.toString())){
            clipboardManager.setPrimaryClip(clipData);
            mTb_copy.setClickable(false);
            Log.e(TAG, "copyText: 内容复制成功！" );
        }else {
            mTb_copy.setChecked(false);
            ToastUtil.showShort(getApplicationContext(),"复制内容为空！");
        }

    }


    //活动销毁时将照片删除
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //deletePhoto();
    }
}
