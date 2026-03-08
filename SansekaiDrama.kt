
package com.sansekai

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class SansekaiDrama : MainAPI() {

    override var name = "Sansekai Drama"
    override var mainUrl = "https://api.sansekai.my.id"
    override var lang = "id"

    override val hasMainPage = true
    override val hasQuickSearch = true

    override val supportedTypes = setOf(
        TvType.Movie,
        TvType.TvSeries
    )

    override val mainPage = mainPageOf(
        "$mainUrl/api/dramabox/trending" to "Trending",
        "$mainUrl/api/dramabox/latest" to "Latest"
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {

        val url = request.data
        val json = app.get(url).parsedSafe<Map<String, Any>>() ?: return HomePageResponse(emptyList())

        val items = mutableListOf<SearchResponse>()

        val data = json["data"] as? List<Map<String, Any>> ?: emptyList()

        for (item in data) {
            val title = item["title"]?.toString() ?: continue
            val poster = item["poster"]?.toString()
            val id = item["id"]?.toString() ?: continue

            items.add(
                newMovieSearchResponse(
                    title,
                    "$mainUrl/api/dramabox/detail/$id",
                    TvType.Movie
                ) {
                    this.posterUrl = poster
                }
            )
        }

        return HomePageResponse(
            listOf(HomePageList(request.name, items))
        )
    }

    override suspend fun search(query: String): List<SearchResponse> {

        val url = "$mainUrl/api/search?q=$query"

        val json = app.get(url).parsedSafe<Map<String, Any>>() ?: return emptyList()

        val results = mutableListOf<SearchResponse>()

        val data = json["data"] as? List<Map<String, Any>> ?: emptyList()

        for (item in data) {

            val title = item["title"]?.toString() ?: continue
            val poster = item["poster"]?.toString()
            val id = item["id"]?.toString() ?: continue

            results.add(
                newMovieSearchResponse(
                    title,
                    "$mainUrl/api/dramabox/detail/$id",
                    TvType.Movie
                ) {
                    this.posterUrl = poster
                }
            )
        }

        return results
    }

    override suspend fun load(url: String): LoadResponse {

        val json = app.get(url).parsedSafe<Map<String, Any>>() ?: throw ErrorLoadingException()

        val data = json["data"] as Map<String, Any>

        val title = data["title"]?.toString() ?: "Unknown"
        val poster = data["poster"]?.toString()
        val description = data["description"]?.toString()

        val episodes = mutableListOf<Episode>()

        val eps = data["episodes"] as? List<Map<String, Any>> ?: emptyList()

        for (ep in eps) {

            val epId = ep["id"]?.toString() ?: continue
            val epNum = ep["number"]?.toString()?.toIntOrNull()

            episodes.add(
                Episode(
                    "$mainUrl/api/dramabox/stream/$epId",
                    name = "Episode $epNum",
                    episode = epNum
                )
            )
        }

        return newTvSeriesLoadResponse(
            title,
            url,
            TvType.TvSeries,
            episodes
        ) {
            this.posterUrl = poster
            this.plot = description
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {

        val json = app.get(data).parsedSafe<Map<String, Any>>() ?: return false

        val video = json["video"]?.toString() ?: return false

        callback(
            ExtractorLink(
                name,
                name,
                video,
                mainUrl,
                Qualities.Unknown.value
            )
        )

        return true
    }
}
