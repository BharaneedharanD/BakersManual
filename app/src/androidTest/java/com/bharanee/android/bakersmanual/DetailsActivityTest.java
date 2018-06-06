package com.bharanee.android.bakersmanual;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {
    int test_position_item=3,test_position_ingredient=0;
    @Rule
    public ActivityTestRule<HomeScreen> homeScreenActivityTestRule=new ActivityTestRule<>(HomeScreen.class);
    @Rule
    public IntentsTestRule<DetailsPage> detailsActivityTestActivityTestRule=new IntentsTestRule<>(DetailsPage.class,false,false);
    @Before
    public void initActivity(){
        Intent intent=new Intent(homeScreenActivityTestRule.getActivity(),DetailsPage.class);
        intent.putExtra("position",1);
        intent.putExtra("fragment","steps_short");
        detailsActivityTestActivityTestRule.launchActivity(intent);
    }
    @Test
    public void getIntentfromHomeScreen(){
        String test_Step = "Melt butter and bittersweet chocolate.";
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(test_position_item).
                check( matches(withText(test_Step)));


    }
    @Test
    public void test_IngredientsList(){
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(test_position_ingredient).perform(click());
        String test_ingredient = "light brown sugar 100 G";
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(test_position_item).
                check( matches(withText(test_ingredient)));
    }
}
