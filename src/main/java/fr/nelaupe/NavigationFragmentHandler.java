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
public class NavigationFragmentHandler implements FragmentManager.OnBackStackChangedListener {

    private final FragmentManager _fm;
    private final int _content;
    private FragmentChangeListener _changeListener;

    private boolean isPopAction;

    public NavigationFragmentHandler(Activity activity, int content) {
        _fm = activity.getFragmentManager();
        _fm.addOnBackStackChangedListener(this);
        _content = content;
        _changeListener = null;
        isPopAction = false;
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
        ft.replace(_content, target, generateTag());
        ft.commit();
    }

    public void replaceContent(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        removeBackStack(_fm);
        FragmentTransaction ft = _fm.beginTransaction();
        ft.replace(_content, target, generateTag());
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
        ft.replace(_content, target, generateTag());
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void pushContent(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        FragmentTransaction ft = _fm.beginTransaction();
        ft.replace(_content, target, generateTag());
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void pushContent(Fragment target, Bundle args) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(target);
        }
        FragmentTransaction ft = _fm.beginTransaction();
        target.setArguments(args);
        ft.replace(_content, target, generateTag());
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void popCurrentFragment() {
        isPopAction = true;
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

    @Override
    public void onBackStackChanged() {
        if(isPopAction) {
            isPopAction = false;
            if (_changeListener != null) {
                _changeListener.onChangeContent(getCurrentFragment());
            }
        }
    }

    private String generateTag() {
        return "NAVIGATION_FRAGMENT_HANDLER"+(getDeepness()+1);
    }

    private Fragment getCurrentFragment() {
        return _fm.findFragmentByTag("NAVIGATION_FRAGMENT_HANDLER"+(getDeepness()));
    }
}