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

package org.mbari.vars.annotation.dao

import java.util.UUID

import org.mbari.vars.annotation.model.CachedAncillaryDatum
import org.mbari.vars.annotation.model.simple.CachedAncillaryDatumBean

/**
 *
 *
 * @author Brian Schlining
 * @since 2016-06-17T16:08:00
 */
trait CachedAncillaryDatumDAO[T <: CachedAncillaryDatum] extends DAO[T] {

  def newPersistentObject(
    latitude: Double,
    longitude: Double,
    depthMeters: Float,
    altitudeMeters: Option[Float] = None,
    crs: Option[String] = None,
    salinity: Option[Float] = None,
    temperatureCelsius: Option[Float] = None,
    oxygenMlL: Option[Float] = None,
    pressureDbar: Option[Float] = None,
    lightTransmission: Option[Float] = None,
    x: Option[Double] = None,
    y: Option[Double] = None,
    z: Option[Double] = None,
    posePositionUnits: Option[String] = None,
    phi: Option[Double] = None,
    theta: Option[Double] = None,
    psi: Option[Double] = None
  ): CachedAncillaryDatum

  def findByObservationUUID(observationUuid: UUID): Option[CachedAncillaryDatum]

  def findByImagedMomentUUID(imagedMomentUuid: UUID): Option[CachedAncillaryDatum]

  def asPersistentObject(bean: CachedAncillaryDatum): CachedAncillaryDatum

}
