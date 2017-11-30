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
        ViewInteraction imageView = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage1), 1), 2), isDisplayed()));
        imageView.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("Sniper cooked sir. Continue reconnaissance.")));
        SystemClock.sleep(1000);
    }
    @Test
    public void testLoseBlueOnBlue0() {
        ViewInteraction imageView = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage1), 1), 1), isDisplayed()));
        imageView.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("Commissar : You're fired from life BLAM!")));
        SystemClock.sleep(1000);
    }
    @Test
    public void testLoseBlueOnBlue1() {
        ViewInteraction imageView = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage1), 2), 4), isDisplayed()));
        imageView.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("Commissar : You're fired from life BLAM!")));
    }
    @Test
    public void testLoseBlueOnBlue2() {
        ViewInteraction imageView = onView(allOf(childAtPosition(childAtPosition(withId(R.id.stage1), 0), 0), isDisplayed()));
        imageView.perform(click());
        onView(allOf(withId(R.id.endTurn_btn), withText("End Turn"))).perform(click());
        onView(withId(R.id.radio)).check(matches(withText("Commissar : You're fired from life BLAM!")));
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
