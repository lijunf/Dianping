package com.maiziedu.dianping_client.fragment;

import com.maiziedu.dianping_client.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//ÎÒµÄ
public class FragmentMy extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_index, null);
		return view;
	}
}
