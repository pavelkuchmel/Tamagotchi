package com.example.tamagotchi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class DeletePlayerQuestion extends DialogFragment {

    private Removable removable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        removable = (Removable) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Tamagotchi tamagotchi = (Tamagotchi) getArguments().getParcelable("tamagotchi");
        List<Tamagotchi> arrayTamagochi = getArguments().getParcelableArrayList("arrayTamagotchi");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удаление игрока")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Точно хотите удалить игрока " + tamagotchi.toString() + " ?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removable.remove(tamagotchi);
                    }
                })
                .setNegativeButton("Нет", null)
                .create();
    }
}
