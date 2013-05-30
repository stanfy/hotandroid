package com.stanfy.hotcode.part1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Initial activity with one BIG button. Just to show how you can use Intents to start another activities.
 */
public class InitialActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // <-- you MUST call correspondent super class method in any life cycle method

        setContentView(R.layout.initial_activity);
        findViewById(R.id.initial_activity_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.ACTION_START_EXAMPLE));
            }
        });
    }
}
