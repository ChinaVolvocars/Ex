package com.mh.ex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class APPFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = View.inflate(getActivity(), R.layout.appfragment, null);
			ListView viewById =(ListView) view.findViewById(R.id.listivew);
			viewById.setAdapter(new ad());
			return view;
		}
	class ad extends BaseAdapter{

		@Override
		public int getCount() {
			return 100;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view=new TextView(getActivity());
			view.setText("哈哈哈哈哈哈");

			return view;
		}
	}
}
