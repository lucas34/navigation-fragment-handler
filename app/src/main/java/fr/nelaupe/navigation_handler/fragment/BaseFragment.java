/**
 * Copyright
 */
package fr.nelaupe.navigation_handler.fragment;

import android.app.Fragment;
import android.os.Bundle;

import fr.nelaupe.NavigationFragmentHandler;
import fr.nelaupe.navigation_handler.MainActivity;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class BaseFragment extends Fragment {

    NavigationFragmentHandler<BaseFragment> mFragmentHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentHandler = ((MainActivity) getActivity()).getHandler();
    }

    protected NavigationFragmentHandler<BaseFragment> navigationHandler() {
        return mFragmentHandler;
    }

}
