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
@SuppressWarnings({"unused", "unchecked"})
public class NavigationFragmentHandler<TFragment extends Fragment> {

    private final FragmentManager _fm;
    private final int _content;
    private FragmentChangeListener<TFragment> _changeListener;

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

    public void showMain(TFragment target) {
        notifyChange(target);
        removeBackStack(_fm);
        transactionReplace(target, generateTag(0));
    }

    public void replaceContent(TFragment target) {
        replaceContent(target, null);
    }

    public void replaceContent(TFragment target, Bundle args) {
        notifyChange(target);
        removeBackStack(_fm);
        target.setArguments(args);
        transactionReplace(target, generateTag(1));
    }

    public void pushContent(TFragment target) {
        pushContent(target, null);
    }

    public void pushContent(TFragment target, Bundle args) {
        notifyChange(target);
        target.setArguments(args);
        transactionReplace(target, generateTag(getDeepness() + 1));
    }

    public void popCurrentFragment() {
        notifyChange(getPreviousFragment());
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

    public void setOnFragmentChangeListener(FragmentChangeListener<TFragment> listener) {
        _changeListener = listener;
    }

    public TFragment getCurrentFragment() {
        return (TFragment) _fm.findFragmentByTag("NAVIGATION_FRAGMENT_HANDLER" + (getDeepness()));
    }

    private String generateTag(int offset) {
        return "NAVIGATION_FRAGMENT_HANDLER" + (offset);
    }

    private TFragment getPreviousFragment() {
        if (getDeepness() == 0) {
            return (TFragment) _fm.findFragmentByTag("NAVIGATION_FRAGMENT_HANDLER0");
        } else {
            return (TFragment) _fm.findFragmentByTag("NAVIGATION_FRAGMENT_HANDLER" + (getDeepness() - 1));
        }
    }

    private void transactionReplace(TFragment target, String tag) {
        FragmentTransaction ft = _fm.beginTransaction();
        ft.replace(_content, target, tag);
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    private void notifyChange(TFragment fragment) {
        if (_changeListener != null) {
            _changeListener.onChangeContent(fragment);
        }
    }

}