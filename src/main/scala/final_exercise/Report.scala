package final_exercise

import play.api.libs.json.{Format, Json, OFormat}

case class Report(
    topDelekInvestor: String,
    q1HoldingsByCurrency: Map[String, Long],
    holdingsPerQ2016: Map[String, Long]

)

object Report {

  implicit val fmt: OFormat[Report] = Json.format[Report]

}
