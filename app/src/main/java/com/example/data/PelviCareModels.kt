package com.example.data

data class PatientProfile(
  val id: String = "#PC-84920",
  val name: String = "Camila Mendonça",
  val age: Int = 34,
  val gestationalWeeks: Int = 32,
  val gestationalDays: Int = 4,
  val previousCesareans: Int = 1,
  val gestas: Int = 2,
  val partos: Int = 1,
  val abortos: Int = 0,
  val dum: String = "05/08/2024",
  val dpp: String = "12/05/2025",
  val daysUntilDpp: Int = 52,
  val obstetrician: String = "Dr. Roberto Guimarães (CRM 142.890-SP)",
  val hospital: String = "Hospital e Maternidade São Luiz - Itaim",
  val healthPlan: String = "Bradesco Top",
  val cpf: String = "***.482.910-**",
  val birthDate: String = "14/08/1990",
  val email: String = "camila.mendonca@email.com",
  val phone: String = "+55 (11) 98712-4590",
  val address: String = "Rua Oscar Freire, 1240, Jardins, São Paulo — SP",
  val avatarUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuBPXfZv8sAmf2qFRNFLDkE0ovyABjZW0kkj8DSc6olDuYRJ4UwekPdfu7WJBMZzNJJkYT5zCpae4fl4867B9atZD3qlAZaYaPAwEW0QTqrxS_oxvXR8NYNMZdc2yvlo4A-P25J3tWUGedXxToPwCY2RdSVaxwX6MKjM8RWtIA-Wk35n-Xl-K27pd630QnBmaR46C8cEI0t6JjjPZ6p_Yr6SdplI6uG3ckL8dukk6FFIXvfNL5WTpKo",
  val doctorName: String = "Dra. Luana Silva",
  val doctorTitle: String = "Fisioterapeuta Pélvica & Uroginecológica • CREFITO-3/284910-F",
  val doctorAvatarUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuD7qTPMRsFJw-CqHbYxg9BeUetjgbiqF6rD8E8rsSMeEGI59iLZW0M4Qa_b28BJn16I1-hTXxCcciGmiao1Q44Mw4ugxamLrkZClBx76v7tMgFSySY7PtrSR9XX1y8kyBALxEi3AzIDeWgOYBnv6x7oFko8M-a_VXTmRAUfLpoZEBsVWWK9kZd49ITibsUItawb3eNH7gigPww6QB3zPtotkDk28pFCNx1E84ZdYBE9Hb84UnqfbKc",
  val primaryComplaint: String = "Incontinência Urinária Mista (IUM) com perda ao esforço (tosse) associada à urgência miccional e dor lombopélvica em sacroilíaca E."
)

data class ExerciseModule(
  val number: String,
  val title: String,
  val subtitle: String,
  val description: String,
  val metricLabel1: String,
  val metricValue1: String,
  val metricLabel2: String,
  val metricValue2: String,
  val volumeLabel: String,
  val volumeValue: String,
  val isCompleted: Boolean = false,
  val iconName: String
)

data class DiarioEntry(
  val id: String,
  val time: String,
  val type: DiarioType,
  val title: String,
  val detail: String,
  val volumeMl: Int? = null,
  val note: String? = null
)

enum class DiarioType {
  HIDRATACAO,
  MICCAO,
  ESCAPE,
  INFUSAO,
  NOCTURIA
}

data class ClinicalFeedback(
  val id: String,
  val date: String,
  val title: String,
  val sessionDuration: String,
  val borgScore: Int,
  val evaScore: Int,
  val patientMessage: String,
  val doctorResponse: String? = null,
  val doctorResponseTime: String? = null,
  val status: String = "Respondido"
)

data class ScheduleItem(
  val time: String,
  val durationMin: Int,
  val patientName: String,
  val room: String,
  val treatment: String,
  val status: String, // "Concluído", "Ao Vivo", "Próximo", "Tarde"
  val note: String,
  val emgPeak: String? = null
)
