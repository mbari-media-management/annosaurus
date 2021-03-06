/*
 * Copyright 2017 Monterey Bay Aquarium Research Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mbari.vars.annotation.dao.jpa

import java.util.UUID
import javax.persistence.EntityManager

import org.mbari.vars.annotation.dao.CachedVideoReferenceInfoDAO
import scala.jdk.CollectionConverters._

/**
  *
  *
  * @author Brian Schlining
  * @since 2016-06-17T17:15:00
  */
class CachedVideoReferenceInfoDAOImpl(entityManager: EntityManager)
    extends BaseDAO[CachedVideoReferenceInfoImpl](entityManager)
    with CachedVideoReferenceInfoDAO[CachedVideoReferenceInfoImpl] {

  override def newPersistentObject(): CachedVideoReferenceInfoImpl =
    new CachedVideoReferenceInfoImpl

  override def findByMissionContact(
      missionContact: String
  ): Iterable[CachedVideoReferenceInfoImpl] =
    findByNamedQuery("VideoReferenceInfo.findByMissionContact", Map("contact" -> missionContact))

  override def findByPlatformName(platformName: String): Iterable[CachedVideoReferenceInfoImpl] =
    findByNamedQuery("VideoReferenceInfo.findByPlatformName", Map("name" -> platformName))

  override def findByMissionID(missionID: String): Iterable[CachedVideoReferenceInfoImpl] =
    findByNamedQuery("VideoReferenceInfo.findByMissionID", Map("id" -> missionID))

  override def findByVideoReferenceUUID(uuid: UUID): Option[CachedVideoReferenceInfoImpl] =
    findByNamedQuery("VideoReferenceInfo.findByVideoReferenceUUID", Map("uuid" -> uuid)).headOption

  override def findAll(
      limit: Option[Int] = None,
      offset: Option[Int] = None
  ): Iterable[CachedVideoReferenceInfoImpl] =
    findByNamedQuery("VideoReferenceInfo.findAll", limit = limit, offset = offset)

  override def findAllVideoReferenceUUIDs(): Iterable[UUID] =
    fetchUsing("VideoReferenceInfo.findAllVideoReferenceUUIDs")
      .map(s => UUID.fromString(s))

  override def findAllMissionContacts(): Iterable[String] =
    fetchUsing("VideoReferenceInfo.findAllMissionContacts")

  override def findAllPlatformNames(): Iterable[String] =
    fetchUsing("VideoReferenceInfo.findAllVideoReferenceUUIDs")

  override def findAllMissionIDs(): Iterable[String] =
    fetchUsing("VideoReferenceInfo.findAllMissionIDs")

  private def fetchUsing(namedQuery: String): Iterable[String] =
    entityManager
      .createNamedQuery(namedQuery)
      .getResultList
      .asScala
      .filter(_ != null)
      .map(_.toString)

}
