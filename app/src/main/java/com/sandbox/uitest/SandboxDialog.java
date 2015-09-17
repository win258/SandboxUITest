package com.sandbox.uitest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wangjia1-xy on 2015/9/17.
 */
public class SandboxDialog extends Dialog {
    public SandboxDialog(Context context, int theme) {
        super(context, theme);
    }

    public SandboxDialog(Context context) {
        super(context);
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String backButtonText;
        private String confirmButtonText;
        private View contentView;

        private DialogInterface.OnClickListener
                backButtonClickListener,
                confirmButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setBackButton(int backButtonText, DialogInterface.OnClickListener listener) {
            this.backButtonText = (String) context.getText(backButtonText);
            this.backButtonClickListener = listener;
            return this;
        }

        public Builder setBackButton(String backButtonText, DialogInterface.OnClickListener listener) {
            this.backButtonText = backButtonText;
            this.backButtonClickListener = listener;
            return this;
        }

        public Builder setConfirmButton(int confirmButtonText, DialogInterface.OnClickListener listener) {
            this.confirmButtonText = (String) context.getText(confirmButtonText);
            this.confirmButtonClickListener = listener;
            return this;
        }

        public Builder setConfirmButton(String confirmButtonText, DialogInterface.OnClickListener listener) {
            this.confirmButtonText = confirmButtonText;
            this.confirmButtonClickListener = listener;
            return this;
        }

        public SandboxDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final SandboxDialog dialog = new SandboxDialog(context, R.style.custom_dialog);

            View layout = inflater.inflate(R.layout.sandbox_dialog, null);
            dialog.addContentView(layout,
                    new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            ((TextView) layout.findViewById(R.id.dialog_title)).setText(title);
            if (message != null) {
                TextView dlgMsg = (TextView) layout.findViewById(R.id.dialog_content_text);
                dlgMsg.setText(message);
            } else if (contentView != null) {
                // 如果未设置Dialog文本，则添加contentView
                ((LinearLayout) layout.findViewById(R.id.dialog_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_content)).addView(
                        contentView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }

            if (backButtonText == null || confirmButtonText == null) {
                layout.findViewById(R.id.dialog_bottom_divider).setVisibility(View.GONE);
                layout.findViewById(R.id.dialog_bottom_content).setVisibility(View.GONE);

            } else {
                LinearLayout bckButton = ((LinearLayout) layout.findViewById(R.id.dialog_back));
                ((TextView) layout.findViewById(R.id.dialog_back_text)).setText(backButtonText);

                if (backButtonClickListener != null) {
                    bckButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            backButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }

                LinearLayout cfmButton = ((LinearLayout) layout.findViewById(R.id.dialog_confirm));
                ((TextView) layout.findViewById(R.id.dialog_confirm_text)).setText(confirmButtonText);

                if (confirmButtonClickListener != null) {
                    cfmButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            confirmButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }
            dialog.setContentView(layout);

            return dialog;
        }
    }
}
