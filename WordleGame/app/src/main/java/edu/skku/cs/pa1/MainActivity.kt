package edu.skku.cs.pa1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import android.util.Log



class MainActivity : AppCompatActivity() {

    private lateinit var dictionary: List<String>


    private fun loadDictionary() {
        try {
            val inputStream = assets.open("wordle_words.txt")
            val reader = inputStream.bufferedReader()
            dictionary = reader.useLines { lines -> lines.toList() }
        } catch (e: IOException) {
            Toast.makeText(this, "Error reading dictionary", Toast.LENGTH_SHORT).show()
        }
    }


    private fun generateSecretWord(): String {
        val randomIndex = (dictionary.indices).random()
        val secretWord = dictionary[randomIndex]

        // -------> Printing secret word in LogCat
        Log.d("WordleGame", "Secret Word: $secretWord")
        return dictionary[randomIndex]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadDictionary()

        val editTxt = findViewById<EditText>(R.id.editInput)
        val submitBtn = findViewById<Button>(R.id.button)
        val wordlist = findViewById<ListView>(R.id.wordList)

        val greenList = findViewById<ListView>(R.id.greenLetterList)
        val yellowList = findViewById<ListView>(R.id.yellowLetterList)
        val grayList = findViewById<ListView>(R.id.grayLetterList)

        val list = arrayListOf<Words>()
        val grayArrayList = arrayListOf<String>()
        val greenArrayList = arrayListOf<String>()
        val yellowArrayList = arrayListOf<String>()

        // Generating secret word
        val secretWord = this.generateSecretWord()

        submitBtn.setOnClickListener{
            val guessWord = editTxt.text.toString()

            if (dictionary.contains(guessWord)) {
                list.add(Words(guessWord, secretWord))
                editTxt.setText("")

                val guessArray = guessWord.toCharArray()
                val secretWordArray = secretWord.toCharArray()

                // Loop for filling 3 columns at the bottom of screen
                // It iterates through each character in guessWord and calls the necessary adapter
                for (i in guessArray.indices){
                    if (guessArray[i] == secretWordArray[i]) {
                        if (!greenArrayList.contains(guessArray[i].toString())) {
                            greenArrayList.add(guessArray[i].toString())
                            greenArrayList.sort()

                            // Discarding yellow letter
                            if (yellowArrayList.contains(guessArray[i].toString())){
                                yellowArrayList.remove(guessArray[i].toString())
                                val yellowAdapter =
                                    YellowListAdapter(this, yellowArrayList)
                                yellowList.adapter = yellowAdapter
                                val lastIndex = yellowAdapter.count - 1
                                yellowList.setSelection(lastIndex)
                            }
                            val greenAdapter =
                                GreenListAdapter(this, greenArrayList)
                            greenList.adapter = greenAdapter
                            val lastIndex = greenAdapter.count - 1
                            greenList.setSelection(lastIndex)
                        }
                    }
                    else if (secretWordArray.contains(guessArray[i])){
                        if (!yellowArrayList.contains(guessArray[i].toString())) {
                            yellowArrayList.add(guessArray[i].toString())
                            yellowArrayList.sort()

                            // If the given letter is in green column, do not print it
                            if (greenArrayList.contains(guessArray[i].toString())){
                                yellowArrayList.remove(guessArray[i].toString())
                            }
                            val yellowAdapter =
                                YellowListAdapter(this, yellowArrayList)
                            yellowList.adapter = yellowAdapter
                            val lastIndex = yellowAdapter.count-1
                            yellowList.setSelection(lastIndex)
                        }
                    }
                    else {
                        if (!grayArrayList.contains(guessArray[i].toString())) {
                            grayArrayList.add(guessArray[i].toString())
                            grayArrayList.sort()
                            val grayAdaptor =
                                GrayListAdapter(this, grayArrayList)
                            grayList.adapter = grayAdaptor
                            val lastIndex = grayAdaptor.count-1
                            grayList.setSelection(lastIndex)
                        }
                    }
                }

                val wordListAdapter = CustomWordListAdapter(this, list)

                // Filling the words list and make it point to the bottom of list
                wordlist.adapter = wordListAdapter
                val lastIndex = wordListAdapter.count - 1
                wordlist.setSelection(lastIndex)
            }
            else {
                Toast.makeText(this, "Word <$guessWord> is not in dictionary! ",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}