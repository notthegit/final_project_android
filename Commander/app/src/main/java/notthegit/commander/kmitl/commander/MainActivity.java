package notthegit.commander.kmitl.commander;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions(Arrays.asList("name", "email"));
        final Button post = findViewById(R.id.post_btn);
        post.setVisibility(View.GONE);



        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //GraphRequest request = GraphRequest.newMeRequest(
                        //        loginResult.getAccessToken(),
                        //        new GraphRequest.GraphJSONObjectCallback() {
                        //            @Override
                        //            public void onCompleted(JSONObject object, GraphResponse response) {

                                        // Application code
                        //                String email = object.getString("email");
                        //                String birthday = object.getString("birthday"); // 01/31/1980 format
                        //            }
                        //        });
                        //Bundle parameters = new Bundle();
                        //parameters.putString("fields", "name,email");
                        //request.setParameters(parameters);
                        //request.executeAsync();
                        post.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }
    public void clickExit(View view){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void clickLevel(View view){
        Intent level = new Intent(this,Stage1.class);
        startActivity(level);
    }
    public void clickHelp(View view){
        Intent level = new Intent(this,HelpActivity.class);
        startActivity(level);
    }
    public void clickPost(View view){
        //Bitmap image = getDrawable(R.drawable.logo);
        //SharePhoto photo = new SharePhoto.Builder()
        //        .setBitmap(image)
        //        .build();
        //SharePhotoContent content = new SharePhotoContent.Builder()
        //        .addPhoto(photo)
        //        .build();
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}

