package androidavengers.correctu.storage_room_screen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import androidavengers.correctu.R;

public class StorageRoomActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_room);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(StorageRoomActivity.this,newConfig.toString(), Toast.LENGTH_LONG).show();
        if(newConfig.orientation == Configuration.KEYBOARDHIDDEN_NO){
            Toast.makeText(StorageRoomActivity.this,"keyboard visible", Toast.LENGTH_LONG).show();
            mToolbar.setVisibility(View.GONE);
            setSupportActionBar(mToolbar);
        }
        if(newConfig.orientation == Configuration.KEYBOARDHIDDEN_YES){
            Toast.makeText(StorageRoomActivity.this,"keyboard hidden", Toast.LENGTH_LONG).show();
            mToolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(mToolbar);
        }

    }
}
