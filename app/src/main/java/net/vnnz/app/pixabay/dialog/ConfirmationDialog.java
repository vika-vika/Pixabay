package net.vnnz.app.pixabay.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.vnnz.app.pixabay.R;
import net.vnnz.app.pixabay.model.pojo.Hits;

public class ConfirmationDialog extends DialogFragment {

    public interface OnDialogFragmentClickListener {
        void onPositiveClicked(Object data);
        void onNegativeClicked();
    }

    public static ConfirmationDialog newInstance(Hits hit) {

        ConfirmationDialog frag = new ConfirmationDialog();
        Bundle args = new Bundle();
        args.putParcelable("hit", hit);
        frag.setArguments(args);
        return frag;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog, null);
        final Hits hit = getArguments().getParcelable("hit");
        TextView text = (TextView) dialogView.findViewById(R.id.dialog_content_text);
        text.setText(getString(R.string.dialog_text, hit.getUser()));

        Button positive = (Button) dialogView.findViewById(R.id.button_positive);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof  OnDialogFragmentClickListener) {
                    ((OnDialogFragmentClickListener)getActivity()).onPositiveClicked(hit);
                }
                dismiss();
            }
        });


        Button negative = (Button) dialogView.findViewById(R.id.button_negative);
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setCustomTitle(null)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        return dialog;
    }
}

