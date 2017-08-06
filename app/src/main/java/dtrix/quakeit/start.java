package dtrix.quakeit;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class start extends AppCompatActivity {

    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

       /* username = (EditText) findViewById(R.id.editText5);
        password = (EditText) findViewById(R.id.editText4);*/

    }

    public void didTapButton(View view) {
        final Button button = (Button) findViewById(R.id.button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 5);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);

        Intent in = new Intent(start.this, MainActivity.class);
        Toast.makeText(start.this, "Your Welcome!!!", Toast.LENGTH_SHORT).show();

        startActivity(in);
    }

}
