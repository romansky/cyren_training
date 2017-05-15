package final_exercise

import java.io.{File, InputStreamReader}

import com.github.tototoshi.csv.CSVReader
import play.api.libs.json.Json


object FinalExercise {

  val file10K = "10k.csv"
  val fileAll = "all.csv"

  type ByCurrency = Map[String, Long]
  type Valued = List[CSVLine]

  def main(args: Array[String]): Unit = {
    val valued = getValuedData(fileAll)
    val holdingValued = filterSells(valued)

    println("total valued csv lines: " + holdingValued.length)

    exploring(holdingValued)

    val foundTopDelekInvestor = topDelekInvestor(holdingValued)
    println("top delek investor:" + foundTopDelekInvestor)

    val report = generateReport(holdingValued)
    println("report json:\n" + Json.toJson(report))
  }

  def generateReport(valued: Valued): Report =
    Report(
      topDelekInvestor(valued)._1,
      q1HoldingsByCurrency(valued),
      holdingsPerQ2016(valued)
    )

  def getValuedData(file: String): Valued = {
    val reader = CSVReader.open(new File(file))
    val all = reader.all()

    def toOption(s: String) =
      s match {
        case _ if s.isEmpty ⇒ None
        case _ ⇒ Some(s)
      }

    // see sample of 5 lines from input file
    // println("top 5 lines from file")
//    println(all.take(5))
    // print the line with indices
//    println(all.take(100).drop(95).head.zipWithIndex)
    // print the line with named indices
//    println(all.take(100).drop(95).head.zip(CSVLine.columns))

    val allCsvLine = all.map(line ⇒ {
      CSVLine(
        `managing_body` = toOption(line(CSVLine.nameToColumn("managing_body"))),
        `fund` = toOption(line(CSVLine.nameToColumn("fund"))),
        `report_year` = toOption(line(CSVLine.nameToColumn("report_year"))),
        `report_qurater` = toOption(line(CSVLine.nameToColumn("report_qurater"))),
        `instrument_symbol` = toOption(line(CSVLine.nameToColumn("instrument_symbol"))),
        `currency` = toOption(line(CSVLine.nameToColumn("currency"))),
        `par_value` = toOption(line(CSVLine.nameToColumn("par_value"))),
        `fair_value` = toOption(line(CSVLine.nameToColumn("fair_value"))),
        `type_of_asset` = toOption(line(CSVLine.nameToColumn("type_of_asset")))
      )
    })


    // find all properties
    allCsvLine.filter {
      csvLine ⇒ csvLine.`par_value`.isDefined || csvLine.`fair_value`.isDefined
    }

  }

  def exploring(valued: Valued): Unit = {

    val currencies = valued.filter(_.`currency`.isDefined).flatMap(_.`currency`).distinct // ~2 passes
    println("found currencies: " + currencies.mkString(","))

    // more efficient
    val currencies2 = valued.foldLeft(List.empty[String]) {
      case (out, csvLine) if csvLine.`currency`.isDefined ⇒
        csvLine.`currency`.get :: out
      case (out, _) ⇒ out
    }.distinct

    println("found currencies v2:  " + currencies2.mkString(","))

    // more idiomatic (& less error prone)
    val currencies3 = valued.foldLeft(List.empty[String]) {
      case (out, csvLine) ⇒
        csvLine.`currency`.toList ::: out
    }.distinct

    println("found currencies v3:  " + currencies3.mkString(","))

    // also ok
    val currencies4 = valued.foldLeft(List.empty[String]) {
      case (out, csvLine) ⇒
        csvLine.`currency`.map(_ :: out).getOrElse(out)
    }.distinct

    println("found currencies v4:  " + currencies4.mkString(","))

    // and finally without distinct
    val currencies5 = valued.foldLeft(Set.empty[String]) {
      case (out, csvLine) ⇒
        csvLine.`currency`.map(out + _).getOrElse(out)
    }

    println("found currencies v5:  " + currencies5.mkString(","))



    // amitim q1 2016 holdings
    val amitim2016Q1 =
      valued
        .filter(_.`managing_body`.contains("amitim"))
        .filter(_.`report_year`.contains("2016"))
        .filter(_.`report_qurater`.contains("1"))

    val amitim2016Q1TotalNis = nisValue(groupByCurrency(amitim2016Q1))

    println("amitim 2016/Q1 holdings " + amitim2016Q1TotalNis)

    val nisPerBody2016Q1 = valued
      .filter(_.`report_year`.contains("2016"))
      .filter(_.`report_qurater`.contains("1"))
      .groupBy(_.`managing_body`).filter(_._1.isDefined).map {
        case (Some(fund), csvLines) ⇒
          fund → nisValue(groupByCurrency(csvLines))
        case (None, csvLines) ⇒
          throw new RuntimeException("unexpected entry with values:" + csvLines)
    }

    println("total holdings by body 2016/Q1 in nis:" + nisPerBody2016Q1)


  }

  def kValue(csVLine: CSVLine) : Double = csVLine.`par_value`.getOrElse(csVLine.`fair_value`.get).toDouble

  def filterSells(valued: Valued): Valued = valued.filter(v⇒ kValue(v) > 0)


  def groupByCurrency(csvLines: List[CSVLine]): ByCurrency =
    csvLines.groupBy(_.`currency`).map {
      case (Some(currency), groupedLines) ⇒
        currency -> groupedLines.map(kValue).sum.toLong
      case (None, groupedLines) ⇒
        "unknown" -> groupedLines.map(kValue).sum.toLong
    }

  def nisValue(byCurrency: ByCurrency): Long =
    byCurrency.map {
      case ("unknown", value) ⇒
        value
      case (currency, value) ⇒
        (value * Currencies.values(currency)).toLong
    }.sum


  def topDelekInvestor(valued: Valued): (String, Long) = {
    valued
      .filter(_.`report_year`.contains("2016"))
      .filter(_.`report_qurater`.contains("1"))
      .filter(_.`instrument_symbol`.exists(_.contains("דלק")))
      .groupBy(_.`managing_body`)
      .toList
      .map {
        case (body, csvLines) ⇒ body.get → nisValue(groupByCurrency(csvLines))
      }
      .sortBy(_._2)
      .reverse
      .head
  }

  def q1HoldingsByCurrency(valued: Valued): ByCurrency = {
    val y2016q1 = valued
      .filter(_.`report_year`.contains("2016"))
      .filter(_.`report_qurater`.contains("1"))

    groupByCurrency(y2016q1)
  }

  def holdingsPerQ2016(valued: Valued): Map[String, Long] =
    valued
      .filter(_.`report_year`.contains("2016"))
      .groupBy(_.`report_qurater`)
      .map {
        case (q, csvLines) ⇒ q.get -> nisValue(groupByCurrency(csvLines))
      }


}