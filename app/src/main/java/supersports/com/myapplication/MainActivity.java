package supersports.com.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import supersports.com.libs.annotation.Failure;
import supersports.com.libs.annotation.Success;
import supersports.com.libs.cheker.PermissionChecker;

public class MainActivity extends AppCompatActivity {

    /**
     * Hello World!
     */
    private TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxt = findViewById(R.id.txt);
        mTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionChecker.request(new String[]{Manifest.permission.CALL_PHONE}, 200);
            }
        });

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            PermissionChecker.onRequestPermissionsResult(requestCode,grantResults);
    }


    @Success(code = 200)
    public   void   onSuccess(){
        Toast.makeText(MainActivity.this,"所有权限已经被授予",Toast.LENGTH_LONG).show();
    }


    @Failure(code = 200)
    public  void  onFailure(){

        Toast.makeText(MainActivity.this,"权限未授予",Toast.LENGTH_LONG).show();
    }


}
