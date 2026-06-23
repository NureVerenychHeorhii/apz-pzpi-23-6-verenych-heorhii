package ua.nure.verenych.sportmanager.data.model

import com.google.gson.annotations.SerializedName

// ── Auth ──────────────────────────────────────────────────────────────────────

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(
    val token: String,
    val user: UserInfo
)

data class UserInfo(
    val id: Int,
    val role: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name")  val lastName: String,
    @SerializedName("team_id")    val teamId: Int?
)

// ── Profile ───────────────────────────────────────────────────────────────────

data class ProfileData(
    val user: UserFull,
    val stats: AggregatedStats,
    val attendance: AttendanceSummary,
    val injuries: List<Injury>
)

data class UserFull(
    val id: Int,
    @SerializedName("first_name")   val firstName: String,
    @SerializedName("last_name")    val lastName: String,
    val email: String,
    val role: String,
    @SerializedName("team_id")      val teamId: Int?,
    @SerializedName("team_name")    val teamName: String?,
    @SerializedName("team_category") val teamCategory: String?
)

data class AggregatedStats(
    @SerializedName("total_goals")  val totalGoals: Int,
    @SerializedName("games_played") val gamesPlayed: Int,
    @SerializedName("avg_rating")   val avgRating: Double?
)

data class AttendanceSummary(
    val total: Int,
    val present: Int,
    val absent: Int,
    val late: Int
)

// ── Event ─────────────────────────────────────────────────────────────────────

data class Event(
    val id: Int,
    val title: String,
    val description: String?,
    @SerializedName("event_date") val eventDate: String,
    val location: String?,
    @SerializedName("team_id")   val teamId: Int?,
    @SerializedName("team_name") val teamName: String?
)

// ── Player stat per event ─────────────────────────────────────────────────────

data class PlayerStat(
    val id: Int,
    val rating: Double?,
    val goals: Int?,
    @SerializedName("coach_comment") val coachComment: String?,
    @SerializedName("event_title")   val eventTitle: String,
    @SerializedName("event_date")    val eventDate: String
)

// ── Injury ────────────────────────────────────────────────────────────────────

data class Injury(
    val id: Int,
    @SerializedName("injury_type")             val injuryType: String,
    @SerializedName("incident_date")           val incidentDate: String,
    @SerializedName("expected_recovery_date")  val recoveryDate: String?,
    val status: String,
    val notes: String?
)

// ── Attendance record ─────────────────────────────────────────────────────────

data class AttendanceRecord(
    val id: Int,
    val status: String,
    val title: String,
    @SerializedName("event_date") val eventDate: String,
    val location: String?
)

// ── Team member ───────────────────────────────────────────────────────────────

data class TeamMember(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name")  val lastName: String,
    val role: String,
    val email: String
)
