package com.example.kotstart

import android.content.Intent
import android.widget.Button

public class test {
    var age:Int=0
    val nom="knj"
    fun affiche(ag:Int,nom:String):String{

        val tab = arrayOf(12,25,47,89)
        for (i in 1..10){
            println(i)
        }
        return "la personne est veille"
    }

}
class Maclasse(var nb1:Int,var nb2:Int){

    fun somme():Int{
        return nb1+nb2
    }

}
val nb=Maclasse(12,15)
var nom:String="KNJ"
//print(nom)
/*while (true){
    println("bonjour")
}
val boutton=findViewById<Button>(R.id.start)
boutton.setOnClickListener{
    val intent= Intent(this,MainActivity2::class.java)//navigue vers la fen 2
    startActivity(intent)//on lance la fen 2
}*/