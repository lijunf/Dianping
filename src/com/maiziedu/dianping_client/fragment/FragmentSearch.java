package com.maiziedu.dianping_client.fragment;

import com.maiziedu.dianping_client.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//·¢ÏÖ
public class FragmentSearch extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_index, null);
		return view;
	}
}
