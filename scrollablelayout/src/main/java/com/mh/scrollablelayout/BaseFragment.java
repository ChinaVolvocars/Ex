package com.mh.scrollablelayout;


import android.support.v4.app.Fragment;

import com.mh.scrollablelayout.lib.ScrollableHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements ScrollableHelper.ScrollableContainer {

    public abstract void pullToRefresh();

    public abstract void refreshComplete();
}
