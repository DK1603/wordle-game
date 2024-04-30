package edu.skku.cs.pa1

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class CustomWordListAdapter(val context: Context, val text: ArrayList<Words>):BaseAdapter(){

    override fun getCount(): Int {
        return text.size
    }
    override fun getItem(position: Int): Any {
        return text.get(position)
    }
    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(i: Int, cvtView: View?, parent: ViewGroup?): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.word_layout,null)



        val guess = text.get(i).name // Setting the input string to variable guess
        val secretWord = text.get(i).secretWord // Secret word == secretWord

        val textViewIds = arrayOf(
            R.id.textView1, R.id.textView2, R.id.textView3,
            R.id.textView4, R.id.textView5
        )

        val guessArray = guess.toCharArray()
        val secretWordArray = secretWord.toCharArray()

        for (i in guessArray.indices) {
            var textView = view.findViewById<TextView>(textViewIds[i])
            textView.setText(guessArray[i].toString().capitalize())

            if (guessArray[i] == secretWordArray[i]) {
                textView.setBackgroundColor(Color.parseColor("#99F691"))
                textView.setTextColor(Color.parseColor("#000000"))
            } else if (secretWordArray.contains(guessArray[i])) {
                textView.setBackgroundColor(Color.parseColor("#FFE46F"))
                textView.setTextColor(Color.parseColor("#000000"))
            } else {
                textView.setBackgroundColor(Color.parseColor("#787C7E"))
                textView.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
        return view
    }


}