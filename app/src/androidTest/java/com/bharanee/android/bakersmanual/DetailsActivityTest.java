package com.bharanee.android.bakersmanual;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {
    @Rule
    public ActivityTestRule<HomeScreen> homeScreenActivityTestRule=new ActivityTestRule<>(HomeScreen.class);
    @Rule
    public IntentsTestRule<DetailsPage> detailsActivityTestActivityTestRule=new IntentsTestRule<>(DetailsPage.class,false,false);

    @Test
    public void getIntentfromHomeScreen(){
        Intent intent=new Intent(homeScreenActivityTestRule.getActivity(),DetailsPage.class);
        intent.putExtra("position",1);
        intent.putExtra("fragment","steps_short");
        detailsActivityTestActivityTestRule.launchActivity(intent);
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(3).
                check( matches(withText("Melt butter and bittersweet chocolate.")));

    }
}
