package com.fmlditital.emp.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fmlditital.emp.R;
import com.fmlditital.emp.config.Confi;
import com.fmlditital.emp.config.UIConfig;
import com.fmlditital.emp.dowmload.DownloadManager;
import com.fmlditital.emp.model.DownloadModel;
import com.fmlditital.emp.tool.NetWorkState;
import com.fmlditital.emp.tool.Tools;

public class DownloadingAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<DownloadModel> data;
	private Context context = null;
	private UIConfig uiConfig = Confi.getInstance().getuIConfig();

	public DownloadingAdapter(Context context, List<DownloadModel> data) {
		mInflater = LayoutInflater.from(context);
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (DownloadModel) data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int positionTemp = position;
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.downloading_iist_item,
					null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.download_iist_item_title);
			holder.size = (TextView) convertView
					.findViewById(R.id.download_iist_item_size);
			holder.progressBar = (ProgressBar) convertView
					.findViewById(R.id.download_iist_item_progressBar);
			holder.state = (ImageView) convertView
					.findViewById(R.id.download_iist_item_state);
			holder.delete = (ImageView) convertView
					.findViewById(R.id.download_iist_item_delete);

			holder.title.setTextColor(Color.parseColor(Confi.getInstance()
					.getuIConfig().getApp_text_color()));
			holder.size.setTextColor(Color.parseColor(Confi.getInstance()
					.getuIConfig().getApp_text_color()));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// convertView.setBackgroundColor(context.getResources().getColor(
		// listitemBackground));

		// Bind the data efficiently with the holder.
		// holder.title.setTextColor(context.getResources().getColor(
		// listitemTextColor));
		// holder.time.setTextColor(context.getResources().getColor(
		// listitemTextColor));
		// holder.size.setTextColor(context.getResources().getColor(
		// listitemTextColor));

		holder.title.setText(data.get(position).getTitle());
		// holder.time.setText(data.get(position).getTime());
		int totalSize = data.get(position).getTotalSize();
		int currentSize = data.get(position).getCurrentPoit();

		holder.size.setText(Tools.translateKToM(currentSize) + "/"
				+ Tools.translateKToM(totalSize));

		if (data.get(positionTemp).getState() == DownloadManager.download_ing) {// downloading
			holder.state.setImageResource(R.drawable.stop);
			holder.progressBar.setVisibility(View.VISIBLE);
			holder.progressBar.setProgress(data.get(position).getProgress());
		} else if (data.get(positionTemp).getState() == DownloadManager.download_pause) {// downloading
			holder.state.setImageResource(R.drawable.downloading);
			holder.progressBar.setVisibility(View.VISIBLE);
			holder.progressBar.setProgress(data.get(position).getProgress());
		} else if (data.get(positionTemp).getState() == DownloadManager.download_ed) {// finish
			holder.state.setImageResource(R.drawable.finish);
			holder.progressBar.setVisibility(View.INVISIBLE);
		}

		// if (!NetWorkState.isNetworkAvailable(context)) {
		// if (data.get(positionTemp) != null)
		// DownloadManager.getInstance().pause(data.get(positionTemp));
		// } else {
		// if (data.get(positionTemp) != null)
		// DownloadManager.getInstance().goon(context,
		// data.get(positionTemp));
		// }

		holder.state.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (NetWorkState.isNetworkAvailable(context)) {
					// pause
					if (data.get(positionTemp).getState() == DownloadManager.download_ing) {
						DownloadManager.getInstance().pause(
								data.get(positionTemp));
					}
					// begin again or goon
					else if (data.get(positionTemp).getState() == DownloadManager.download_pause) {
						DownloadManager.getInstance().goon(context,
								data.get(positionTemp));
					} else if (data.get(positionTemp).getState() == DownloadManager.download_ed) {
					}
				} else {
					Tools.showBadNetwork(context);
				}
			}

		});

		// if(data.get(position).getProgress()==100) {
		// if(alertDialog!=null && alertDialog.isShowing())
		// alertDialog.dismiss();
		// }

		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (data != null && data.size() > 0
						&& data.get(positionTemp) != null)
					DownloadManager.getInstance().pause(data.get(positionTemp));

				// DownloadActivity.alertDialog = new
				// AlertDialog.Builder(context)
				// .create();
				// // DownloadActivity.alertDialog= alertDialog;
				// DownloadActivity.alertDialog
				// .setTitle(R.string.delete_download_note)
				final AlertDialog alertDialog = new AlertDialog.Builder(context)
						.create();
				alertDialog.show();
				alertDialog.setContentView(R.layout.dialog);
				// 接收最外层LinearLayout
				LinearLayout dialogBground = (LinearLayout) alertDialog
						.findViewById(R.id.dialog_linearlayout_bg);
				// 修改LinearLayout颜色
				dialogBground.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));
				// 设置LinearLayout透明度
				dialogBground.getBackground().setAlpha(150);

				// 接收Title
				TextView txtTitle = (TextView) alertDialog
						.findViewById(R.id.dialog_textview_title);
				// 设置Title颜色
				txtTitle.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));
				txtTitle.setText(R.string.delete_download_messager);

				Button dialogBtnOk = (Button) alertDialog
						.findViewById(R.id.dialog_button_ok);

				dialogBtnOk.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));

				dialogBtnOk.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));

				dialogBtnOk.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (data != null && data.size() > 0
								&& data.get(positionTemp) != null)
							DownloadManager.getInstance().delete(
									data.get(positionTemp));
						alertDialog.cancel();

					}
				});
				// 接收CanelButton
				Button dialogBtnCanel = (Button) alertDialog
						.findViewById(R.id.dialog_button_cancel);
				// 修改CanelButton字体颜色
				dialogBtnCanel.setTextColor(Color.parseColor(uiConfig
						.getTopbar_text_color()));
				// 设置CanelButton颜色
				dialogBtnCanel.setBackgroundColor(Color.parseColor(uiConfig
						.getTopbar_background_color()));
				// CanelButton绑定监听器
				dialogBtnCanel.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						alertDialog.cancel();
						if (NetWorkState.isNetworkAvailable(context)) {
							if (data != null && data.size() > 0
									&& data.get(positionTemp) != null)
								DownloadManager.getInstance().goon(context,
										data.get(positionTemp));
						}
					}

				});

			}

		});

		return convertView;
	}

	final class ViewHolder {
		TextView title, size;
		ProgressBar progressBar;
		ImageView icon, state, delete;
	}
}
