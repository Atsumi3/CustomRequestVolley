package info.nukoneko.tool.customrequestvolley.activity;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class BaseActivityAbstract extends AppCompatActivity {
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
