package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.DiarioMiccionalDao
import com.example.data.local.DiarioMiccionalEpisode
import com.example.data.local.PelviCareDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class DiarioMiccionalRoomTest {

  private lateinit var db: PelviCareDatabase
  private lateinit var dao: DiarioMiccionalDao

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(context, PelviCareDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    dao = db.diarioMiccionalDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  @Test
  fun testInsertAndRetrieveDiarioMiccionalEpisode() = runBlocking {
    val episode = DiarioMiccionalEpisode(
      timestamp = System.currentTimeMillis(),
      formattedTime = "15:30",
      dayOfProtocol = 2,
      volumeMl = 250,
      urgencyLevel = 2,
      hadLeakage = false,
      leakageIntensity = "Nenhuma",
      activityContext = "Repouso",
      episodeType = "MICCAO",
      notes = "Teste de micção com volume de 250ml e urgência nível 2"
    )

    val insertedId = dao.insertEpisode(episode)
    assertTrue(insertedId > 0)

    val episodes = dao.getAllEpisodes().first()
    assertEquals(1, episodes.size)

    val retrieved = episodes.first()
    assertEquals(250, retrieved.volumeMl)
    assertEquals(2, retrieved.urgencyLevel)
    assertEquals("15:30", retrieved.formattedTime)
    assertEquals(2, retrieved.dayOfProtocol)
    assertEquals("MICCAO", retrieved.episodeType)
  }

  @Test
  fun testDeleteEpisode() = runBlocking {
    val episode = DiarioMiccionalEpisode(
      timestamp = System.currentTimeMillis(),
      formattedTime = "10:00",
      dayOfProtocol = 1,
      volumeMl = 180,
      urgencyLevel = 3,
      hadLeakage = true,
      leakageIntensity = "Gotas",
      activityContext = "Tosse",
      episodeType = "ESCAPE",
      notes = "Escape leve"
    )

    val id = dao.insertEpisode(episode)
    assertEquals(1, dao.getCount())

    dao.deleteEpisodeById(id)
    assertEquals(0, dao.getCount())
  }
}
