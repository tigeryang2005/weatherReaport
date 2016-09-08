package com.example.tiger.weather;

import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.tiger.weather.activity.WeatherActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Created by tiger on 16/9/1.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@LargeTest
public class WeatherActivityTest extends ActivityInstrumentationTestCase2 {

    @Rule
    public ActivityTestRule<WeatherActivity> mActivityRule = new ActivityTestRule<>(WeatherActivity.class);

    //private UiDevice mDevice;
    private String mStringToBetyped;

    public WeatherActivityTest() {
        super(WeatherActivity.class);
    }

    @Before
    public void initValidString() {
        mStringToBetyped = "Espreso test";
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        //injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        getActivity();
        //mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //Espresso.registerIdlingResources(new WeatherActivity(getActivity().findViewById(R.id.weather_info_layout)));
    }

//    @Test
//    public void checkPreCondition() {
//        assertThat(mDevice, notNullValue());
//    }

    @Test
    public void testWeatherActivity() {
        //mDevice.findObject(By.desc("应用")).click();
        //mDevice.wait(Until.hasObject(By.desc("Weather")), 500);
        //mDevice.findObject(By.desc("Weather")).click();
        onView(withId(R.id.refresh_weather)).check(matches(isDisplayed()));
        //onView(withId(R.id.weather_desp)).perform(typeText("test123"), closeSoftKeyboard());
        onView(withId(R.id.switch_city)).check(matches(isDisplayed()));
        onView(withId(R.id.switch_city)).perform(click());
        Log.d("点击城市选择按键", "");
        onView(withId(R.id.list_view)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(1).perform(click());
        onView(withId(R.id.title_text)).check(matches(withText("上海")));
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(0).perform(click());
        onView(withId(R.id.title_text)).check(matches(withText("上海")));
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(1).perform(click()).check(doesNotExist());
        //onView(withId(R.id.weather_info_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.city_name)).check(matches(withText("闵行")));
        //onView(withText(R.id.weather_desp)).check(matches(isDisplayed()));
        onView(withId(R.id.weather_desp)).check(matches(withText("多云")));
        onView(withId(R.id.weather_desp)).perform(typeText(mStringToBetyped), closeSoftKeyboard());
        //onView(withText(R.id.weather_desp)).check(matches(isDisplayed()));
        //onView(withId(R.id.weather_desp)).perform(typeText("123fortest"), closeSoftKeyboard());
        onView(withId(R.id.refresh_weather)).perform(click());
        //onView(withId(R.id.weather_desp)).perform(typeText("test for weather desp"));
        //onView(withId(R.id.weather_desp)).check(matches(withText("test for weather desp")));
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
