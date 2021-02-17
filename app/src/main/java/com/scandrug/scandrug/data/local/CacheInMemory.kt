package com.scandrug.scandrug.data.local

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
//        fun saveOneClientIdentities(clientIdentities: ClientIdentityDo) {
//            hashMap["IDENTITIES"] = clientIdentities
//        }
//
//        fun saveAllClientIdentities(clientIdentities: ClientIdentityDo) {
//            hashMap["IDENTITIES"] = clientIdentities
//        }
//
//
//        fun getOneClientIdentities(): ClientIdentityDo? {
//            return hashMap["IDENTITIES"] as? ClientIdentityDo
//        }
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
            hashMap.remove("CACHE")
            hashMap.remove("TYPES")
        }


    }

}