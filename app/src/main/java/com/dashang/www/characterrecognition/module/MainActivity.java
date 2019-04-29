package com.dashang.www.characterrecognition.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dashang.www.characterrecognition.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    @BindView(R.id.iv_image)
    ImageView mIv_image;
    @BindView(R.id.bt_distinguish)
    Button mBt_distinguish;
    @BindView(R.id.tv_result)
    TextView mTv_result;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);

        mBt_distinguish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getData();
            }
        });

    }

    @Override
    public void setData() {

    }

    @Override
    public void dataError(Throwable e) {
        mTv_result.setText(e.toString());
    }
}
