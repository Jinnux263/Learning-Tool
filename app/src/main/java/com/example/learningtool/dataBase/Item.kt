package com.example.learningtool.dataBase

class Item {
    var time : Long? = null

    constructor(){
        this.time = 0
    }
    constructor(input: Long){
        this.time = input
    }
}