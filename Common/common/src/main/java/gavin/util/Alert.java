package gavin.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import gavin.common.R;

public final class Alert {

    public interface OnAlertSelectId {
        void onClick(int whichButton);
    }

    private Alert() {

    }

    public static AlertDialog showAlert(final Context context, final String msg, final String title) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.totem_alert_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final int msgId, final int titleId) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(titleId);
        builder.setMessage(msgId);
        builder.setPositiveButton(R.string.totem_alert_confirm, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final int msgId, final int titleId, final DialogInterface.OnClickListener l) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(titleId);
        builder.setMessage(msgId);
        builder.setPositiveButton(R.string.totem_alert_confirm, l);
        // builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String msg, final String title, final DialogInterface.OnClickListener l) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.totem_alert_confirm, l);
        // builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final int msgId, final int titleId, final DialogInterface.OnClickListener lOk, final DialogInterface.OnClickListener lCancel) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(titleId);
        builder.setMessage(msgId);
        builder.setPositiveButton(R.string.totem_alert_confirm, lOk);
        builder.setNegativeButton(R.string.totem_alert_cancel, lCancel);
        // builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final int msg, final int title, final int yes, final int no, final DialogInterface.OnClickListener lOk,
            final DialogInterface.OnClickListener lCancel) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(yes, lOk);
        builder.setNegativeButton(no, lCancel);
        // builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String msg, final String title, final DialogInterface.OnClickListener lOk, final DialogInterface.OnClickListener lCancel) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.totem_alert_confirm, lOk);
        builder.setNegativeButton(R.string.totem_alert_cancel, lCancel);
        // builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String msg, final String title, final String yes, final String no, final DialogInterface.OnClickListener lOk,
            final DialogInterface.OnClickListener lCancel) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(yes, lOk);
        builder.setNegativeButton(no, lCancel);
        // builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String title, final View view, final DialogInterface.OnClickListener lOk) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton(R.string.totem_alert_confirm, lOk);
        // builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String title, final View view, final String ok, final String cancel, final DialogInterface.OnClickListener lOk,
            final DialogInterface.OnClickListener lCancel) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton(ok, lOk);
        builder.setNegativeButton(cancel, lCancel);
        // builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String title, final String ok, final View view, final DialogInterface.OnClickListener lOk) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton(ok, lOk);
        // builder.setCancelable(true);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String title, final String msg, final View view, final DialogInterface.OnClickListener lOk,
            final DialogInterface.OnClickListener lCancel) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setView(view);
        builder.setPositiveButton(R.string.totem_alert_confirm, lOk);
        builder.setNegativeButton(R.string.totem_alert_cancel, lCancel);
        // builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (lCancel != null) {
                    lCancel.onClick(dialog, 0);
                }
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public static AlertDialog showAlert(final Context context, final String title, final View view, final DialogInterface.OnCancelListener lCancel) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(view);
        // builder.setCancelable(true);
        builder.setOnCancelListener(lCancel);
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }
}
