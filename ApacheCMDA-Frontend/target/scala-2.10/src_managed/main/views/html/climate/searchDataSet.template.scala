
package views.html.climate

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more		   *
 * contributor license agreements.  See the NOTICE file distributed with	   *
 * this work for additional information regarding copyright ownership.         *
 * The ASF licenses this file to You under the Apache License, Version 2.0     *
 * (the "License"); you may not use this file except in compliance with        *
 * the License.  You may obtain a copy of the License at                       *
 *                                                                             *
 *    http://www.apache.org/licenses/LICENSE-2.0                               *
 *																			   *
 * Unless required by applicable law or agreed to in writing, software         *
 * distributed under the License is distributed on an "AS IS" BASIS,           *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 * See the License for the specific language governing permissions and         *
 * limitations under the License.											   *
 *******************************************************************************/
object searchDataSet extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[play.data.Form[DataSet],play.api.templates.HtmlFormat.Appendable] {

    /*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more		   *
 * contributor license agreements.  See the NOTICE file distributed with	   *
 * this work for additional information regarding copyright ownership.         *
 * The ASF licenses this file to You under the Apache License, Version 2.0     *
 * (the "License"); you may not use this file except in compliance with        *
 * the License.  You may obtain a copy of the License at                       *
 *                                                                             *
 *    http://www.apache.org/licenses/LICENSE-2.0                               *
 *																			   *
 * Unless required by applicable law or agreed to in writing, software         *
 * distributed under the License is distributed on an "AS IS" BASIS,           *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 * See the License for the specific language governing permissions and         *
 * limitations under the License.											   *
 *******************************************************************************/
    def apply/*18.2*/(dataSetForm: play.data.Form[DataSet]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._

def /*22.2*/scripts/*22.9*/:play.api.templates.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*22.13*/("""
	<script src='"""),_display_(Seq[Any](/*23.16*/routes/*23.22*/.Assets.at("javascripts/edit_button.js"))),format.raw/*23.62*/("""'></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
  	<script src="//code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
	<script type="text/javascript">
	$(function() """),format.raw/*28.15*/("""{"""),format.raw/*28.16*/("""
	    var availableDataSetNames = [
	                "AIRS Air Temperature",
					"AIRS Near-Surface Air Temperature",
					"AIRS Specific Humidity",
					"AMSRE Sea Surface Temperature",
					"ARGO Ocean Salinity",
					"ARGO Ocean Temperature",
					"AVISO Sea Surface Height",
					"CAM5 Air Temperature",
					"CAM5 Cloud Ice Water Content",
					"CAM5 Cloud Liquid Water Content",
					"CAM5 Leaf Area Index",
					"CAM5 Near-Surface Wind Speed",
					"CAM5 Precipitation Flux",
					"CAM5 Relative Humidity",
					"CAM5 Sea Surface Height",
					"CAM5 Sea Surface Temperature",
					"CAM5 Specific Humidity",
					"CAM5 Surface Downwelling Clear-Sky Shortwave Radiation",
					"CAM5 Surface Downwelling Longwave Radiation",
					"CAM5 Surface Downwelling Shortwave Radiation",
					"CAM5 Surface Temperature",
					"CAM5 Surface Upwelling Clear-Sky Shortwave Radiation",
					"CAM5 Surface Upwelling Longwave Radiation",
					"CAM5 Surface Upwelling Shortwave Radiation",
					"CAM5 TOA Incident Shortwave Radiation",
					"CAM5 TOA Outgoing Clear-Sky Longwave Radiation",
					"CAM5 TOA Outgoing Clear-Sky Shortwave Radiation",
					"CAM5 TOA Outgoing Longwave Radiation",
					"CAM5 TOA Outgoing Shortwave Radiation",
					"CAM5 Total Cloud Fraction",
					"CAM5 Vertical Wind Velocity",
					"CANAM4 Air Temperature",
					"CANAM4 Cloud Ice Water Content",
					"CANAM4 Cloud Liquid Water Content",
					"CANAM4 Eastward Near-Surface Wind",
					"CANAM4 Near-Surface Air Temperature",
					"CANAM4 Near-Surface Relative Humidity",
					"CANAM4 Near-Surface Wind Speed",
					"CANAM4 Northward Near-Surface Wind",
					"CANAM4 Precipitation Flux",
					"CANAM4 Relative Humidity",
					"CANAM4 Specific Humidity",
					"CANAM4 Surface Downwelling Clear-Sky Longwave Radiation",
					"CANAM4 Surface Downwelling Clear-Sky Shortwave Radiation",
					"CANAM4 Surface Downwelling Longwave Radiation",
					"CANAM4 Surface Downwelling Shortwave Radiation",
					"CANAM4 Surface Temperature",
					"CANAM4 Surface Upwelling Clear-Sky Shortwave Radiation",
					"CANAM4 Surface Upwelling Longwave Radiation",
					"CANAM4 Surface Upwelling Shortwave Radiation",
					"CANAM4 TOA Incident Shortwave Radiation",
					"CANAM4 TOA Outgoing Clear-Sky Longwave Radiation",
					"CANAM4 TOA Outgoing Clear-Sky Shortwave Radiation",
					"CANAM4 TOA Outgoing Longwave Radiation",
					"CANAM4 TOA Outgoing Shortwave Radiation",
					"CANAM4 Total Cloud Fraction",
					"CANAM4 Vertical Wind Velocity",
					"CERES Surface Downwelling Clear-Sky Longwave Radiation",
					"CERES Surface Downwelling Clear-Sky Shortwave Radiation",
					"CERES Surface Downwelling Longwave Radiation",
					"CERES Surface Downwelling Shortwave Radiation",
					"CERES Surface Upwelling Clear-Sky Shortwave Radiation",
					"CERES Surface Upwelling Longwave Radiation",
					"CERES Surface Upwelling Shortwave Radiation",
					"CERES TOA Incident Shortwave Radiation",
					"CERES TOA Outgoing Clear-Sky Longwave Radiation",
					"CERES TOA Outgoing Clear-Sky Shortwave Radiation",
					"CERES TOA Outgoing Longwave Radiation",
					"CERES TOA Outgoing Shortwave Radiation",
					"CM3 Air Temperature",
					"CM3 Cloud Ice Water Content",
					"CM3 Cloud Liquid Water Content",
					"CM3 Eastward Near-Surface Wind",
					"CM3 Near-Surface Wind Speed",
					"CM3 Northward Near-Surface Wind",
					"CM3 Precipitation Flux",
					"CM3 Relative Humidity",
					"CM3 Specific Humidity",
					"CM3 Surface Downwelling Clear-Sky Longwave Radiation",
					"CM3 Surface Downwelling Clear-Sky Shortwave Radiation",
					"CM3 Surface Downwelling Longwave Radiation",
					"CM3 Surface Downwelling Shortwave Radiation",
					"CM3 Surface Temperature",
					"CM3 Surface Upwelling Clear-Sky Shortwave Radiation",
					"CM3 Surface Upwelling Longwave Radiation",
					"CM3 Surface Upwelling Shortwave Radiation",
					"CM3 TOA Incident Shortwave Radiation",
					"CM3 TOA Outgoing Clear-Sky Longwave Radiation",
					"CM3 TOA Outgoing Clear-Sky Shortwave Radiation",
					"CM3 TOA Outgoing Longwave Radiation",
					"CM3 TOA Outgoing Shortwave Radiation",
					"CM3 Total Cloud Fraction",
					"CM3 Vertical Wind Velocity",
					"CM5A-LR Air Temperature",
					"CM5A-LR Cloud Ice Water Content",
					"CM5A-LR Cloud Liquid Water Content",
					"CM5A-LR Eastward Near-Surface Wind",
					"CM5A-LR Leaf Area Index",
					"CM5A-LR Near-Surface Wind Speed",
					"CM5A-LR Northward Near-Surface Wind",
					"CM5A-LR Precipitation Flux",
					"CM5A-LR Specific Humidity",
					"CM5A-LR Surface Downwelling Clear-Sky Longwave Radiation",
					"CM5A-LR Surface Downwelling Clear-Sky Shortwave Radiation",
					"CM5A-LR Surface Downwelling Longwave Radiation",
					"CM5A-LR Surface Downwelling Shortwave Radiation",
					"CM5A-LR Surface Temperature",
					"CM5A-LR Surface Upwelling Clear-Sky Shortwave Radiation",
					"CM5A-LR Surface Upwelling Longwave Radiation",
					"CM5A-LR Surface Upwelling Shortwave Radiation",
					"CM5A-LR TOA Incident Shortwave Radiation",
					"CM5A-LR TOA Outgoing Clear-Sky Longwave Radiation",
					"CM5A-LR TOA Outgoing Clear-Sky Shortwave Radiation",
					"CM5A-LR TOA Outgoing Longwave Radiation",
					"CM5A-LR TOA Outgoing Shortwave Radiation",
					"CM5A-LR Total Cloud Fraction",
					"E2-H Air Temperature",
					"E2-H Cloud Ice Water Content",
					"E2-H Cloud Liquid Water Content",
					"E2-H Eastward Near-Surface Wind",
					"E2-H Near-Surface Wind Speed",
					"E2-H Northward Near-Surface Wind",
					"E2-H Precipitation Flux",
					"E2-H Relative Humidity",
					"E2-H Sea Surface Temperature",
					"E2-H Specific Humidity",
					"E2-H Surface Downwelling Clear-Sky Longwave Radiation",
					"E2-H Surface Downwelling Clear-Sky Shortwave Radiation",
					"E2-H Surface Downwelling Longwave Radiation",
					"E2-H Surface Downwelling Shortwave Radiation",
					"E2-H Surface Temperature",
					"E2-H Surface Upwelling Clear-Sky Shortwave Radiation",
					"E2-H Surface Upwelling Longwave Radiation",
					"E2-H Surface Upwelling Shortwave Radiation",
					"E2-H TOA Incident Shortwave Radiation",
					"E2-H TOA Outgoing Clear-Sky Longwave Radiation",
					"E2-H TOA Outgoing Clear-Sky Shortwave Radiation",
					"E2-H TOA Outgoing Longwave Radiation",
					"E2-H TOA Outgoing Shortwave Radiation",
					"E2-H Total Cloud Fraction",
					"E2-H Vertical Wind Velocity",
					"E2-R Air Temperature",
					"E2-R Cloud Ice Water Content",
					"E2-R Cloud Liquid Water Content",
					"E2-R Eastward Near-Surface Wind",
					"E2-R Near-Surface Wind Speed",
					"E2-R Northward Near-Surface Wind",
					"E2-R Precipitation Flux",
					"E2-R Relative Humidity",
					"E2-R Sea Surface Height",
					"E2-R Sea Surface Temperature",
					"E2-R Specific Humidity",
					"E2-R Surface Downwelling Clear-Sky Longwave Radiation",
					"E2-R Surface Downwelling Clear-Sky Shortwave Radiation",
					"E2-R Surface Downwelling Shortwave Radiation",
					"E2-R Surface Temperature",
					"E2-R Surface Upwelling Clear-Sky Shortwave Radiation",
					"E2-R Surface Upwelling Longwave Radiation",
					"E2-R Surface Upwelling Shortwave Radiation",
					"E2-R TOA Incident Shortwave Radiation",
					"E2-R TOA Outgoing Clear-Sky Longwave Radiation",
					"E2-R TOA Outgoing Clear-Sky Shortwave Radiation",
					"E2-R TOA Outgoing Longwave Radiation",
					"E2-R TOA Outgoing Shortwave Radiation",
					"E2-R Total Cloud Fraction",
					"E2-R Vertical Wind Velocity",
					"ESM2G Air Temperature",
					"ESM2G Cloud Ice Water Content",
					"ESM2G Cloud Liquid Water Content",
					"ESM2G Eastward Near-Surface Wind",
					"ESM2G Leaf Area Index",
					"ESM2G Near-Surface Wind Speed",
					"ESM2G Northward Near-Surface Wind",
					"ESM2G Precipitation Flux",
					"ESM2G Relative Humidity",
					"ESM2G Sea Surface Height",
					"ESM2G Sea Surface Temperature",
					"ESM2G Specific Humidity",
					"ESM2G Surface Downwelling Clear-Sky Longwave Radiation",
					"ESM2G Surface Downwelling Longwave Radiation",
					"ESM2G Surface Temperature",
					"ESM2G Surface Upwelling Longwave Radiation",
					"ESM2G TOA Incident Shortwave Radiation",
					"ESM2G TOA Outgoing Clear-Sky Longwave Radiation",
					"ESM2G TOA Outgoing Clear-Sky Shortwave Radiation",
					"ESM2G TOA Outgoing Longwave Radiation",
					"ESM2G TOA Outgoing Shortwave Radiation",
					"ESM2G Total Cloud Fraction",
					"ESM2G Vertical Wind Velocity",
					"GPCP Precipitation Flux",
					"GRACE Equivalent Water Height Over Land",
					"GRACE Equivalent Water Height Over Ocean",
					"HadGEM2-A Air Temperature",
					"HadGEM2-A Cloud Ice Water Content",
					"HadGEM2-A Cloud Liquid Water Content",
					"HadGEM2-A Eastward Near-Surface Wind",
					"HadGEM2-A Near-Surface Relative Humidity",
					"HadGEM2-A Near-Surface Wind Speed",
					"HadGEM2-A Northward Near-Surface Wind",
					"HadGEM2-A Precipitation Flux",
					"HadGEM2-A Relative Humidity",
					"HadGEM2-A Specific Humidity",
					"HadGEM2-A Surface Downwelling Clear-Sky Longwave Radiation",
					"HadGEM2-A Surface Downwelling Clear-Sky Shortwave Radiation",
					"HadGEM2-A Surface Downwelling Longwave Radiation",
					"HadGEM2-A Surface Downwelling Shortwave Radiation",
					"HadGEM2-A Surface Temperature",
					"HadGEM2-A Surface Upwelling Clear-Sky Shortwave Radiation",
					"HadGEM2-A Surface Upwelling Longwave Radiation",
					"HadGEM2-A Surface Upwelling Shortwave Radiation",
					"HadGEM2-A TOA Incident Shortwave Radiation",
					"HadGEM2-A TOA Outgoing Clear-Sky Longwave Radiation",
					"HadGEM2-A TOA Outgoing Clear-Sky Shortwave Radiation",
					"HadGEM2-A TOA Outgoing Longwave Radiation",
					"HadGEM2-A TOA Outgoing Shortwave Radiation",
					"HadGEM2-A Total Cloud Fraction",
					"HadGEM2-A Vertical Wind Velocity",
					"HadGEM2-ES Air Temperature",
					"HadGEM2-ES Cloud Ice Water Content",
					"HadGEM2-ES Cloud Liquid Water Content",
					"HadGEM2-ES Eastward Near-Surface Wind",
					"HadGEM2-ES Leaf Area Index",
					"HadGEM2-ES Near-Surface Wind Speed",
					"HadGEM2-ES Northward Near-Surface Wind",
					"HadGEM2-ES Precipitation Flux",
					"HadGEM2-ES Relative Humidity",
					"HadGEM2-ES Sea Surface Height",
					"HadGEM2-ES Sea Surface Temperature",
					"HadGEM2-ES Specific Humidity",
					"HadGEM2-ES Surface Downwelling Clear-Sky Longwave Radiation",
					"HadGEM2-ES Surface Downwelling Clear-Sky Shortwave Radiation",
					"HadGEM2-ES Surface Downwelling Longwave Radiation",
					"HadGEM2-ES Surface Downwelling Shortwave Radiation",
					"HadGEM2-ES Surface Temperature",
					"HadGEM2-ES Surface Upwelling Clear-Sky Shortwave Radiation",
					"HadGEM2-ES Surface Upwelling Longwave Radiation",
					"HadGEM2-ES Surface Upwelling Shortwave Radiation",
					"HadGEM2-ES TOA Incident Shortwave Radiation",
					"HadGEM2-ES TOA Outgoing Clear-Sky Longwave Radiation",
					"HadGEM2-ES TOA Outgoing Clear-Sky Shortwave Radiation",
					"HadGEM2-ES TOA Outgoing Longwave Radiation",
					"HadGEM2-ES TOA Outgoing Shortwave Radiation",
					"HadGEM2-ES Total Cloud Fraction",
					"HadGEM2-ES Vertical Wind Velocity",
					"MIROC5 Air Temperature", "MIROC5 Cloud Ice Water Content",
					"MIROC5 Cloud Liquid Water Content",
					"MIROC5 Eastward Near-Surface Wind",
					"MIROC5 Leaf Area Index", "MIROC5 Near-Surface Wind Speed",
					"MIROC5 Northward Near-Surface Wind",
					"MIROC5 Precipitation Flux", "MIROC5 Relative Humidity",
					"MIROC5 Specific Humidity",
					"MIROC5 Surface Downwelling Clear-Sky Longwave Radiation",
					"MIROC5 Surface Downwelling Clear-Sky Shortwave Radiation",
					"MIROC5 Surface Downwelling Longwave Radiation",
					"MIROC5 Surface Downwelling Shortwave Radiation",
					"MIROC5 Surface Temperature",
					"MIROC5 Surface Upwelling Clear-Sky Shortwave Radiation",
					"MIROC5 Surface Upwelling Longwave Radiation",
					"MIROC5 Surface Upwelling Shortwave Radiation",
					"MIROC5 TOA Incident Shortwave Radiation",
					"MIROC5 TOA Outgoing Clear-Sky Longwave Radiation",
					"MIROC5 TOA Outgoing Clear-Sky Shortwave Radiation",
					"MIROC5 TOA Outgoing Longwave Radiation",
					"MIROC5 TOA Outgoing Shortwave Radiation",
					"MIROC5 Total Cloud Fraction",
					"MIROC5 Vertical Wind Velocity", "MK3.6 Air Temperature",
					"MK3.6 Cloud Ice Water Content",
					"MK3.6 Cloud Liquid Water Content",
					"MK3.6 Eastward Near-Surface Wind",
					"MK3.6 Near-Surface Wind Speed",
					"MK3.6 Northward Near-Surface Wind",
					"MK3.6 Precipitation Flux", "MK3.6 Relative Humidity",
					"MK3.6 Specific Humidity",
					"MK3.6 Surface Downwelling Clear-Sky Longwave Radiation",
					"MK3.6 Surface Downwelling Clear-Sky Shortwave Radiation",
					"MK3.6 Surface Downwelling Longwave Radiation",
					"MK3.6 Surface Downwelling Shortwave Radiation",
					"MK3.6 Surface Temperature",
					"MK3.6 Surface Upwelling Clear-Sky Shortwave Radiation",
					"MK3.6 Surface Upwelling Longwave Radiation",
					"MK3.6 Surface Upwelling Shortwave Radiation",
					"MK3.6 TOA Incident Shortwave Radiation",
					"MK3.6 TOA Outgoing Clear-Sky Longwave Radiation",
					"MK3.6 TOA Outgoing Clear-Sky Shortwave Radiation",
					"MK3.6 TOA Outgoing Longwave Radiation",
					"MK3.6 TOA Outgoing Shortwave Radiation",
					"MK3.6 Total Cloud Fraction",
					"MK3.6 Vertical Wind Velocity", "MLS Air Temperature",
					"MLS Specific Humidity", "MODIS Leaf Area Index",
					"MODIS Total Cloud Fraction",
					"NODC Ocean Heat Content Anomaly within 2000 m Depth",
					"NODC Ocean Heat Content Anomaly within 700 m Depth",
					"NORESM Air Temperature", "NORESM Cloud Ice Water Content",
					"NORESM Cloud Liquid Water Content",
					"NORESM Eastward Near-Surface Wind",
					"NORESM Leaf Area Index",
					"NORESM Northward Near-Surface Wind",
					"NORESM Precipitation Flux", "NORESM Relative Humidity",
					"NORESM Sea Surface Height",
					"NORESM Sea Surface Temperature",
					"NORESM Specific Humidity",
					"NORESM Surface Downwelling Clear-Sky Longwave Radiation",
					"NORESM Surface Downwelling Clear-Sky Shortwave Radiation",
					"NORESM Surface Downwelling Longwave Radiation",
					"NORESM Surface Downwelling Shortwave Radiation",
					"NORESM Surface Temperature",
					"NORESM Surface Upwelling Clear-Sky Shortwave Radiation",
					"NORESM Surface Upwelling Longwave Radiation",
					"NORESM Surface Upwelling Shortwave Radiation",
					"NORESM TOA Incident Shortwave Radiation",
					"NORESM TOA Outgoing Clear-Sky Longwave Radiation",
					"NORESM TOA Outgoing Clear-Sky Shortwave Radiation",
					"NORESM TOA Outgoing Longwave Radiation",
					"NORESM TOA Outgoing Shortwave Radiation",
					"NORESM Total Cloud Fraction",
					"NORESM Vertical Wind Velocity",
					"QuikSCAT Eastward Near-Surface Wind",
					"QuikSCAT Near-Surface Wind Speed",
					"QuikSCAT Northward Near-Surface Wind",
					"TRMM Precipitation Flux",
					"interim Eastward Near-Surface Wind",
					"interim Near-Surface Wind Speed",
					"interim Northward Near-Surface Wind",
					"interim Relative Humidity",
					"interim Sea Surface Temperature",
					"interim Total Cloud Fraction",
					"interim Vertical Wind Velocity" ];
			
			var availableAgency = [ "ARGO", "CCCMA", "CSIRO", "ECMWF",
					"Flux,kg", "GFDL", "GFDL", "GISS", "IPSL", "MIROC", "NASA",
					"NASA", "NCAR", "NCC", "NOAA", "UKMO", "UKMO" ];

			var availableInstruments = [ "AIRS", "AMSRE", "ARGO", "AVISO",
					"CAM5", "CANAM4", "CERES", "CM3", "CM5A-LR", "E2-H",
					"E2-R", "ESM2G", "GPCP", "GRACE", "HadGEM2-A",
					"HadGEM2-ES", "MIROC5", "MK3.6", "MLS", "MODIS", "NODC",
					"NORESM", "QuikSCAT", "TRMM", "interim" ];

		var availablePhysicalVariable = [ "Air Temperature",
					"Cloud Ice Water Content", "Cloud Liquid Water Content",
					"Eastward Near-Surface Wind",
					"Equivalent Water Height Over Land",
					"Equivalent Water Height Over Ocean", "Leaf Area Index",
					"Near-Surface Air Temperature",
					"Near-Surface Relative Humidity",
					"Near-Surface Wind Speed", "Northward Near-Surface Wind",
					"Ocean Heat Content Anomaly within 2000 m Depth",
					"Ocean Heat Content Anomaly within 700 m Depth",
					"Ocean Salinity", "Ocean Temperature",
					"Precipitation Flux", "Precipitation", "Relative Humidity",
					"Sea Surface Height", "Sea Surface Temperature",
					"Specific Humidity",
					"Surface Downwelling Clear-Sky Longwave Radiation",
					"Surface Downwelling Clear-Sky Shortwave Radiation",
					"Surface Downwelling Longwave Radiation",
					"Surface Downwelling Shortwave Radiation",
					"Surface Temperature",
					"Surface Upwelling Clear-Sky Shortwave Radiation",
					"Surface Upwelling Longwave Radiation",
					"Surface Upwelling Shortwave Radiation",
					"TOA Incident Shortwave Radiation",
					"TOA Outgoing Clear-Sky Longwave Radiation",
					"TOA Outgoing Clear-Sky Shortwave Radiation",
					"TOA Outgoing Longwave Radiation",
					"TOA Outgoing Shortwave Radiation", "Total Cloud Fraction",
					"Vertical Wind Velocity" ];
			var availableGridDimension = [ "2D", "3D" ];
			$("#dataSetName").autocomplete("""),format.raw/*397.35*/("""{"""),format.raw/*397.36*/("""
				source : availableDataSetNames
			"""),format.raw/*399.4*/("""}"""),format.raw/*399.5*/(""");
			$("#agency").autocomplete("""),format.raw/*400.30*/("""{"""),format.raw/*400.31*/("""
				source : availableAgency
			"""),format.raw/*402.4*/("""}"""),format.raw/*402.5*/(""");
			$("#instrument").autocomplete("""),format.raw/*403.34*/("""{"""),format.raw/*403.35*/("""
				source : availableInstruments
			"""),format.raw/*405.4*/("""}"""),format.raw/*405.5*/(""");
			$("#physicalVariable").autocomplete("""),format.raw/*406.40*/("""{"""),format.raw/*406.41*/("""
				source : availablePhysicalVariable
			"""),format.raw/*408.4*/("""}"""),format.raw/*408.5*/(""");
			$("#gridDimension").autocomplete("""),format.raw/*409.37*/("""{"""),format.raw/*409.38*/("""
				source : availableGridDimension
			"""),format.raw/*411.4*/("""}"""),format.raw/*411.5*/(""");
		"""),format.raw/*412.3*/("""}"""),format.raw/*412.4*/(""");

		$(document)
				.ready(
						function() """),format.raw/*416.18*/("""{"""),format.raw/*416.19*/("""
							$("#preview")
									.click(
											function() """),format.raw/*419.23*/("""{"""),format.raw/*419.24*/("""
												var target = document
														.getElementById("show");
												if (target.style.display == "none") """),format.raw/*422.49*/("""{"""),format.raw/*422.50*/("""
													target.style.display = "block";
													$("#preview").text("Hide");
													var dataSetName = $(
															"#dataSetName")
															.val();
													var agency = $("#agency")
															.val();
													var instrument = $(
															"#instrument")
															.val();
													var physicalVariable = $(
															"#physicalVariable")
															.val();
													var gridDimension = $(
															"#gridDimension")
															.val();
													var dataSetStartTime = $(
															"#dataSetStartTime")
															.val();
													var dataSetEndTime = $(
															"#dataSetEndTime")
															.val();

													if (dataSetName != "") """),format.raw/*446.37*/("""{"""),format.raw/*446.38*/("""
														$("#content")
																.append(
																		" Dataset Name = "
																				+ dataSetName);
													"""),format.raw/*451.14*/("""}"""),format.raw/*451.15*/("""
													if (agency != ""
															&& agency != null) """),format.raw/*453.35*/("""{"""),format.raw/*453.36*/("""
														$("#content")
																.append(
																		" Agency = "
																				+ agency);
													"""),format.raw/*458.14*/("""}"""),format.raw/*458.15*/("""
													if (instrument != ""
															&& instrument != null) """),format.raw/*460.39*/("""{"""),format.raw/*460.40*/("""
														$("#content")
																.append(
																		" Instrument = "
																				+ instrument);
													"""),format.raw/*465.14*/("""}"""),format.raw/*465.15*/("""
													if (physicalVariable != ""
															&& physicalVariable != null) """),format.raw/*467.45*/("""{"""),format.raw/*467.46*/("""
														$("#content")
																.append(
																		" Physical Variable = "
																				+ physicalVariable);
													"""),format.raw/*472.14*/("""}"""),format.raw/*472.15*/("""
													if (gridDimension != ""
															&& gridDimension != null) """),format.raw/*474.42*/("""{"""),format.raw/*474.43*/("""
														$("#content")
																.append(
																		" Grid Dimension = "
																				+ gridDimension);
													"""),format.raw/*479.14*/("""}"""),format.raw/*479.15*/("""
													if (dataSetStartTime != ""
															&& dataSetStartTime != null) """),format.raw/*481.45*/("""{"""),format.raw/*481.46*/("""
														$("#content")
																.append(
																		" Dataset Start Time = "
																				+ dataSetStartTime);
													"""),format.raw/*486.14*/("""}"""),format.raw/*486.15*/("""
													if (dataSetEndTime != ""
															&& dataSetEndTime != null) """),format.raw/*488.43*/("""{"""),format.raw/*488.44*/("""
														$("#content")
																.append(
																		" Dataset End Time = "
																				+ dataSetEndTime);
													"""),format.raw/*493.14*/("""}"""),format.raw/*493.15*/("""

												"""),format.raw/*495.13*/("""}"""),format.raw/*495.14*/(""" else """),format.raw/*495.20*/("""{"""),format.raw/*495.21*/("""
													$("#content").val('');
													target.style.display = "none";
													$("#preview").text(
															"Preview");
												"""),format.raw/*500.13*/("""}"""),format.raw/*500.14*/("""
											"""),format.raw/*501.12*/("""}"""),format.raw/*501.13*/(""");
						"""),format.raw/*502.7*/("""}"""),format.raw/*502.8*/(""");
	</script>
""")))};
Seq[Any](format.raw/*18.40*/("""

"""),format.raw/*21.1*/("""
"""),format.raw/*504.2*/("""

"""),_display_(Seq[Any](/*506.2*/main("Search Dataset", scripts)/*506.33*/{_display_(Seq[Any](format.raw/*506.34*/("""
	
	"""),_display_(Seq[Any](/*508.3*/flash_message())),format.raw/*508.18*/("""   
	 
    <h1 style="margin-left:490px">Search Dataset</h1>
    """),_display_(Seq[Any](/*511.6*/helper/*511.12*/.form(routes.DatasetController.getSearchResult())/*511.61*/ {_display_(Seq[Any](format.raw/*511.63*/("""
    	<div class="ui-widget col-sm-offset-3 col-sm-7">
    	<div class = "form-group">
    		"""),_display_(Seq[Any](/*514.8*/inputText(dataSetForm("Dataset Name"), 'class -> "form-control", 'id -> "dataSetName", '_label -> Messages("Dataset Name"), 'placeholder -> "ARGO Ocean Temperature", 'size->70))),format.raw/*514.184*/(""" 
    	</div>
    	<div class = "form-group">
    		"""),_display_(Seq[Any](/*517.8*/inputText(dataSetForm("Agency"), 'class -> "form-control", 'id -> "agency", '_label -> Messages("Agency"), 'placeholder -> "NOAA", 'size->70))),format.raw/*517.149*/(""" 
    	</div>
    	<div class = "form-group">
    		"""),_display_(Seq[Any](/*520.8*/inputText(dataSetForm("Instrument"), 'class -> "form-control", 'id -> "instrument", '_label -> Messages("Instrument"), 'placeholder -> "ARGO", 'size->70))),format.raw/*520.161*/(""" 
       	</div>
       	<div class = "form-group">
       		"""),_display_(Seq[Any](/*523.11*/inputText(dataSetForm("Physical Variable"), 'class -> "form-control", 'id -> "physicalVariable", '_label -> Messages("Physical Variable"), 'placeholder -> "Ocean temperature", 'size->70))),format.raw/*523.197*/("""
	    </div>
	    <div class = "form-group">
	    	"""),_display_(Seq[Any](/*526.8*/inputText(dataSetForm("Grid Dimension"), 'class -> "form-control", 'id -> "gridDimension", '_label -> Messages("Grids Dimension"), 'placeholder -> "3D", 'size->70))),format.raw/*526.171*/("""
	    	
	    </div>
	    <div class = "form-group">
	    	"""),_display_(Seq[Any](/*530.8*/inputText(dataSetForm("Dataset Start Time"), 'class -> "form-control", 'id -> "dataSetStartTime", '_label -> Messages("Dataset Start Time"), 'placeholder -> "YYYYMM", 'size->70))),format.raw/*530.185*/("""
	    	"""),_display_(Seq[Any](/*531.8*/inputText(dataSetForm("Dataset End Time"), 'class -> "form-control", 'id -> "dataSetEndTime", '_label -> Messages("Dataset End Time"), 'placeholder -> "YYYYMM", 'size->70))),format.raw/*531.179*/("""
	    	
	    	<div id="show" style="display: none;">
	    		<textarea style="width: 640px" rows="4" id="content"></textarea>
	    	</div>
	    </div>
	    <div align="center">
	    	<button id="preview" type="button" class="btn btn-info"> Preview</button>
	    	<input class="btn" type="submit" value="Search">
	    </div>
	    </div>
	    
	    """)))})),format.raw/*543.7*/("""
	 """)))})))}
    }
    
    def render(dataSetForm:play.data.Form[DataSet]): play.api.templates.HtmlFormat.Appendable = apply(dataSetForm)
    
    def f:((play.data.Form[DataSet]) => play.api.templates.HtmlFormat.Appendable) = (dataSetForm) => apply(dataSetForm)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/searchDataSet.scala.html
                    HASH: c2dd8ec623e9aa8a68a5cd2ec3161c619c2d5e43
                    MATRIX: 3202->1205|3334->1264|3349->1271|3434->1275|3486->1291|3501->1297|3563->1337|3870->1616|3899->1617|20964->18653|20994->18654|21061->18693|21090->18694|21151->18726|21181->18727|21242->18760|21271->18761|21336->18797|21366->18798|21432->18836|21461->18837|21532->18879|21562->18880|21633->18923|21662->18924|21730->18963|21760->18964|21828->19004|21857->19005|21890->19010|21919->19011|21995->19058|22025->19059|22115->19120|22145->19121|22296->19243|22326->19244|23097->19986|23127->19987|23296->20127|23326->20128|23420->20193|23450->20194|23608->20323|23638->20324|23740->20397|23770->20398|23936->20535|23966->20536|24080->20621|24110->20622|24289->20772|24319->20773|24427->20852|24457->20853|24630->20997|24660->20998|24774->21083|24804->21084|24984->21235|25014->21236|25124->21317|25154->21318|25330->21465|25360->21466|25403->21480|25433->21481|25468->21487|25498->21488|25680->21641|25710->21642|25751->21654|25781->21655|25818->21664|25847->21665|25902->1243|25931->1262|25960->21680|25999->21683|26040->21714|26080->21715|26121->21720|26159->21735|26261->21801|26277->21807|26336->21856|26377->21858|26507->21952|26707->22128|26796->22181|26961->22322|27050->22375|27227->22528|27326->22590|27536->22776|27624->22828|27811->22991|27906->23050|28107->23227|28151->23235|28346->23406|28725->23753
                    LINES: 56->18|59->22|59->22|61->22|62->23|62->23|62->23|67->28|67->28|436->397|436->397|438->399|438->399|439->400|439->400|441->402|441->402|442->403|442->403|444->405|444->405|445->406|445->406|447->408|447->408|448->409|448->409|450->411|450->411|451->412|451->412|455->416|455->416|458->419|458->419|461->422|461->422|485->446|485->446|490->451|490->451|492->453|492->453|497->458|497->458|499->460|499->460|504->465|504->465|506->467|506->467|511->472|511->472|513->474|513->474|518->479|518->479|520->481|520->481|525->486|525->486|527->488|527->488|532->493|532->493|534->495|534->495|534->495|534->495|539->500|539->500|540->501|540->501|541->502|541->502|544->18|546->21|547->504|549->506|549->506|549->506|551->508|551->508|554->511|554->511|554->511|554->511|557->514|557->514|560->517|560->517|563->520|563->520|566->523|566->523|569->526|569->526|573->530|573->530|574->531|574->531|586->543
                    -- GENERATED --
                */
            