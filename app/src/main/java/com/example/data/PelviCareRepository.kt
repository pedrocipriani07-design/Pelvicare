package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object PelviCareRepository {

  private val _patientProfile = MutableStateFlow(PatientProfile())
  val patientProfile: StateFlow<PatientProfile> = _patientProfile.asStateFlow()

  private val _isDoctorPerspective = MutableStateFlow(false)
  val isDoctorPerspective: StateFlow<Boolean> = _isDoctorPerspective.asStateFlow()

  private val _waterIntakeMl = MutableStateFlow(1650)
  val waterIntakeMl: StateFlow<Int> = _waterIntakeMl.asStateFlow()

  private val _waterTargetMl = MutableStateFlow(2000)
  val waterTargetMl: StateFlow<Int> = _waterTargetMl.asStateFlow()

  private val _daytimeVoids = MutableStateFlow(5)
  val daytimeVoids: StateFlow<Int> = _daytimeVoids.asStateFlow()

  private val _leakagesToday = MutableStateFlow(0)
  val leakagesToday: StateFlow<Int> = _leakagesToday.asStateFlow()

  private val _is34WeeksTransitioned = MutableStateFlow(false)
  val is34WeeksTransitioned: StateFlow<Boolean> = _is34WeeksTransitioned.asStateFlow()

  private val _diarioEntries = MutableStateFlow(
    listOf(
      DiarioEntry(
        id = "1",
        time = "14:15",
        type = DiarioType.HIDRATACAO,
        title = "Copo de água mineral",
        detail = "Volume ingerido: 300 ml",
        volumeMl = 300
      ),
      DiarioEntry(
        id = "2",
        time = "13:40",
        type = DiarioType.MICCAO,
        title = "Micção normal no banheiro",
        detail = "Dor: Grau 0 • Sem sensação de urgência"
      ),
      DiarioEntry(
        id = "3",
        time = "11:20",
        type = DiarioType.ESCAPE,
        title = "Escape leve ao espirrar",
        detail = "Volume: Gotas • Com protetor diário"
      ),
      DiarioEntry(
        id = "4",
        time = "10:00",
        type = DiarioType.INFUSAO,
        title = "Chá de camomila morno",
        detail = "Volume ingerido: 200 ml",
        volumeMl = 200
      ),
      DiarioEntry(
        id = "5",
        time = "08:30",
        type = DiarioType.MICCAO,
        title = "Micção matinal ao acordar",
        detail = "Volume abundante • Jato regular contínuo"
      ),
      DiarioEntry(
        id = "6",
        time = "04:15",
        type = DiarioType.NOCTURIA,
        title = "Despertar noturno para urinar",
        detail = "1ª interrupção de sono • Volume moderado"
      )
    )
  )
  val diarioEntries: StateFlow<List<DiarioEntry>> = _diarioEntries.asStateFlow()

  private val _feedbacks = MutableStateFlow(
    listOf(
      ClinicalFeedback(
        id = "f1",
        date = "Hoje, 18 Março • 15:30",
        title = "Treino Pélvico + Diafragma",
        sessionDuration = "15 min",
        borgScore = 4,
        evaScore = 0,
        patientMessage = "“Senti mais facilidade na respiração do 3º exercício e consegui isolar bem a musculatura sem apertar os glúteos!”",
        doctorResponse = "“Excelente, Camila! A coordenação diafragma-períneo é o maior divisor de águas para evitar escapes. Seguimos firmes no protocolo!”",
        doctorResponseTime = "Hoje, 16:10",
        status = "Respondido"
      ),
      ClinicalFeedback(
        id = "f2",
        date = "Ontem, 17 Março • 09:15",
        title = "Fibras Fásicas (Contrações Rápidas)",
        sessionDuration = "8 min",
        borgScore = 3,
        evaScore = 0,
        patientMessage = "“Subi 2 lances de escadas sem sensação de peso pélvico logo após o treino matinal.”",
        doctorResponse = null,
        status = "Sincronizado"
      ),
      ClinicalFeedback(
        id = "f3",
        date = "15 Março • 16:00",
        title = "Sustentação + Bola Suíça",
        sessionDuration = "15 min",
        borgScore = 5,
        evaScore = 1,
        patientMessage = "“Paciente relatou leve fadiga no final da 3ª série. A Dra. Luana recomendou descanso consciente de 12s entre as séries tônicas.”",
        doctorResponse = null,
        status = "Ajuste Feito"
      )
    )
  )
  val feedbacks: StateFlow<List<ClinicalFeedback>> = _feedbacks.asStateFlow()

  val scheduleToday = listOf(
    ScheduleItem(
      time = "08:30",
      durationMin = 50,
      patientName = "Beatriz Souza",
      room = "Sala 1",
      treatment = "Avaliação Inicial • Incontinência Urinária de Esforço (IUE)",
      status = "Concluído",
      note = "Oxford Grau 2 • Prontuário salvo"
    ),
    ScheduleItem(
      time = "10:00",
      durationMin = 60,
      patientName = "Camila Mendonça",
      room = "Box 2",
      treatment = "Sessão 5 • Biofeedback Eletromiográfico TMAP",
      status = "Ao Vivo",
      note = "Fadiga: Baixa",
      emgPeak = "28.4 µV"
    ),
    ScheduleItem(
      time = "11:30",
      durationMin = 45,
      patientName = "Juliana Ramos",
      room = "Consultório 2",
      treatment = "Preparação Perineal de Parto & Massagem Perineal",
      status = "Próximo",
      note = "34ª Semana • Uso de EPI-NO liberado pelo obstetra"
    ),
    ScheduleItem(
      time = "14:00",
      durationMin = 50,
      patientName = "Larissa Duarte",
      room = "Consultório 1",
      treatment = "Reavaliação Pós-Parto Tardio (6º mês)",
      status = "Tarde",
      note = "Protocolo de Diástase dos Retos Abdominais"
    )
  )

  fun togglePerspective() {
    _isDoctorPerspective.value = !_isDoctorPerspective.value
  }

  fun setDoctorPerspective(isDoctor: Boolean) {
    _isDoctorPerspective.value = isDoctor
  }

  fun addWater(amountMl: Int = 250) {
    _waterIntakeMl.value += amountMl
    val newEntry = DiarioEntry(
      id = System.currentTimeMillis().toString(),
      time = "Agora",
      type = DiarioType.HIDRATACAO,
      title = "Copo de água adicionado",
      detail = "Volume ingerido: $amountMl ml",
      volumeMl = amountMl
    )
    _diarioEntries.value = listOf(newEntry) + _diarioEntries.value
  }

  fun registerVoid() {
    _daytimeVoids.value += 1
    val newEntry = DiarioEntry(
      id = System.currentTimeMillis().toString(),
      time = "Agora",
      type = DiarioType.MICCAO,
      title = "Micção voluntária no banheiro",
      detail = "Fluxo fisiológico regular"
    )
    _diarioEntries.value = listOf(newEntry) + _diarioEntries.value
  }

  fun registerLeak() {
    _leakagesToday.value += 1
    val newEntry = DiarioEntry(
      id = System.currentTimeMillis().toString(),
      time = "Agora",
      type = DiarioType.ESCAPE,
      title = "Escape involuntário registrado",
      detail = "Esforço / tosse leve"
    )
    _diarioEntries.value = listOf(newEntry) + _diarioEntries.value
  }

  fun registerUrgency() {
    val newEntry = DiarioEntry(
      id = System.currentTimeMillis().toString(),
      time = "Agora",
      type = DiarioType.MICCAO,
      title = "Urgência súbita relatada",
      detail = "Sensação súbita com contenção"
    )
    _diarioEntries.value = listOf(newEntry) + _diarioEntries.value
  }

  fun addFeedback(feedback: ClinicalFeedback) {
    _feedbacks.value = listOf(feedback) + _feedbacks.value
  }

  fun confirm34WeeksTransition() {
    _is34WeeksTransitioned.value = true
    _patientProfile.value = _patientProfile.value.copy(
      gestationalWeeks = 34,
      gestationalDays = 0,
      daysUntilDpp = 42
    )
  }

  fun updateProfile(updated: PatientProfile) {
    _patientProfile.value = updated
  }
}
