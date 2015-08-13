/*
 * Copyright 2015-present Lucas Nelaupe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.nelaupe;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created with IntelliJ
 * Created by Lucas Nelaupe
 * Date 24/03/15
 */
@SuppressWarnings("unused")
public class NavigationFragmentHandler {

    private final FragmentManager _fm;
    private final int _content;
    private FragmentChangeListener _changeListener;

    public NavigationFragmentHandler(Activity activity, int content) {
        _fm = activity.getFragmentManager();
        _content = content;
        _changeListener = null;
    }

    public NavigationFragmentHandler(FragmentManager fragmentManager, int content) {
        _fm = fragmentManager;
        _content = content;
        _changeListener = null;
    }

    public void showMain(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        removeBackStack(_fm);
        FragmentTransaction ft = _fm.beginTransaction();
        ft.replace(_content, target, generateTag(0));
        ft.commit();
    }

    public void replaceContent(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        removeBackStack(_fm);
        FragmentTransaction ft = _fm.beginTransaction();
        ft.replace(_content, target, generateTag(1));
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void replaceContent(Fragment target, Bundle args) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        removeBackStack(_fm);
        FragmentTransaction ft = _fm.beginTransaction();
        target.setArguments(args);
        ft.replace(_content, target, generateTag(1));
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void pushContent(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        FragmentTransaction ft = _fm.beginTransaction();
        ft.replace(_content, target, generateTag(getDeepness() + 1));
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void pushContent(Fragment target, Bundle args) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        FragmentTransaction ft = _fm.beginTransaction();
        target.setArguments(args);
        ft.replace(_content, target, generateTag(getDeepness() + 1));
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void popCurrentFragment() {
        if (_changeListener != null) {
            _changeListener.onChangeContent(getPreviousFragment());
        }
        _fm.popBackStackImmediate();
    }

    private void removeBackStack(FragmentManager fm) {
        for (int i = 0; i < _fm.getBackStackEntryCount(); ++i) {
            _fm.popBackStack();
        }
    }

    public int getDeepness() {
        return _fm.getBackStackEntryCount();
    }

    public void setOnFragmentChangeListener(FragmentChangeListener listener) {
        _changeListener = listener;
    }

    private String generateTag(int offset) {
        return "NAVIGATION_FRAGMENT_HANDLER" + (offset);
    }

    private Fragment getPreviousFragment() {
        if (getDeepness() == 0) {
            return _fm.findFragmentByTag("NAVIGATION_FRAGMENT_HANDLER0");
        } else {
            return _fm.findFragmentByTag("NAVIGATION_FRAGMENT_HANDLER" + (getDeepness() - 1));
        }
    }

    public Fragment getCurrentFragment() {
        return _fm.findFragmentByTag("NAVIGATION_FRAGMENT_HANDLER" + (getDeepness()));
    }

}