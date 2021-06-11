package com.bnkit.bnkit.data.remote
import com.bnkit.bnkit.data.models.*
import com.bnkit.bnkit.data.models.EmploymentTypes.EmploymentTypes
import com.bnkit.bnkit.data.models.EmploymentTypes.ServiceProviders
import com.bnkit.bnkit.data.models.RequestFinance.AddRequest.NewRequest
import com.bnkit.bnkit.data.models.RequestFinance.products.Products
import com.bnkit.bnkit.data.models.RequestFinance.products.ProductsItem
import com.bnkit.bnkit.data.models.RequestHistoryModel.RequestHistoryResponse
import com.bnkit.bnkit.data.models.offers.OfferDetailsModel
import com.bnkit.bnkit.data.models.offers.OffersResponse
import com.bnkit.bnkit.data.models.remoteapimodels.CheckClientBaseResponse
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    @GET(EndPoints.VALIDATE_REGISTRATION)
    suspend fun checkClient(@Query("sysToken")  sysToken:String,@Query("email") email:String
    ,@Query("phone")  phone:String): Response<CheckClientBaseResponse>

    @POST(EndPoints.LEAVE_MESSAGE)
    suspend fun leaveMessage(@Body leaveMessageBody: LeaveMessageBody): Response<LeaveMessageResponse>

    @GET(EndPoints.RESENDSMS)
    suspend fun reSendMessage(@Query("sysToken")  sysToken:String,@Query("phone") phone:String
                            ,@Query("verification")  verification:Int): Response<ResendMessageResponse>

    @POST(EndPoints.REGISTER_USER)
    suspend fun registerUser(@Body registrationBody: RegistrationBody): Response<RegistrationResponse>


    @GET(EndPoints.COMPANY_INFO)
    suspend fun getCompanyInfo(): Response<CompanyInfoResponse>

    @PUT(EndPoints.UPDATE_FIRE_TOKEN)
    suspend fun updateToken(@Body updateTokenBody: UpdateTokenBody): Response<Any>

    @PUT(EndPoints.LOG_INFO)
    suspend fun getLogInfo(@Body logInfoBody: LogInfoBody):  Response<RegistrationResponse>

    @GET(EndPoints.OFFERS)
    suspend fun getOffers(@Query("skip") start :Int, @Query("take") length:Int ):OffersResponse

    @GET(EndPoints.ALLREQUESTS)
    suspend fun getAllRequests(@Query("uid") uid :String, @Query("sysToken") systoken:String ):RequestHistoryResponse

    @GET(EndPoints.GET_NOTIFICATIONS)
    suspend fun getNotifications(@Query("sysToken")  sysToken:String,@Query("uid") uid:String
                              ,@Query("skip")  skip:Int,@Query("take")  take:Int): Response<List<NotificationsResponseItem>>

    @GET(EndPoints.GETOFFERSDETAILS)
    suspend fun getOfferDetails(@Query("sysToken")  sysToken:String,@Query("uid") uid:String
                              ,@Query("id")  id:Int): OfferDetailsModel



    @DELETE(EndPoints.DELETE_NOTIFICATIONS)
    suspend fun deleteNotifications(@Query("sysToken")  sysToken:String,@Query("uid") uid:String
                                 ,@Query("Id")  Id:Int): Response<NotificationsResponseItem>

    @DELETE(EndPoints.CHANGE_SEEING_NOTIFICATIONS)
    suspend fun changeNotifications(@Query("sysToken")  sysToken:String,@Query("uid") uid:String
                                    ,@Query("RelId")  RelId:Int): Response<NotificationsResponseItem>

    @GET(EndPoints.PRODUCTS)
    suspend fun getProducts(@Query("skip")  skip:Int,@Query("take") take:Int): Products

    @GET(EndPoints.PRODUCTDETAIL)
    suspend fun getProductDetails(@Query("id")  id:Int): ProductsItem

    @GET(EndPoints.EMPLOYMENTTYPES)
    suspend fun getEmploymentTypes(): EmploymentTypes

    @GET(EndPoints.SERVICEPROVIDER)
    suspend fun getServiceProvider(): ServiceProviders


    @POST(EndPoints.NEWREQUEST)
    suspend fun NewRequest(@Body newRequest: NewRequest) :Any

}