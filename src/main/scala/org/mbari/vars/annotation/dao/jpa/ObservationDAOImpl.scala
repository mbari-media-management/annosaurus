package org.mbari.vars.annotation.dao.jpa

import java.util.UUID
import javax.persistence.EntityManager

import org.mbari.vars.annotation.dao.ObservationDAO

import scala.collection.JavaConverters._

/**
 *
 *
 * @author Brian Schlining
 * @since 2016-06-17T17:10:00
 */
class ObservationDAOImpl(entityManager: EntityManager)
    extends BaseDAO[ObservationImpl](entityManager)
    with ObservationDAO[ObservationImpl] {

  override def newPersistentObject(): ObservationImpl = new ObservationImpl

  override def findByVideoReferenceUUID(uuid: UUID, limit: Option[Int] = None, offset: Option[Int] = None): Iterable[ObservationImpl] =
    findByNamedQuery("Observation.findByVideoReferenceUUID", Map("uuid" -> uuid))

  /**
   *
   * @return Order sequence of all concept names used
   */
  override def findAllNames(): Seq[String] = entityManager.createNamedQuery("Observation.findAllNames")
    .getResultList
    .asScala
    .filter(_ != null)
    .map(_.toString)

  override def findAll(): Iterable[ObservationImpl] =
    findByNamedQuery("Observation.findAll")

  override def findAll(limit: Int, offset: Int): Iterable[ObservationImpl] =
    findByNamedQuery("Observation.findAll", limit = Some(limit), offset = Some(offset))

  override def findAllNamesByVideoReferenceUUID(uuid: UUID): Seq[String] = {
    val query = entityManager.createNamedQuery("Observation.findAllNamesByVideoReferenceUUID")
    query.setParameter(1, UUIDConverter.uuidToString(uuid))
    query.getResultList
      .asScala
      .map(_.toString)
  }
}
