package music_m.music_mining.play;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import music_m.music_mining.R;

/**
 * Created by jongjookim on 2017. 1. 3..
 */

public class Play extends Activity {

    private ViewPager mViewPager;

    private NavigationTabStrip mCenterNavigationTabStrip;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        initUI();
        setUI();
    }

    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.vp);

        mCenterNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts_center);

    }

    private void setUI() {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = new View(getBaseContext());
                container.addView(view);
                return view;
            }
        });


        mCenterNavigationTabStrip.setViewPager(mViewPager, 1);
    }
}
