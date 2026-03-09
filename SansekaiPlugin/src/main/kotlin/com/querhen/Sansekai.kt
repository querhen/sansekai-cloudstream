package com.querhen

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.loadExtractor

class Sansekai : MainAPI() {
    override var mainUrl = "https://api.sansekai.my.id"
    override var name = "Sansekai"
    override val hasMainPage = true
    override var lang = "id"
    override val hasQuickSearch = true
    override val supportedTypes = setOf(TvType.TvSeries, TvType.Movie)

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        // Mengambil data dari endpoint API Sansekai
        val data = app.get("$mainUrl/api/home").parsed<List<HomeResponse>>()
        val homeItems = data.map { res ->
            val movieData = res.items.map { item ->
                MovieSearchResponse(
                    item.title,
                    item.url,
                    this.name,
                    TvType.TvSeries,
                    item.poster,
                    null,
                    null
                )
            }
            HomePageList(res.category, movieData)
        }
        return HomePageResponse(homeItems)
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/api/search?q=$query"
        return app.get(url).parsed<List<SearchResult>>().map {
            MovieSearchResponse(
                it.title,
                it.url,
                this.name,
                TvType.TvSeries,
                it.poster,
                null,
                null
            )
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val res = app.get(url).parsed<MovieDetail>()
        return TvSeriesLoadResponse(
            res.title,
            url,
            this.name,
            TvType.TvSeries,
            res.episodes.map { ep ->
                Episode(ep.url, ep.name, ep.season, ep.number)
            },
            res.poster,
            res.year,
            res.plot
        )
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        // Otomatis mencari extractor yang cocok (Dramabox/Shortmax)
        loadExtractor(data, subtitleCallback, callback)
        return true
    }
    
    // Data Classes untuk parsing JSON API
    data class HomeResponse(val category: String, val items: List<SearchResult>)
    data class SearchResult(val title: String, val url: String, val poster: String)
    data class MovieDetail(
        val title: String, 
        val poster: String, 
        val year: Int?, 
        val plot: String?, 
        val episodes: List<EpisodeDetail>
    )
    data class EpisodeDetail(val name: String, val url: String, val season: Int?, val number: Int?)
}
