package com.scandrug.scandrug.data.remotemodel

data class DrugDetailsModel(
      var tabletNumber:String="",
      var tabletWeight:String="",
      var drugName:String="",
      var drugPrice:String="",
      var drugDesc:String="",
      var drugRelieve:String="",
      var drugEffect:String="",
      var drugDosageUsed:String="",
      var clientStreet:String="",
      var clientCity:String="",
      var clientApartment:String="",
      var orderStatus:Int=1,
      var orderId:String="",
      var dateOrder:String="",
      var userName:String="",
      var userId:String=""

)

data class UserData(
      var email:String="",
      var firstName:String="",
      var phoneNumber:String=""

      )