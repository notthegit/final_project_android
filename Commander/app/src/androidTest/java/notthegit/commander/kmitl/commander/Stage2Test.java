package notthegit.commander.kmitl.commander;

import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
public class Stage2Test {
    @Rule
    public ActivityTestRule<Stage2> mActivityTestRule = new ActivityTestRule<>(Stage2.class);
    @Test
    public void testLoseDidNothing() {
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("HQ : Tank are crossing the bridge.\n Mission Fail.")));
        SystemClock.sleep(1000);
    }
    @Test
    public void testLoseInfantry() {
        ViewInteraction imageView = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 2), 3), isDisplayed()));
        imageView.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("HQ : Infantry cross the river.\n Mission Fail.")));
        SystemClock.sleep(1000);
    }
    @Test
    public void testLoseBridging() {
        ViewInteraction imageView = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 2), 3), isDisplayed()));
        imageView.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        ViewInteraction imageView1 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 0), 1), isDisplayed()));
        imageView1.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        ViewInteraction imageView2 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 1), 2), isDisplayed()));
        imageView2.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        ViewInteraction imageView3 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 4), 3), isDisplayed()));
        imageView3.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("HQ : Bridging repair the bridge and tank are crossing the bridge.\n Mission Fail.")));
        SystemClock.sleep(1000);
    }
    @Test
    public void testWin() {
        ViewInteraction imageView = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 2), 3), isDisplayed()));
        imageView.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        ViewInteraction imageView1 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 0), 1), isDisplayed()));
        imageView1.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        ViewInteraction imageView2 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 1), 2), isDisplayed()));
        imageView2.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        ViewInteraction imageView3 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 4), 3), isDisplayed()));
        imageView3.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        ViewInteraction imageView4 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage2), 2), 2), isDisplayed()));
        imageView4.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("HQ : All enemy have been stop at bridge")));
        SystemClock.sleep(1000);
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
