package com.querhen

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class Sansekai : MainAPI() { 
    override var mainUrl = "https://drama.sansekai.my.id"
    override var name = "Sansekai"
    override val supportedTypes = setOf(TvType.TvSeries, TvType.Movie)

    override suspend fun getMainPage(page: Int, request : MainPageRequest): HomePageResponse? {
        // Logika scraping atau API call ke api.sansekai.my.id di sini
        return newHomePageResponse(name, listOf()) 
    }
}
