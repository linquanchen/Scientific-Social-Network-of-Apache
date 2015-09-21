
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
object dataSetList extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[List[DataSet],play.data.Form[DataSet],play.api.templates.HtmlFormat.Appendable] {

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
    def apply/*18.2*/(dataSets: List[DataSet], dataSetForm: play.data.Form[DataSet]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._

def /*22.2*/scripts/*22.9*/:play.api.templates.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*22.13*/("""
	<script src='"""),_display_(Seq[Any](/*23.16*/routes/*23.22*/.Assets.at("javascripts/edit_button.js"))),format.raw/*23.62*/("""'></script>
	<script type="text/javascript">
	$(document).ready(function()"""),format.raw/*25.30*/("""{"""),format.raw/*25.31*/("""
		//alert($("#url").text());
	"""),format.raw/*27.2*/("""}"""),format.raw/*27.3*/(""");
	</script>
""")))};
Seq[Any](format.raw/*18.65*/("""

"""),format.raw/*21.1*/("""
"""),format.raw/*29.2*/("""

"""),_display_(Seq[Any](/*31.2*/main("Dataset List", scripts)/*31.31*/ {_display_(Seq[Any](format.raw/*31.33*/("""
	
	"""),_display_(Seq[Any](/*33.3*/flash_message())),format.raw/*33.18*/("""   
	 
    <h1>Climate Datasets</h1>
    <h2>"""),_display_(Seq[Any](/*36.10*/dataSets/*36.18*/.size())),format.raw/*36.25*/(""" Datasets Found</h2>
    <div style="overflow-y:scroll">

		<table class="table table-striped table-bordered table-condensed tablesorter" id ="myTable">
		<thead>
	<tr >
		<th style = "vertical-align: top;" class="col-md-1 header">Id</th>
		<th style = "vertical-align: top;" class="col-md-2">Dataset Name</th>
		<th style = "vertical-align: top;" class="col-md-1">Agency</th>
		<th style = "vertical-align: top;" class="col-md-1">Instrument</th>
		<th style = "vertical-align: top;" class="col-md-2">Physical variable</th>
		<th style = "vertical-align: top;" class="col-md-1">Variable short name</th>
		<th style = "vertical-align: top;" class="col-md-1">Units</th>
		<th style = "vertical-align: top;" class="col-md-1">Grid Dimension</th>
		<!--  
		<th style = "vertical-align: top;" class="col-md-3">Source</th> 
		<th style = "vertical-align: top;" class="col-md-3">Status</th>
		<th style = "vertical-align: top;" class="col-md-4">Responsible Person</th>
		<th style = "vertical-align: top;" class="col-md-4">Data Source Name in Web Interface</th> 
		-->
		<th style = "vertical-align: top;" class="col-md-2">Variable Name in Web Interface</th>
		<th style = "vertical-align: top;" class="col-md-1">Data Source Input Parameter</th>
		<!-- <th style = "vertical-align: top;" class="col-md-3">Variable Name Input Parameter</th> -->
		<th style = "vertical-align: top;" class="col-md-1">Dataset Start Time</th>
		<th style = "vertical-align: top;"class="col-md-1">Dataset End Time</th>

	</tr>
	</thead>
	<tbody>
	"""),_display_(Seq[Any](/*65.3*/for(dataSet <- dataSets) yield /*65.27*/{_display_(Seq[Any](format.raw/*65.28*/("""
	<tr>
		<td><font size="2">"""),_display_(Seq[Any](/*67.23*/dataSet/*67.30*/.getId())),format.raw/*67.38*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*68.23*/dataSet/*68.30*/.getDataSetName())),format.raw/*68.47*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*69.23*/dataSet/*69.30*/.getAgencyId())),format.raw/*69.44*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*70.23*/dataSet/*70.30*/.getInstrument())),format.raw/*70.46*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*71.23*/dataSet/*71.30*/.getPhysicalVariable())),format.raw/*71.52*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*72.23*/dataSet/*72.30*/.getCMIP5VarName())),format.raw/*72.48*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*73.23*/dataSet/*73.30*/.getUnits())),format.raw/*73.41*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*74.23*/dataSet/*74.30*/.getGridDimension())),format.raw/*74.49*/("""</font></td>
		<!--
		<td><font size="2">"""),_display_(Seq[Any](/*76.23*/dataSet/*76.30*/.getSource())),format.raw/*76.42*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*77.23*/dataSet/*77.30*/.getStatus())),format.raw/*77.42*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*78.23*/dataSet/*78.30*/.getResponsiblePerson())),format.raw/*78.53*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*79.23*/dataSet/*79.30*/.getDataSourceName())),format.raw/*79.50*/("""</font></td>
		-->
		<td><font size="2">"""),_display_(Seq[Any](/*81.23*/dataSet/*81.30*/.getVariableName())),format.raw/*81.48*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*82.23*/dataSet/*82.30*/.getDataSourceInput())),format.raw/*82.51*/("""</font></td>
		<!--<td><font size="2">"""),_display_(Seq[Any](/*83.27*/dataSet/*83.34*/.getVariableNameInput())),format.raw/*83.57*/("""</font></td> -->
		<td><font size="2">"""),_display_(Seq[Any](/*84.23*/dataSet/*84.30*/.getStartTime())),format.raw/*84.45*/("""</font></td>
		<td><font size="2">"""),_display_(Seq[Any](/*85.23*/dataSet/*85.30*/.getEndTime())),format.raw/*85.43*/("""</font></td>
		
	</tr>
	
	""")))})),format.raw/*89.3*/("""
	</tbody>
    </table>
    
 
    
    </div>
""")))})),format.raw/*96.2*/("""
"""))}
    }
    
    def render(dataSets:List[DataSet],dataSetForm:play.data.Form[DataSet]): play.api.templates.HtmlFormat.Appendable = apply(dataSets,dataSetForm)
    
    def f:((List[DataSet],play.data.Form[DataSet]) => play.api.templates.HtmlFormat.Appendable) = (dataSets,dataSetForm) => apply(dataSets,dataSetForm)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/dataSetList.scala.html
                    HASH: 402412ba366036282131f1c841db4a247124d200
                    MATRIX: 3214->1205|3371->1289|3386->1296|3471->1300|3523->1316|3538->1322|3600->1362|3702->1436|3731->1437|3789->1468|3817->1469|3872->1268|3901->1287|3929->1484|3967->1487|4005->1516|4045->1518|4085->1523|4122->1538|4204->1584|4221->1592|4250->1599|5804->3118|5844->3142|5883->3143|5948->3172|5964->3179|5994->3187|6065->3222|6081->3229|6120->3246|6191->3281|6207->3288|6243->3302|6314->3337|6330->3344|6368->3360|6439->3395|6455->3402|6499->3424|6570->3459|6586->3466|6626->3484|6697->3519|6713->3526|6746->3537|6817->3572|6833->3579|6874->3598|6952->3640|6968->3647|7002->3659|7073->3694|7089->3701|7123->3713|7194->3748|7210->3755|7255->3778|7326->3813|7342->3820|7384->3840|7461->3881|7477->3888|7517->3906|7588->3941|7604->3948|7647->3969|7722->4008|7738->4015|7783->4038|7858->4077|7874->4084|7911->4099|7982->4134|7998->4141|8033->4154|8091->4181|8170->4229
                    LINES: 56->18|59->22|59->22|61->22|62->23|62->23|62->23|64->25|64->25|66->27|66->27|69->18|71->21|72->29|74->31|74->31|74->31|76->33|76->33|79->36|79->36|79->36|108->65|108->65|108->65|110->67|110->67|110->67|111->68|111->68|111->68|112->69|112->69|112->69|113->70|113->70|113->70|114->71|114->71|114->71|115->72|115->72|115->72|116->73|116->73|116->73|117->74|117->74|117->74|119->76|119->76|119->76|120->77|120->77|120->77|121->78|121->78|121->78|122->79|122->79|122->79|124->81|124->81|124->81|125->82|125->82|125->82|126->83|126->83|126->83|127->84|127->84|127->84|128->85|128->85|128->85|132->89|139->96
                    -- GENERATED --
                */
            