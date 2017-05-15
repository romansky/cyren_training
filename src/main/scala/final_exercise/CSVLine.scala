package final_exercise


case class CSVLine(
                    `managing_body`: Option[String],        // 0
                    `fund`: Option[String],                 // 1
                    `report_year`: Option[String],          // 2
                    `report_qurater`: Option[String],       // 3
//                  `instrument_type`: Option[String],      // 4
//                  `instrument_sub_type`: Option[String],  // 5
                    `instrument_symbol`: Option[String],    // 6
//                  `instrument_id`: Option[String],        // 7
//                  `underlying_asset`: Option[String],     // 8
//                  `industry`: Option[String],             // 9
//                  `rating`: Option[String],               // 10
//                  `rating_agency`: Option[String],        // 11
//                  `date_of_purchase`: Option[String],     // 12
//                  `average_of_duration`: Option[String],  // 13
                    `currency`: Option[String],             // 14
//                  `intrest_rate`: Option[String],         // 15
//                  `yield`: Option[String],                // 16
                    `par_value`: Option[String],            // 17
//                  `rate`: Option[String],                 // 18
//                  `market_cap`: Option[String],           // 19
                    `fair_value`: Option[String],           // 20
//                  `rate_of_ipo`: Option[String],          // 21
//                  `rate_of_fund`: Option[String],         // 22
//                  `date_of_revaluation`: Option[String],  // 23
                    `type_of_asset`: Option[String]//,        // 24
//                  `tmp_name`: Option[String],             // 25
//                  `issuer_number`: Option[String]         // 26
                  )


object CSVLine {

  val columns = List(
    "managing_body",
    "fund",
    "report_year",
    "report_qurater",
    "instrument_type",
    "instrument_sub_type",
    "instrument_symbol",
    "instrument_id",
    "underlying_asset",
    "industry",
    "rating",
    "rating_agency",
    "date_of_purchase",
    "average_of_duration",
    "currency",
    "intrest_rate",
    "yield",
    "par_value",
    "rate",
    "market_cap",
    "fair_value",
    "rate_of_ipo",
    "rate_of_fund",
    "date_of_revaluation",
    "type_of_asset",
    "tmp_name",
    "issuer_number"
  )


  def nameToColumn(name: String) = columns.indexOf(name)
}