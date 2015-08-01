package info.nukoneko.tool.customrequestvolley.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;

import butterknife.Bind;
import butterknife.OnClick;
import info.nukoneko.tool.customrequestvolley.R;
import info.nukoneko.tool.customrequestvolley.client.VolleyClient;

public class MainActivity extends BaseActivityAbstract {
    @Bind(R.id.url)
    EditText urlEdit;
    @Bind(R.id.response)
    TextView responseView;

    @OnClick(R.id.button) void onClick(){
        // Example
        new VolleyClient<>(this, urlEdit.getText().toString(), String.class, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseView.setText(response);
            }
        });
        // /Example
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
