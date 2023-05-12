package com.example.addvertisements

class DataClass {
    //var id:String? = null
    var title:String? = null
    var type:String? = null
    var description:String? = null
   var image:String? = null
    var price:String? = null
    var contact:String? = null
    var address:String? = null

    constructor(
        //id:String?,
        title: String?,
        type: String?,
        description: String?,
        image: String?,
        price: String?,
        contact: String?,
        address: String?
    ) {
        this.title = title
        this.type = type
        this.description = description
        this.image = image
        this.price = price
        this.contact = contact
        this.address = address
    }

    constructor(){}

//    constructor(dataTitle:String?, dataType:String?, dataDescription:String?, dataImage:String? ,dataPrice:String?,dataContact:String?,dataAddress:String?){
//        this.dataTitle = dataTitle
//        this.dataType = dataType
//        this.dataDescription = dataDescription
//        this.dataImage = dataImage
//        this.dataPrice = dataPrice
//        this.dataContact = dataContact
//        this.dataAddress = dataAddress
//
//    }

}