package com.example.myapplication

data class Image (

    var largeImageURL : String,
    var user : String,
    var tags : String,
    var likes : Int,
    var id : Int
)

data class Hits (
    var hits : Array<Image>
)