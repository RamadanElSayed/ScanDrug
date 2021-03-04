package com.scandrug.scandrug.data.local

import com.scandrug.scandrug.data.remotemodel.DrugDetailsModel

class CacheInMemory {

    companion object Singleton {

        private var hashMap: HashMap<String, Any> = HashMap<String, Any>()
//
//        fun saveClientJobs(clientJobs: List<ClientJobDo>) {
//            hashMap["JOBS"] = clientJobs
//        }
//
//        fun getClientJobs() {
//            hashMap["JOBS"]
//        }
//
//
        fun setDrugDetailsModel(drugDetailsModel: DrugDetailsModel) {
            hashMap["drugDetailsModel"] = drugDetailsModel
        }
//
//        fun saveAllClientIdentities(clientIdentities: ClientIdentityDo) {
//            hashMap["IDENTITIES"] = clientIdentities
//        }
//
//
        fun getDrugDetailsModel(): DrugDetailsModel? {
            return hashMap["drugDetailsModel"] as? DrugDetailsModel
        }
//
//        fun saveProfileCache(cache: DatumDo) {
//            hashMap["CACHE"] = cache
//        }
//
//        fun getProfileCache(): DatumDo {
//            return hashMap["CACHE"] as DatumDo
//        }
//
//
//        fun saveToken(token: String) {
//            hashMap["TOKEN"] = token
//        }
//
//        fun loadToken(): String? {
//            return hashMap["TOKEN"] as? String
//        }

        fun clearProfileData() {
            hashMap.remove("drugDetailsModel")
        }


    }

}