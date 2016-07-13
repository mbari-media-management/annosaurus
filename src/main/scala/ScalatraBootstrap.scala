import javax.servlet.ServletContext

import org.mbari.vars.annotation.api.{ AnnotationSwagger, AnnotationV1Api, AssociationV1Api, ImagedMomentV1Api }
import org.mbari.vars.annotation.controllers._
import org.mbari.vars.annotation.dao.jpa.DevelopmentDAOFactory
import org.scalatra.LifeCycle
import org.scalatra.swagger.{ ApiInfo, Swagger }
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

/**
 *
 *
 * @author Brian Schlining
 * @since 2016-05-20T14:41:00
 */
class ScalatraBootstrap extends LifeCycle {

  private[this] val log = LoggerFactory.getLogger(getClass)

  val apiInfo = ApiInfo(
    """video-annotation-service""",
    """Video Annotations - Server""",
    """http://localhost:8080/api-docs""",
    """brian@mbari.org""",
    """MIT""",
    """http://opensource.org/licenses/MIT"""
  )

  implicit val swagger = new Swagger("1.2", "1.0.0", apiInfo)

  override def init(context: ServletContext): Unit = {

    println("STARTING UP NOW")

    implicit val executionContext = ExecutionContext.global

    val daoFactory: BasicDAOFactory = DevelopmentDAOFactory.asInstanceOf[BasicDAOFactory]
    val annotationController = new AnnotationController(daoFactory)
    val associationController = new AssociationController(daoFactory)
    val imagedMomentController = new ImagedMomentController(daoFactory)
    val imageReferenceController = new ImageReferenceController(daoFactory)
    val observationController = new ObservationController(daoFactory)

    val annotationV1Api = new AnnotationV1Api(annotationController)
    val associationV1Api = new AssociationV1Api(associationController)
    val imagedMomentV1Api = new ImagedMomentV1Api(imagedMomentController)

    context.mount(annotationV1Api, "/v1/annotations")
    context.mount(associationV1Api, "/v1/associations")
    context.mount(imagedMomentV1Api, "/v1/imagedmoments")
    context.mount(new AnnotationSwagger, "/api-docs")

  }

}
