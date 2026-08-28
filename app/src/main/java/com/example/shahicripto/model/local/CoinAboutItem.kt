package com.example.shahicripto.model.local

import java.io.Serializable

data class CoinAboutItem(

    var coinWebsite : String? = "no-data" ,
    var coinGithub : String? = "no-data" ,
    var coinX : String ? = "no-data" ,
    var coinDesc : String? = "no-data" ,
    var coinReddit : String? = "no-data"
) : Serializable
