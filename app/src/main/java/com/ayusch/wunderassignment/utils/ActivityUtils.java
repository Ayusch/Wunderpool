package com.ayusch.wunderassignment.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ayusch.wunderassignment.R;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static android.support.v4.util.Preconditions.checkNotNull;

public class ActivityUtils {

    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity(@NonNull FragmentManager manager,
                                             @NonNull Fragment fragment, int frameId, String tag) {
        checkNotNull(manager);
        checkNotNull(fragment);
        String backStateName = fragment.getClass().getName();

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(tag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(frameId, fragment, tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(tag);
            ft.commit();
        } else {
            Fragment currentfrag = manager.findFragmentById(R.id.content_frame);
            if (currentfrag != null)
                if (!currentfrag.getTag().equals(tag))
                    manager.popBackStack();

        }

    }

   /* public static void viewFragment(final int selectedItemId, final BottomNavigationView bottomNavigation, final FragmentManager supportFragmentManager, Fragment fragment, String name) {

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        // 1. Know how many fragments there are in the stack
        final int count = supportFragmentManager.getBackStackEntryCount();
        // 2. If the fragment is **not** "car type", save it to the stack
        if (name.equals(Const.MAP_FRAGMENT)) {
            fragmentTransaction.addToBackStack(name);
        }
        // Commit !
        fragmentTransaction.commit();
        // 3. After the commit, if the fragment is not an "car type" the back stack is changed, triggering the
        // OnBackStackChanged callback
        supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if (supportFragmentManager.getBackStackEntryCount() <= count) {
                    // pop all the fragment and remove the listener
                    supportFragmentManager.popBackStack(Const.MAP_FRAGMENT, POP_BACK_STACK_INCLUSIVE);
                    supportFragmentManager.removeOnBackStackChangedListener(this);
                    // set the home button selected
                    if (bottomNavigation != null) {
                        bottomNavigation.setSelectedItemId(selectedItemId);
                    }
                }
            }
        });
    }*/
}
