package com.reabuilt.a94761525castlecustom.data.repository

import com.reabuilt.a94761525castlecustom.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContentRepository(private val application: CastleApplication) {

    private val _trendingContent = MutableStateFlow<List<Content>>(emptyList())
    private val _continueWatching = MutableStateFlow<List<Content>>(emptyList())
    private val _recommendedContent = MutableStateFlow<List<Content>>(emptyList())
    private val _featuredCollections = MutableStateFlow<List<FeaturedCollection>>(emptyList())
    private val _liveChannels = MutableStateFlow<List<LiveChannel>>(emptyList())
    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    private val _watchlist = MutableStateFlow<Set<String>>(emptySet())
    private val _watchHistory = MutableStateFlow<List<WatchHistoryItem>>(emptyList())
    private val _downloads = MutableStateFlow<List<DownloadItem>>(emptyList())
    private val _userPreferences = MutableStateFlow(UserPreferences())

    val trendingContent: StateFlow<List<Content>> = _trendingContent.asStateFlow()
    val continueWatching: StateFlow<List<Content>> = _continueWatching.asStateFlow()
    val recommendedContent: StateFlow<List<Content>> = _recommendedContent.asStateFlow()
    val featuredCollections: StateFlow<List<FeaturedCollection>> = _featuredCollections.asStateFlow()
    val liveChannels: StateFlow<List<LiveChannel>> = _liveChannels.asStateFlow()
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()
    val watchlist: StateFlow<Set<String>> = _watchlist.asStateFlow()
    val watchHistory: StateFlow<List<WatchHistoryItem>> = _watchHistory.asStateFlow()
    val downloads: StateFlow<List<DownloadItem>> = _downloads.asStateFlow()
    val userPreferences: StateFlow<UserPreferences> = _userPreferences.asStateFlow()

    init {
        loadMockData()
        loadUserPreferences()
    }

    private fun loadMockData() {
        application.getMainScope().launch {
            // Load trending content
            _trendingContent.value = generateTrendingContent()

            // Load continue watching
            _continueWatching.value = generateContinueWatching()

            // Load recommended
            _recommendedContent.value = generateRecommendedContent()

            // Load featured collections
            _featuredCollections.value = generateFeaturedCollections()

            // Load live channels
            _liveChannels.value = generateLiveChannels()

            // Load watchlist
            _watchlist.value = setOf("1", "3", "5", "8", "12")

            // Load watch history
            _watchHistory.value = generateWatchHistory()

            // Load downloads
            _downloads.value = generateDownloads()
        }
    }

    private fun loadUserPreferences() {
        // In real app, load from SharedPreferences/DataStore
        _userPreferences.value = UserPreferences(
            preferredLanguages = listOf("Hindi", "English", "Tamil"),
            preferredGenres = listOf("Action", "Thriller", "Comedy", "Drama"),
            videoQuality = VideoQuality.HD,
            autoPlayNext = true,
            downloadsOverWifiOnly = true,
            themeMode = ThemeMode.SYSTEM,
            enableNotifications = true
        )
    }

    private fun generateTrendingContent(): List<Content> {
        return listOf(
            Content(
                id = "1",
                title = "Pathaan",
                description = "An Indian spy action thriller film directed by Siddharth Anand. Shah Rukh Khan stars as Pathaan, a RAW agent who teams up with an ISI agent to stop a terrorist organization.",
                posterUrl = "https://image.tmdb.org/t/p/w500/8Vt6kHEHvjWr6zHX6GseEWYwK5W.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/8Vt6kHEHvjWr6zHX6GseEWYwK5W.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Thriller"),
                languages = listOf("Hindi", "Tamil", "Telugu"),
                releaseYear = 2023,
                durationMinutes = 146,
                rating = 4.5f,
                voteCount = 12500,
                isTrending = true,
                isFeatured = true,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Shah Rukh Khan", "Deepika Padukone", "John Abraham", "Dimple Kapadia"),
                crew = listOf("Siddharth Anand (Director)", "Aditya Chopra (Producer)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/pathaan_480p.m3u8",
                    "720p" to "https://example.com/pathaan_720p.m3u8",
                    "1080p" to "https://example.com/pathaan_1080p.m3u8"
                ),
                subtitleUrls = mapOf(
                    "English" to "https://example.com/pathaan_en.vtt",
                    "Hindi" to "https://example.com/pathaan_hi.vtt"
                )
            ),
            Content(
                id = "2",
                title = "Jawan",
                description = "A high-octane action thriller directed by Atlee, starring Shah Rukh Khan in a dual role as a father and son who team up to fight corruption.",
                posterUrl = "https://image.tmdb.org/t/p/w500/5u2wKMMQVijEv8nKmcRJ0mKj2hV.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/5u2wKMMQVijEv8nKmcRJ0mKj2hV.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Thriller", "Drama"),
                languages = listOf("Hindi", "Tamil", "Telugu"),
                releaseYear = 2023,
                durationMinutes = 169,
                rating = 4.6f,
                voteCount = 15200,
                isTrending = true,
                isFeatured = true,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Shah Rukh Khan", "Nayanthara", "Vijay Sethupathi", "Deepika Padukone"),
                crew = listOf("Atlee (Director)", "Gauri Khan (Producer)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/jawan_480p.m3u8",
                    "720p" to "https://example.com/jawan_720p.m3u8",
                    "1080p" to "https://example.com/jawan_1080p.m3u8"
                )
            ),
            Content(
                id = "3",
                title = "The Family Man",
                description = "A middle-class man secretly works as an intelligence officer for a special cell of the National Investigation Agency while trying to balance his family life.",
                posterUrl = "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Action", "Thriller", "Drama", "Family"),
                languages = listOf("Hindi", "Tamil", "Telugu", "Malayalam"),
                releaseYear = 2019,
                durationMinutes = null,
                rating = 4.8f,
                voteCount = 25000,
                isTrending = true,
                isFeatured = true,
                seasonCount = 2,
                episodeCount = 19,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Manoj Bajpayee", "Priyamani", "Sharib Hashmi", "Neeraj Madhav"),
                crew = listOf("Raj & DK (Creators)", "Manoj Bajpayee (Producer)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/tfm_s1e1_480p.m3u8",
                    "720p" to "https://example.com/tfm_s1e1_720p.m3u8",
                    "1080p" to "https://example.com/tfm_s1e1_1080p.m3u8"
                )
            ),
            Content(
                id = "4",
                title = "Mirzapur",
                description = "A shocking incident at a wedding procession ignites a series of events entangling the lives of two families in the lawless city of Mirzapur.",
                posterUrl = "https://image.tmdb.org/t/p/w500/kPZ85Wq5QzrBnZ5q5QzrBnZ5q5Q.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/kPZ85Wq5QzrBnZ5q5QzrBnZ5q5Q.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Crime", "Thriller", "Action"),
                languages = listOf("Hindi"),
                releaseYear = 2018,
                durationMinutes = null,
                rating = 4.7f,
                voteCount = 32000,
                isTrending = true,
                seasonCount = 3,
                episodeCount = 27,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Pankaj Tripathi", "Ali Fazal", "Divyenndu", "Shweta Tripathi"),
                crew = listOf("Karan Anshuman (Creator)", "Gurmmeet Singh (Director)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/mirzapur_s1e1_480p.m3u8",
                    "720p" to "https://example.com/mirzapur_s1e1_720p.m3u8",
                    "1080p" to "https://example.com/mirzapur_s1e1_1080p.m3u8"
                )
            ),
            Content(
                id = "5",
                title = "Attack on Titan",
                description = "Humanity lives behind walls to protect themselves from giant humanoid Titans. When the walls are breached, Eren Yeager vows to eliminate every Titan.",
                posterUrl = "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                contentType = ContentType.ANIME,
                genres = listOf("Action", "Fantasy", "Drama", "Mystery"),
                languages = listOf("Japanese", "Hindi", "English", "Tamil"),
                releaseYear = 2013,
                durationMinutes = null,
                rating = 4.9f,
                voteCount = 50000,
                isTrending = true,
                isFeatured = true,
                seasonCount = 4,
                episodeCount = 89,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Yuki Kaji", "Yui Ishikawa", "Marina Inoue", "Hiroshi Kamiya"),
                crew = listOf("Hajime Isayama (Creator)", "Tetsurō Araki (Director)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/aot_s1e1_480p.m3u8",
                    "720p" to "https://example.com/aot_s1e1_720p.m3u8",
                    "1080p" to "https://example.com/aot_s1e1_1080p.m3u8"
                )
            ),
            Content(
                id = "6",
                title = "Animal",
                description = "A son's love for his father turns into an obsession, leading to a violent chain of events. Directed by Sandeep Reddy Vanga.",
                posterUrl = "https://image.tmdb.org/t/p/w500/animal_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/animal_backdrop.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Crime", "Drama"),
                languages = listOf("Hindi", "Tamil", "Telugu", "Malayalam", "Kannada"),
                releaseYear = 2023,
                durationMinutes = 201,
                rating = 4.3f,
                voteCount = 18000,
                isTrending = true,
                isNewRelease = true,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Ranbir Kapoor", "Rashmika Mandanna", "Anil Kapoor", "Bobby Deol"),
                crew = listOf("Sandeep Reddy Vanga (Director)", "Bhushan Kumar (Producer)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/animal_480p.m3u8",
                    "720p" to "https://example.com/animal_720p.m3u8",
                    "1080p" to "https://example.com/animal_1080p.m3u8"
                )
            ),
            Content(
                id = "7",
                title = "Sacred Games",
                description = "A link in their pasts leads an honest cop to a fugitive gang boss, whose cryptic warning spurs the officer on a quest to save Mumbai.",
                posterUrl = "https://image.tmdb.org/t/p/w500/sacred_games_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/sacred_games_backdrop.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Crime", "Thriller", "Drama", "Mystery"),
                languages = listOf("Hindi", "English"),
                releaseYear = 2018,
                durationMinutes = null,
                rating = 4.6f,
                voteCount = 28000,
                isTrending = false,
                seasonCount = 2,
                episodeCount = 16,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Saif Ali Khan", "Nawazuddin Siddiqui", "Radhika Apte"),
                crew = listOf("Vikramaditya Motwane (Director)", "Anurag Kashyap (Director)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/sg_s1e1_480p.m3u8",
                    "720p" to "https://example.com/sg_s1e1_720p.m3u8",
                    "1080p" to "https://example.com/sg_s1e1_1080p.m3u8"
                )
            ),
            Content(
                id = "8",
                title = "Kantara",
                description = "A fictional story of a Kambala champion who locks horns with an upright forest officer. Written and directed by Rishab Shetty.",
                posterUrl = "https://image.tmdb.org/t/p/w500/kantara_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/kantara_backdrop.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Thriller", "Fantasy"),
                languages = listOf("Kannada", "Hindi", "Tamil", "Telugu", "Malayalam"),
                releaseYear = 2022,
                durationMinutes = 148,
                rating = 4.7f,
                voteCount = 22000,
                isTrending = true,
                isFeatured = true,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Rishab Shetty", "Sapthami Gowda", "Kishore Kumar G"),
                crew = listOf("Rishab Shetty (Director/Writer)", "Vijay Kiragandur (Producer)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/kantara_480p.m3u8",
                    "720p" to "https://example.com/kantara_720p.m3u8",
                    "1080p" to "https://example.com/kantara_1080p.m3u8"
                )
            ),
            Content(
                id = "9",
                title = "RRR",
                description = "A fictitious story about two legendary revolutionaries and their journey away from home before they started fighting for their country in 1920s.",
                posterUrl = "https://image.tmdb.org/t/p/w500/rrr_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/rrr_backdrop.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Drama", "Historical"),
                languages = listOf("Telugu", "Hindi", "Tamil", "Malayalam", "Kannada", "English"),
                releaseYear = 2022,
                durationMinutes = 187,
                rating = 4.8f,
                voteCount = 45000,
                isTrending = true,
                isFeatured = true,
                quality = VideoQuality.ULTRA_HD,
                ageRating = "U/A 16+",
                cast = listOf("N. T. Rama Rao Jr.", "Ram Charan", "Ajay Devgn", "Alia Bhatt"),
                crew = listOf("S. S. Rajamouli (Director)", "D. V. V. Danayya (Producer)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/rrr_480p.m3u8",
                    "720p" to "https://example.com/rrr_720p.m3u8",
                    "1080p" to "https://example.com/rrr_1080p.m3u8",
                    "4K" to "https://example.com/rrr_4k.m3u8"
                )
            ),
            Content(
                id = "10",
                title = "Panchayat",
                description = "An engineering graduate ends up as a secretary of a Panchayat office in a remote village of Uttar Pradesh.",
                posterUrl = "https://image.tmdb.org/t/p/w500/panchayat_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/panchayat_backdrop.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Comedy", "Drama"),
                languages = listOf("Hindi"),
                releaseYear = 2020,
                durationMinutes = null,
                rating = 4.9f,
                voteCount = 35000,
                isTrending = true,
                isFeatured = true,
                seasonCount = 3,
                episodeCount = 20,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 13+",
                cast = listOf("Jitendra Kumar", "Neena Gupta", "Raghubir Yadav", "Chandan Roy"),
                crew = listOf("Deepak Kumar Mishra (Director)", "The Viral Fever (Producer)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/panchayat_s1e1_480p.m3u8",
                    "720p" to "https://example.com/panchayat_s1e1_720p.m3u8",
                    "1080p" to "https://example.com/panchayat_s1e1_1080p.m3u8"
                )
            )
        )
    }

    private fun generateContinueWatching(): List<Content> {
        val allContent = generateTrendingContent() + generateMoreContent()
        return allContent.shuffled().take(5).map { content ->
            content.copy(
                // Add watch progress metadata would be in a real app
            )
        }
    }

    private fun generateRecommendedContent(): List<Content> {
        return generateMoreContent().shuffled().take(10)
    }

    private fun generateMoreContent(): List<Content> {
        return listOf(
            Content(
                id = "11",
                title = "Drishyam 2",
                description = "A gripping tale of an investigation that threatens to expose a family's dark secret from seven years ago.",
                posterUrl = "https://image.tmdb.org/t/p/w500/drishyam2_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/drishyam2_backdrop.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Crime", "Thriller", "Drama"),
                languages = listOf("Hindi", "Malayalam"),
                releaseYear = 2022,
                durationMinutes = 140,
                rating = 4.6f,
                voteCount = 24000,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Ajay Devgn", "Tabu", "Akshaye Khanna", "Shriya Saran"),
                crew = listOf("Abhishek Pathak (Director)", "Jeethu Joseph (Original Writer)"),
                streamUrls = mapOf("720p" to "https://example.com/drishyam2_720p.m3u8", "1080p" to "https://example.com/drishyam2_1080p.m3u8")
            ),
            Content(
                id = "12",
                title = "Vikram Vedha",
                description = "A tough police officer sets out to track down and eliminate a dangerous gangster, but the lines between good and evil blur.",
                posterUrl = "https://image.tmdb.org/t/p/w500/vikramvedha_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/vikramvedha_backdrop.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Thriller", "Crime"),
                languages = listOf("Hindi", "Tamil"),
                releaseYear = 2022,
                durationMinutes = 160,
                rating = 4.5f,
                voteCount = 19000,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Hrithik Roshan", "Saif Ali Khan", "Radhika Apte"),
                crew = listOf("Pushkar-Gayathri (Directors)", "S. Sashikanth (Producer)"),
                streamUrls = mapOf("720p" to "https://example.com/vv_720p.m3u8", "1080p" to "https://example.com/vv_1080p.m3u8")
            ),
            Content(
                id = "13",
                title = "Aspirants",
                description = "Three friends navigate the ups and downs of UPSC preparation in Old Rajinder Nagar, Delhi.",
                posterUrl = "https://image.tmdb.org/t/p/w500/aspirants_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/aspirants_backdrop.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Drama", "Comedy"),
                languages = listOf("Hindi"),
                releaseYear = 2021,
                durationMinutes = null,
                rating = 4.8f,
                voteCount = 15000,
                seasonCount = 2,
                episodeCount = 10,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 13+",
                cast = listOf("Naveen Kasturia", "Shivankit Parihar", "Abhilash Thapliyal", "Sunny Hinduja"),
                crew = listOf("Arunabh Kumar (Creator)", "Apoorv Singh Karki (Director)"),
                streamUrls = mapOf("720p" to "https://example.com/aspirants_s1e1_720p.m3u8", "1080p" to "https://example.com/aspirants_s1e1_1080p.m3u8")
            ),
            Content(
                id = "14",
                title = "Kota Factory",
                description = "The story of students preparing for IIT JEE in Kota, Rajasthan - the coaching capital of India.",
                posterUrl = "https://image.tmdb.org/t/p/w500/kotafactory_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/kotafactory_backdrop.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Drama", "Comedy"),
                languages = listOf("Hindi"),
                releaseYear = 2019,
                durationMinutes = null,
                rating = 4.9f,
                voteCount = 22000,
                seasonCount = 2,
                episodeCount = 10,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 13+",
                cast = listOf("Jitendra Kumar", "Mayur More", "Ranjan Raj", "Alam Khan"),
                crew = listOf("Saurabh Khanna (Creator)", "Raghav Subbu (Director)"),
                streamUrls = mapOf("720p" to "https://example.com/kf_s1e1_720p.m3u8", "1080p" to "https://example.com/kf_s1e1_1080p.m3u8")
            ),
            Content(
                id = "15",
                title = "Demon Slayer",
                description = "Tanjiro Kamado becomes a demon slayer to save his sister and avenge his family.",
                posterUrl = "https://image.tmdb.org/t/p/w500/demonslayer_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/demonslayer_backdrop.jpg",
                contentType = ContentType.ANIME,
                genres = listOf("Action", "Fantasy", "Supernatural"),
                languages = listOf("Japanese", "Hindi", "English", "Tamil", "Telugu"),
                releaseYear = 2019,
                durationMinutes = null,
                rating = 4.8f,
                voteCount = 40000,
                seasonCount = 3,
                episodeCount = 54,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Natsuki Hanae", "Akari Kitō", "Hiro Shimono", "Yoshitsugu Matsuoka"),
                crew = listOf("Koyoharu Gotouge (Creator)", "Haruo Sotozaki (Director)"),
                streamUrls = mapOf("720p" to "https://example.com/ds_s1e1_720p.m3u8", "1080p" to "https://example.com/ds_s1e1_1080p.m3u8")
            ),
            Content(
                id = "16",
                title = "One Piece",
                description = "Monkey D. Luffy and his pirate crew explore the Grand Line in search of the ultimate treasure, One Piece.",
                posterUrl = "https://image.tmdb.org/t/p/w500/onepiece_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/onepiece_backdrop.jpg",
                contentType = ContentType.ANIME,
                genres = listOf("Adventure", "Fantasy", "Action", "Comedy"),
                languages = listOf("Japanese", "Hindi", "English"),
                releaseYear = 1999,
                durationMinutes = null,
                rating = 4.9f,
                voteCount = 60000,
                seasonCount = 20,
                episodeCount = 1100,
                quality = VideoQuality.HD,
                ageRating = "U/A 13+",
                cast = listOf("Mayumi Tanaka", "Kazuya Nakai", "Akemi Okamura", "Kappei Yamaguchi"),
                crew = listOf("Eiichiro Oda (Creator)", "Toei Animation (Studio)"),
                streamUrls = mapOf("720p" to "https://example.com/op_s1e1_720p.m3u8", "1080p" to "https://example.com/op_s1e1_1080p.m3u8")
            ),
            Content(
                id = "17",
                title = "Money Heist",
                description = "An unusual group of robbers attempt to carry out the most perfect robbery in Spanish history.",
                posterUrl = "https://image.tmdb.org/t/p/w500/moneyheist_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/moneyheist_backdrop.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Crime", "Thriller", "Drama"),
                languages = listOf("Spanish", "Hindi", "English", "Tamil", "Telugu"),
                releaseYear = 2017,
                durationMinutes = null,
                rating = 4.7f,
                voteCount = 55000,
                seasonCount = 5,
                episodeCount = 41,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Álvaro Morte", "Úrsula Corberó", "Itziar Ituño", "Pedro Alonso"),
                crew = listOf("Álex Pina (Creator)", "Jesús Colmenar (Director)"),
                streamUrls = mapOf("720p" to "https://example.com/mh_s1e1_720p.m3u8", "1080p" to "https://example.com/mh_s1e1_1080p.m3u8")
            ),
            Content(
                id = "18",
                title = "Stranger Things",
                description = "When a young boy vanishes, a small town uncovers a mystery involving secret experiments and supernatural forces.",
                posterUrl = "https://image.tmdb.org/t/p/w500/strangerthings_poster.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/strangerthings_backdrop.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Sci-Fi", "Horror", "Drama", "Mystery"),
                languages = listOf("English", "Hindi", "Tamil", "Telugu"),
                releaseYear = 2016,
                durationMinutes = null,
                rating = 4.8f,
                voteCount = 48000,
                seasonCount = 4,
                episodeCount = 34,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Millie Bobby Brown", "Finn Wolfhard", "Winona Ryder", "David Harbour"),
                crew = listOf("The Duffer Brothers (Creators)", "Shawn Levy (Director)"),
                streamUrls = mapOf("720p" to "https://example.com/st_s1e1_720p.m3u8", "1080p" to "https://example.com/st_s1e1_1080p.m3u8")
            )
        )
    }

    private fun generateFeaturedCollections(): List<FeaturedCollection> {
        val allContent = generateTrendingContent() + generateMoreContent()
        return listOf(
            FeaturedCollection(
                id = "fc1",
                title = "Blockbuster Hits",
                description = "The biggest Indian blockbusters of recent years",
                content = allContent.filter { it.contentType == ContentType.MOVIE && it.rating >= 4.5 }.shuffled().take(10),
                bannerUrl = "https://example.com/banner_blockbusters.jpg"
            ),
            FeaturedCollection(
                id = "fc2",
                title = "Binge-Worthy Web Series",
                description = "Complete series you can watch in one sitting",
                content = allContent.filter { it.contentType in setOf(ContentType.WEB_SERIES, ContentType.TV_SHOW) }.shuffled().take(10),
                bannerUrl = "https://example.com/banner_webseries.jpg"
            ),
            FeaturedCollection(
                id = "fc3",
                title = "Anime Universe",
                description = "Popular anime series and movies dubbed in Hindi",
                content = allContent.filter { it.contentType == ContentType.ANIME }.shuffled().take(10),
                bannerUrl = "https://example.com/banner_anime.jpg"
            ),
            FeaturedCollection(
                id = "fc4",
                title = "South Indian Cinema",
                description = "Best of Tamil, Telugu, Malayalam, and Kannada cinema",
                content = allContent.filter { it.languages.any { it in setOf("Tamil", "Telugu", "Malayalam", "Kannada") } }.shuffled().take(10),
                bannerUrl = "https://example.com/banner_south.jpg"
            ),
            FeaturedCollection(
                id = "fc5",
                title = "Action Packed",
                description = "High-octane action movies and series",
                content = allContent.filter { "Action" in it.genres }.shuffled().take(10),
                bannerUrl = "https://example.com/banner_action.jpg"
            ),
            FeaturedCollection(
                id = "fc6",
                title = "Family Entertainment",
                description = "Content suitable for the whole family",
                content = allContent.filter { it.ageRating.startsWith("U") }.shuffled().take(10),
                bannerUrl = "https://example.com/banner_family.jpg"
            )
        )
    }

    private fun generateLiveChannels(): List<LiveChannel> {
        return listOf(
            LiveChannel("lc1", "ABP News", "https://example.com/abp_logo.png", "https://example.com/abp_live.m3u8", LiveTvCategory.NEWS, 101, currentProgram = "Breaking News", nextProgram = "Prime Time Debate"),
            LiveChannel("lc2", "Aaj Tak", "https://example.com/aajtak_logo.png", "https://example.com/aajtak_live.m3u8", LiveTvCategory.NEWS, 102, currentProgram = "Headlines", nextProgram = "Special Report"),
            LiveChannel("lc3", "NDTV 24x7", "https://example.com/ndtv_logo.png", "https://example.com/ndtv_live.m3u8", LiveTvCategory.NEWS, 103, currentProgram = "News Hour", nextProgram = "The Big Fight"),
            LiveChannel("lc4", "Star Sports 1", "https://example.com/starsports1_logo.png", "https://example.com/starsports1_live.m3u8", LiveTvCategory.SPORTS, 201, currentProgram = "LIVE: India vs Australia", nextProgram = "Match Highlights"),
            LiveChannel("lc5", "Star Sports 2", "https://example.com/starsports2_logo.png", "https://example.com/starsports2_live.m3u8", LiveTvCategory.SPORTS, 202, currentProgram = "Premier League", nextProgram = "Football Analysis"),
            LiveChannel("lc6", "Sony Ten 1", "https://example.com/sonyten1_logo.png", "https://example.com/sonyten1_live.m3u8", LiveTvCategory.SPORTS, 203, currentProgram = "WWE Raw", nextProgram = "WWE SmackDown"),
            LiveChannel("lc7", "Star Gold", "https://example.com/stargold_logo.png", "https://example.com/stargold_live.m3u8", LiveTvCategory.MOVIES, 301, currentProgram = "Movie: Pathaan", nextProgram = "Movie: Jawan"),
            LiveChannel("lc8", "Star Gold 2", "https://example.com/stargold2_logo.png", "https://example.com/stargold2_live.m3u8", LiveTvCategory.MOVIES, 302, currentProgram = "Movie: RRR", nextProgram = "Movie: Bahubali"),
            LiveChannel("lc9", "Zee Cinema", "https://example.com/zeecinema_logo.png", "https://example.com/zeecinema_live.m3u8", LiveTvCategory.MOVIES, 303, currentProgram = "Movie: Animal", nextProgram = "Movie: Gadar 2"),
            LiveChannel("lc10", "MTV", "https://example.com/mtv_logo.png", "https://example.com/mtv_live.m3u8", LiveTvCategory.MUSIC, 401, currentProgram = "MTV Unplugged", nextProgram = "Top 20 Countdown"),
            LiveChannel("lc11", "9X Jalwa", "https://example.com/9xjalwa_logo.png", "https://example.com/9xjalwa_live.m3u8", LiveTvCategory.MUSIC, 402, currentProgram = "Bollywood Hits", nextProgram = "Retro Classics"),
            LiveChannel("lc12", "Nickelodeon", "https://example.com/nick_logo.png", "https://example.com/nick_live.m3u8", LiveTvCategory.KIDS, 501, currentProgram = "Doraemon", nextProgram = "Shin Chan"),
            LiveChannel("lc13", "Cartoon Network", "https://example.com/cn_logo.png", "https://example.com/cn_live.m3u8", LiveTvCategory.KIDS, 502, currentProgram = "Teen Titans Go!", nextProgram = "Ben 10"),
            LiveChannel("lc14", "Aastha", "https://example.com/aastha_logo.png", "https://example.com/aastha_live.m3u8", LiveTvCategory.DEVOTIONAL, 601, currentProgram = "Morning Aarti", nextProgram = "Bhajan Sandhya"),
            LiveChannel("lc15", "Sanskar", "https://example.com/sanskar_logo.png", "https://example.com/sanskar_live.m3u8", LiveTvCategory.DEVOTIONAL, 602, currentProgram = "Discourse", nextProgram = "Live Darshan"),
            LiveChannel("lc16", "Colors", "https://example.com/colors_logo.png", "https://example.com/colors_live.m3u8", LiveTvCategory.ENTERTAINMENT, 701, currentProgram = "Bigg Boss", nextProgram = "Khatron Ke Khiladi"),
            LiveChannel("lc17", "Star Plus", "https://example.com/starplus_logo.png", "https://example.com/starplus_live.m3u8", LiveTvCategory.ENTERTAINMENT, 702, currentProgram = "Anupamaa", nextProgram = "Yeh Rishta Kya Kehlata Hai"),
            LiveChannel("lc18", "Sony TV", "https://example.com/sonytv_logo.png", "https://example.com/sonytv_live.m3u8", LiveTvCategory.ENTERTAINMENT, 703, currentProgram = "Kaun Banega Crorepati", nextProgram = "The Kapil Sharma Show"),
            LiveChannel("lc19", "Discovery", "https://example.com/discovery_logo.png", "https://example.com/discovery_live.m3u8", LiveTvCategory.KNOWLEDGE, 801, currentProgram = "Man vs Wild", nextProgram = "MythBusters"),
            LiveChannel("lc20", "History TV18", "https://example.com/history_logo.png", "https://example.com/history_live.m3u8", LiveTvCategory.KNOWLEDGE, 802, currentProgram = "Ancient Aliens", nextProgram = "Pawn Stars")
        )
    }

    private fun generateWatchHistory(): List<WatchHistoryItem> {
        val allContent = generateTrendingContent() + generateMoreContent()
        return allContent.shuffled().take(10).mapIndexed { index, content ->
            WatchHistoryItem(
                contentId = content.id,
                content = content,
                watchedAt = System.currentTimeMillis() - (index * 86400000L),
                progress = (Math.random() * 100).toInt(),
                episodeId = if (content.contentType != ContentType.MOVIE) "ep_${content.id}_1" else null
            )
        }
    }

    private fun generateDownloads(): List<DownloadItem> {
        val allContent = generateTrendingContent() + generateMoreContent()
        return allContent.shuffled().take(5).mapIndexed { index, content ->
            DownloadItem(
                id = "dl_${content.id}",
                contentId = content.id,
                content = content,
                downloadUrl = content.streamUrls["720p"] ?: content.streamUrls.values.firstOrNull() ?: "",
                quality = "720p",
                progress = 100,
                status = DownloadStatus.COMPLETED,
                fileSize = (1.5 + Math.random() * 3.0).toString().take(4).toDouble(),
                downloadedAt = System.currentTimeMillis() - (index * 3600000L),
                episodeId = if (content.contentType != ContentType.MOVIE) "ep_${content.id}_1" else null
            )
        }
    }

    // Public API methods
    suspend fun toggleWatchlist(contentId: String) {
        val current = _watchlist.value.toMutableSet()
        if (contentId in current) {
            current.remove(contentId)
        } else {
            current.add(contentId)
        }
        _watchlist.value = current
    }

    suspend fun addToWatchHistory(item: WatchHistoryItem) {
        val current = _watchHistory.value.toMutableList()
        current.removeAll { it.contentId == item.contentId && it.episodeId == item.episodeId }
        current.add(0, item)
        if (current.size > 50) current.removeAt(current.size - 1)
        _watchHistory.value = current
    }

    suspend fun clearWatchHistory() {
        _watchHistory.value = emptyList()
    }

    suspend fun addSearchQuery(query: String) {
        val current = _searchHistory.value.toMutableList()
        current.remove(query)
        current.add(0, query)
        if (current.size > 20) current.removeAt(current.size - 1)
        _searchHistory.value = current
    }

    suspend fun clearSearchHistory() {
        _searchHistory.value = emptyList()
    }

    suspend fun updateUserPreferences(preferences: UserPreferences) {
        _userPreferences.value = preferences
    }

    suspend fun addDownload(item: DownloadItem) {
        val current = _downloads.value.toMutableList()
        current.add(0, item)
        _downloads.value = current
    }

    suspend fun removeDownload(downloadId: String) {
        val current = _downloads.value.toMutableList()
        current.removeAll { it.id == downloadId }
        _downloads.value = current
    }

    suspend fun updateDownloadProgress(downloadId: String, progress: Int, status: DownloadStatus) {
        val current = _downloads.value.toMutableList()
        val index = current.indexOfFirst { it.id == downloadId }
        if (index != -1) {
            val item = current[index]
            current[index] = item.copy(progress = progress, status = status)
            _downloads.value = current
        }
    }
}

@Serializable
data class FeaturedCollection(
    val id: String,
    val title: String,
    val description: String,
    val content: List<Content>,
    val bannerUrl: String
)

@Serializable
data class WatchHistoryItem(
    val contentId: String,
    val content: Content,
    val watchedAt: Long,
    val progress: Int,
    val episodeId: String?
)

@Serializable
data class DownloadItem(
    val id: String,
    val contentId: String,
    val content: Content,
    val downloadUrl: String,
    val quality: String,
    val progress: Int,
    val status: DownloadStatus,
    val fileSize: Double, // GB
    val downloadedAt: Long,
    val episodeId: String?
)

enum class DownloadStatus {
    QUEUED,
    DOWNLOADING,
    PAUSED,
    COMPLETED,
    FAILED,
    REMOVED
}
