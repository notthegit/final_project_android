package notthegit.commander.kmitl.commander;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Stage1Test {
    @Rule
    public ActivityTestRule<Stage1> mActivityTestRule = new ActivityTestRule<>(Stage1.class);
    @Test
    public void testLoseDidNothing() {
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("HQ : We lost contact with all squads\nMission Fail! Also commissar want to see you.")));
        SystemClock.sleep(1000);
    }
    @Test
    public void testWin() {
        //onView(allOf(withId(R.id.ivCell[1][2]))).perform(click());
        //onView(withId(R.id.radio)).check(matches(withText("Sniper cooked sir. Continue reconnaissance.")));
        //SystemClock.sleep(1000);
    }
}
