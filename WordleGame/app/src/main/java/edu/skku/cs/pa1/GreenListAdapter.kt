package edu.skku.cs.pa1

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class GreenListAdapter(val context: Context, val greenArrayList: ArrayList<String>): BaseAdapter() {
    override fun getCount(): Int {
        return greenArrayList.size
    }

    override fun getItem(p0: Int): Any {
        return greenArrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.letter_layout, null)

        val textView = view.findViewById<TextView>(R.id.letterTextView)

        textView.setBackgroundColor(Color.parseColor("#99F691"))
        textView.setTextColor(Color.parseColor("#000000"))

        textView.setText(greenArrayList[p0].capitalize())

        return view
    }
}