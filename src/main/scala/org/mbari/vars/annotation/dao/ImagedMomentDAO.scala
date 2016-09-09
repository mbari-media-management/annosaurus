package org.mbari.vars.annotation.dao

import java.time.{ Duration, Instant }
import java.util.UUID

import org.mbari.vars.annotation.dao.jpa.ImagedMomentImpl
import org.mbari.vars.annotation.model.ImagedMoment
import org.mbari.vcr4j.time.Timecode

/**
 *
 *
 * @author Brian Schlining
 * @since 2016-06-17T16:07:00
 */
trait ImagedMomentDAO[T <: ImagedMoment] extends DAO[T] {

  def newPersistentObject(
    videoReferenceUUID: UUID,
    timecode: Option[Timecode] = None,
    elapsedTime: Option[Duration] = None,
    recordedDate: Option[Instant] = None
  ): T

  def findAllVideoReferenceUUIDs(limit: Option[Int], offset: Option[Int]): Iterable[UUID]
  def findByVideoReferenceUUID(uuid: UUID, limit: Option[Int] = None, offset: Option[Int] = None): Iterable[T]

  def findWithImageReferences(videoReferenceUUID: UUID): Iterable[T]

  def findByVideoReferenceUUIDAndTimecode(uuid: UUID, timecode: Timecode): Option[T]
  def findByVideoReferenceUUIDAndRecordedDate(uuid: UUID, recordedDate: Instant): Option[T]
  def findByVideoReferenceUUIDAndElapsedTime(uuid: UUID, elapsedTime: Duration): Option[T]

  /**
   * Look up an imaged moment based on the videoReferenceUUID and one of the indices into a video.
   * The order of search is
   * 1. Timecode
   * 2. ElapsedTime
   * 3. RecordedDate
   *
   * @param uuid The videoReferenceUUID that the imagedMoment is attached to
   * @param timecode The timecode index
   * @param elapsedTime The elapsedTime index (This is the index of runtime into the video)
   * @param recordedDate The recordedDate index
   * @return
   */
  def findByVideoReferenceUUIDAndIndex(
    uuid: UUID,
    timecode: Option[Timecode] = None,
    elapsedTime: Option[Duration] = None,
    recordedDate: Option[Instant] = None
  ): Option[T] = {
    var imagedMoment = timecode.flatMap(findByVideoReferenceUUIDAndTimecode(uuid, _))
    imagedMoment = if (imagedMoment.isDefined) imagedMoment else elapsedTime.flatMap(findByVideoReferenceUUIDAndElapsedTime(uuid, _))
    if (imagedMoment.isDefined) imagedMoment else recordedDate.flatMap(findByVideoReferenceUUIDAndRecordedDate(uuid, _))
  }

  def findByObservationUUID(uuid: UUID): Option[T]

  /**
   * Deletes an imagedMoment if it does not contain any observations or imageReferences
   * @param imagedMoment The object to delete
   * @return true if deleted, false if not deleted.
   */
  def deleteIfEmpty(imagedMoment: T): Boolean = deleteIfEmptyByUUID(imagedMoment.uuid)

  def deleteIfEmptyByUUID(uuid: UUID): Boolean = {
    println("!!!DeleteifEmptyByUUID")
    findByUUID(uuid).map(imagedMoment => {

      if (imagedMoment.imageReferences.isEmpty && imagedMoment.observations.isEmpty) {

        delete(imagedMoment)
        true
      } else false
    }).getOrElse(false)
  }
}
