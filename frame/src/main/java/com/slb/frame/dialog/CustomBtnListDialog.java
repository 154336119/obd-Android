package com.slb.frame.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slb.frame.R;


/**
 * Created by Gifford on 2017/7/7.
 */

public class CustomBtnListDialog extends Dialog {

	public CustomBtnListDialog(@NonNull Context context) {
		super(context);
	}

	public CustomBtnListDialog(@NonNull Context context, @StyleRes int themeResId) {
		super(context, themeResId);
	}
	public static class Builder {
		private Context context;
		private String title;
		private CharSequence message;
		private String firstChooseButtonText;
		private String secondChooseButtonText;
		private String cancelChooseButtonText;
		private View contentView;
		private OnClickListener firstChooseButtonClickListener;
		private OnClickListener secondChooseButtonClickListener;
		private OnClickListener canelButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public CustomBtnListDialog.Builder setMessage(CharSequence message) {
			this.message = message;
			return this;
		}

		/**
		 * @return
		 */
		public CustomBtnListDialog.Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * @return
		 */
		public CustomBtnListDialog.Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}


		public CustomBtnListDialog.Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public CustomBtnListDialog.Builder setFirstChooseButton(int firstChooseButtonText,
		                                              OnClickListener listener) {
			this.firstChooseButtonText = (String) context
					.getText(firstChooseButtonText);
			this.firstChooseButtonClickListener = listener;
			return this;
		}

		public CustomBtnListDialog.Builder setFirstChooseButton(String firstChooseButtonText,
		                                              OnClickListener listener) {
			this.firstChooseButtonText = firstChooseButtonText;
			this.firstChooseButtonClickListener = listener;
			return this;
		}

		public CustomBtnListDialog.Builder setsecondChooseButton(int secondChooseButtonText,
		                                                     OnClickListener listener) {
			this.secondChooseButtonText = (String) context
					.getText(secondChooseButtonText);
			this.secondChooseButtonClickListener = listener;
			return this;
		}

		public CustomBtnListDialog.Builder setsecondChooseButton(String secondChooseButtonText,
		                                                     OnClickListener listener) {
			this.secondChooseButtonText = secondChooseButtonText;
			this.secondChooseButtonClickListener = listener;
			return this;
		}

		public CustomBtnListDialog.Builder setCanelButton(int canelButtonText,
		                                                         OnClickListener listener) {
			this.cancelChooseButtonText = (String) context
					.getText(canelButtonText);
			this.canelButtonClickListener = listener;
			return this;
		}

		public CustomBtnListDialog.Builder setCanelButton(String canelButtonText,
		                                                         OnClickListener listener) {
			this.cancelChooseButtonText = canelButtonText;
			this.canelButtonClickListener = listener;
			return this;
		}

		public CustomBtnListDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final CustomBtnListDialog dialog = new CustomBtnListDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.custom_btnlist_dialog, null);
			dialog.addContentView(layout, new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			if (firstChooseButtonText != null) {
				Button firstButton=((Button) layout.findViewById(R.id.firstButton));
				firstButton.setVisibility(View.VISIBLE);
				firstButton.setText(firstChooseButtonText);
				if (firstChooseButtonClickListener != null) {
					firstButton.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									firstChooseButtonClickListener.onClick(dialog,0);
								}
							});
				}
			}
			if (secondChooseButtonText != null) {
				Button secondButton=((Button) layout.findViewById(R.id.secondButton));
				secondButton.setVisibility(View.VISIBLE);
				secondButton.setText(secondChooseButtonText);
				if (secondChooseButtonClickListener != null) {
					secondButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							secondChooseButtonClickListener.onClick(dialog,1);
						}
					});
				}
			}
			if (cancelChooseButtonText != null) {
				Button canelButton=((Button) layout.findViewById(R.id.canelButton));
				canelButton.setVisibility(View.VISIBLE);
				canelButton.setText(cancelChooseButtonText);
				if (canelButtonClickListener != null) {
					canelButton.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							canelButtonClickListener.onClick(dialog,2);
						}
					});
				}
			}

			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new ViewGroup.LayoutParams(
								ViewGroup.LayoutParams.FILL_PARENT,
								ViewGroup.LayoutParams.FILL_PARENT));
			}
			if (title != null) {
				((TextView) layout.findViewById(R.id.title)).setText(title);
			}
			dialog.setCanceledOnTouchOutside(false);
			dialog.setContentView(layout);
			return dialog;
		}
	}
}
