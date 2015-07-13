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
public class NavigationFragmentHandler<TActivity extends Activity> {

    private final TActivity _self;
    private final int _content;
    private FragmentChangeListener _changeListener;

    public NavigationFragmentHandler(TActivity self, int content) {
        _self = self;
        _content = content;
        _changeListener = null;
    }

    public void showMain(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent();
        }
        FragmentManager fm = _self.getFragmentManager();
        removeBackStack(fm);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(_content, target);
        ft.commit();
    }

    public void replaceContent(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent();
        }
        FragmentManager fm = _self.getFragmentManager();
        removeBackStack(fm);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(_content, target);
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void replaceContent(Fragment target, Bundle args) {
        if (_changeListener != null) {
            _changeListener.onChangeContent();
        }
        FragmentManager fm = _self.getFragmentManager();
        removeBackStack(fm);
        FragmentTransaction ft = fm.beginTransaction();
        target.setArguments(args);
        ft.replace(_content, target);
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void pushContent(Fragment target) {
        if (_changeListener != null) {
            _changeListener.onChangeContent();
        }
        FragmentManager fm = _self.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(_content, target);
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void pushContent(Fragment target, Bundle args) {
        if (_changeListener != null) {
            _changeListener.onChangeContent();
        }
        FragmentManager fm = _self.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        target.setArguments(args);
        ft.replace(_content, target);
        ft.addToBackStack(target.toString());
        ft.commit();
    }

    public void popCurrentFragment() {
        if (_changeListener != null) {
            _changeListener.onChangeContent();
        }
        FragmentManager fm = _self.getFragmentManager();
        fm.popBackStackImmediate();
    }

    private void removeBackStack(FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public int getDeepness() {
        return _self.getFragmentManager().getBackStackEntryCount();
    }


    public void setOnFragmentChangeListener(FragmentChangeListener listener) {
        _changeListener = listener;
    }

}