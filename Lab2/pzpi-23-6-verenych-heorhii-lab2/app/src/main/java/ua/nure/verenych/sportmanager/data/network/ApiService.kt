package ua.nure.verenych.sportmanager.data.network

import ua.nure.verenych.sportmanager.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Auth
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // Profile
    @GET("profile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<ProfileData>

    @GET("profile/events")
    suspend fun getMyEvents(@Header("Authorization") token: String): Response<List<Event>>

    @GET("profile/stats")
    suspend fun getMyStats(@Header("Authorization") token: String): Response<List<PlayerStat>>

    @GET("profile/attendance")
    suspend fun getMyAttendance(@Header("Authorization") token: String): Response<List<AttendanceRecord>>

    @GET("profile/team")
    suspend fun getMyTeam(@Header("Authorization") token: String): Response<List<TeamMember>>

    @GET("injuries")
    suspend fun getMyInjuries(@Header("Authorization") token: String): Response<List<Injury>>
}
