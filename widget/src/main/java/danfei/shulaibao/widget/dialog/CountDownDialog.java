package danfei.shulaibao.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import danfei.shulaibao.widget.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * 倒计时提示框
 * Created by Administrator on 2017/4/18.
 */

public class CountDownDialog extends Dialog {
    public CountDownDialog(@NonNull Context context) {
        super(context);
    }

    public CountDownDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    public static class Builder {
        private Context context;
        private String title;
        private CharSequence message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private TimeOutLinstener timeOutLinstener;
        public Builder(Context context) {
            this.context = context;
        }
        public Builder setTimeOutLinstener(TimeOutLinstener timeOutLinstener) {
            this.timeOutLinstener = timeOutLinstener;
            return this;
        }
        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        /**
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CountDownDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CountDownDialog dialog = new CountDownDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_count_down_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
               Button button =((Button) layout.findViewById(R.id.positiveButton));
                getPositiveBtnText(button,timeOutLinstener);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
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


    public static void getPositiveBtnText(final Button btn, final TimeOutLinstener linstener){
         RxCountDown.countdown(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        btn.setText("确定("+integer+")");
                        if(integer == 0){
                            linstener.timeIsZero();
                        }
                    }
                });
    }

    public static class RxCountDown {
        public static Observable<Integer> countdown(int time) {
            if (time < 0) time = 0;
            final int countTime = time;
            return Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<Long, Integer>() {
                        @Override
                        public Integer call(Long increaseTime) {
                            return countTime - increaseTime.intValue();
                        }
                    })
                    .take(countTime + 1);
        }

    }

    public interface TimeOutLinstener{
        void timeIsZero();
    }
    }

