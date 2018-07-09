package com.andela.android.javadevelopers;

import android.app.Activity;
import android.app.ProgressDialog;

import com.andela.android.javadevelopers.home.view.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowProgressDialog;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by chike on 26/03/2018.
 */

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
//    private MainActivity activity;
    private ShadowProgressDialog shadow;
    private Activity activity;
    private ActivityController controller;
//    Bundle savedInstance = new Bundle()


    @Before
    public void setup() {
//        activity = Robolectric.setupActivity(MainActivity.class);
//        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        controller = Robolectric.buildActivity(MainActivity.class).create().start();
        activity = (Activity) controller.get();
//        ProgressDialog dialog = ProgressDialog
//                .show(activity.getApplicationContext(), "Kenya Java Developers",
//                "Loading... Please wait!!!", false, false);
//        shadow = org.robolectric.Shadows.shadowOf(dialog);


    }

    @Test
    public void shouldStartLoginActivity() {
//        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
//        assertEquals(recyclerView.isAttachedToWindow(), true);
        ProgressDialog dialog = ProgressDialog
                .show(activity.getApplicationContext(), "Kenya Java Developers",
                "Loading... Please wait!!!", false, false);
        assertTrue(dialog.isShowing());
        dialog.dismiss();
        assertFalse(dialog.isShowing());
        controller.resume();

    }

//    @Test
//    public void shouldSetMessage() {
//        CharSequence message = "Loading... Please wait!!!";
//        assertEquals(shadow.getMessage(), message);
//        assertEquals(shadow.hasBeenDismissed(), false);
//    }
}
