package com.bharanee.android.bakersmanual;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {
    @Rule
    public IntentsTestRule<HomeScreen> homeScreenActivityTestRule=new IntentsTestRule<>(HomeScreen.class);

    @Test
    public void testHomeScreen(){
        try {
            //Delay to have list available for test
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.items_rv))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText("Brownies")),
                        click()));

        intended(allOf( hasExtra("position",1)));
    }
}
