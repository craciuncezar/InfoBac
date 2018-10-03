package io.github.craciuncezar.infobac.views;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import io.github.craciuncezar.infobac.R;

public class ReportDialog {
    public static void showDialog(Context context, LayoutInflater inflater){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.dialog_report,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button trimiteBtn = view.findViewById(R.id.trimite_report);
        Button closeBtn = view.findViewById(R.id.cancel_report);
        CheckBox checkBoxLimbaj = view.findViewById(R.id.checkBox_limbaj_vulgar);
        CheckBox checkBoxInadecvat = view.findViewById(R.id.checkBox_continut_inadecvat);
        CheckBox checkBoxAltMotiv = view.findViewById(R.id.checkbox_alt_motiv);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        trimiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxAltMotiv.isChecked() || checkBoxLimbaj.isChecked() || checkBoxInadecvat.isChecked()){
                    //* Todo: Report */
                    alertDialog.cancel();
                } else {
                    Toast.makeText(context,"Selecteaza un motiv",  Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();
    }
}
