package com.mh.scrollablelayout;


import android.support.v4.app.Fragment;

import com.mh.scrollablelayout.lib.ScrollableHelper;


public abstract class BaseFragmentScrollable extends Fragment implements ScrollableHelper.ScrollableContainer {

    public abstract void pullToRefresh();

    public abstract void refreshComplete();
}
