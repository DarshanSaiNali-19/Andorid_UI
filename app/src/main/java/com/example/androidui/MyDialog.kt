package com.example.androidui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //create a dialog return that
        val dlg : Dialog
        val ctx = context
        val dBundle = arguments

        val title = dBundle?.getString("dTitle")
        val msg = dBundle?.getString("dMessage")
        val flag = dBundle?.getInt("flag")

        val builder = AlertDialog.Builder(ctx!!)
        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setPositiveButton("Yes"){ _ , _ ->
           if(flag == 1)
                activity?.finish()
            else if(flag == 2){
               val userid = dBundle.getString("userid")
               val password = dBundle.getString("pwd")
               showMessage(ctx, " Submitting Data")
               activity?.finish()
           }


        }
        builder.setNegativeButton("No"){dialog, _ ->
            dialog.cancel()
        }

        dlg = builder.create()
        return dlg
    }
}