package com.madgag.android.lazydrawables.samples;

import static com.madgag.android.lazydrawables.gravatar.Gravatars.gravatarIdFor;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.madgag.android.lazydrawables.AsyncLoadDrawable;
import com.madgag.android.lazydrawables.ImageSession;

public class GravatarListAdapter extends BaseAdapter {
	private final LayoutInflater layoutInflater;
	private final ImageSession<String, Bitmap> imageSession;
	private List<String> emailAddresses;
	private final Drawable downloadingDrawable;

	public GravatarListAdapter(LayoutInflater layoutInflater, List<String> emailAddresses, ImageSession<String, Bitmap> imageSession, Drawable downloadingDrawable) {
		this.layoutInflater = layoutInflater;
		this.emailAddresses = emailAddresses;
		this.imageSession = imageSession;
		this.downloadingDrawable = downloadingDrawable;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	public long getItemId(int i) {
		return emailAddresses.hashCode();
	}
	
	public int getCount() {
		return emailAddresses.size();
	}

	public Object getItem(int index) {
		return emailAddresses.get(index);
	}

	public View getView(int index, View convertView, ViewGroup parent) {
		convertView = createViewIfNecessary(convertView);
		
		ViewHolder holder = (ViewHolder) convertView.getTag();
		
		holder.updateViewFor(emailAddresses.get(index));

		return convertView;
	}

	private View createViewIfNecessary(View convertView) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.demo_list_item, null);
			convertView.setTag(new ViewHolder(convertView));
		}
		return convertView;
	}


	class ViewHolder {
		private final TextView detailView,titleView;
		private final ImageView icon;
		
		public ViewHolder(View v) {
			titleView = (TextView) v.findViewById(R.id.demo_list_item_title);
			detailView = (TextView) v.findViewById(R.id.demo_list_item_detail);
			icon = (ImageView) v.findViewById(R.id.demo_list_item_icon);
		}
		
		public void updateViewFor(String emailAddress) {
			titleView.setText(emailAddress);
			detailView.setText(emailAddress.length()+" characters");
			
			Drawable avatarDrawable = imageSession.get(gravatarIdFor(emailAddress));
			icon.setImageDrawable(avatarDrawable);
			icon.setScaleType(ScaleType.CENTER);
//			if (avatarDrawable instanceof AsyncLoadDrawable) {
//				((AsyncLoadDrawable) avatarDrawable).setDelegate(downloadingDrawable.mutate());
//			}
			
//			RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
//			anim.setInterpolator(new LinearInterpolator());
//			anim.setRepeatCount(Animation.INFINITE);
//			anim.setDuration(700);
//			
//			icon.startAnimation(anim);
		}
	}

}