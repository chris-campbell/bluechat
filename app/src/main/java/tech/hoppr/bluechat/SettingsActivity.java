package tech.hoppr.bluechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Button updateAccountSettings;
    private EditText userName, userStatus;
    private CircleImageView userProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        initializeField();
    }

    private void initializeField() {
        updateAccountSettings = findViewById(R.id.update_settings_button);
        userName = findViewById(R.id.set_user_name);
        userStatus = findViewById(R.id.set_user_status);
        userProfileImage = findViewById(R.id.set_profile_image);
    }
}