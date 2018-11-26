package com.example.ra930641.traning.objects

class Exercise {

    var type: String = ""
    var name: String = ""
    var description: String = ""
    var set: String = ""



    constructor()

    constructor(type: String, name: String, description: String, set: String){
        this.type = type
        this.name = name
        this.description = description
        this.set = set
    }
}