package com.querhen

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.AppUtils.parseJson
import com.lagradost.cloudstream3.utils.HttpQueries

class Sansekai : MainAPI() {
    override var mainUrl = "https://drama.sansekai.my.id"
    override var name = "Sansekai"
    override val supportedTypes = setOf(TvType.TvSeries, TvType.Movie, TvType.Anime)

    // Link API dari postingan M Yusril
    private val apiUrl = "https://api.sansekai.my.id"

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        // Mengambil data dari endpoint API Sansekai
        val response = app.get("$apiUrl/api/all").text
        
        // Di sini kita memetakan JSON dari API ke format Cloudstream
        // Sesuaikan key JSON (misal: "title", "poster") dengan response dari server
        val items = mutableListOf<SearchResponse>()
        
        // Contoh penambahan list (Dummy logic, sesuaikan dengan struktur JSON asli)
        return newHomePageResponse(
            listOf(HomePageList("Terbaru", items)),
            hasNext = false
        )
    }

    override suspend fun search(query: String): List<SearchResponse> {
        return app.get("$apiUrl/search?q=$query").parsed<List<SearchResponse>>()
    }
}
