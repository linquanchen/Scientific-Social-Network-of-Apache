
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
object climateServices extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[List[metadata.ClimateService],play.data.Form[metadata.ClimateService],play.api.templates.HtmlFormat.Appendable] {

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
    def apply/*18.2*/(climateServices: List[metadata.ClimateService], climateServiceForm: play.data.Form[metadata.ClimateService]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._

def /*22.2*/scripts/*22.9*/:play.api.templates.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*22.13*/("""
	<script src='"""),_display_(Seq[Any](/*23.16*/routes/*23.22*/.Assets.at("javascripts/edit_button.js"))),format.raw/*23.62*/("""'></script>
	<script type="text/javascript">
	$(document).ready(function()"""),format.raw/*25.30*/("""{"""),format.raw/*25.31*/("""

	"""),format.raw/*27.2*/("""}"""),format.raw/*27.3*/(""");
	</script>
""")))};
Seq[Any](format.raw/*18.111*/("""

"""),format.raw/*21.1*/("""
"""),format.raw/*29.2*/("""

"""),_display_(Seq[Any](/*31.2*/main("Climate Services", scripts)/*31.35*/ {_display_(Seq[Any](format.raw/*31.37*/("""
	
	"""),_display_(Seq[Any](/*33.3*/flash_message())),format.raw/*33.18*/("""   
	 
    <h1>"""),_display_(Seq[Any](/*35.10*/climateServices/*35.25*/.size())),format.raw/*35.32*/(""" Climate Services</h1>
		<table class="table table-striped table-bordered table-condensed ex2 tablesorter" id = "csTable">
		<thead>
	<tr>
		<th class="col-md-2">Climate Service Name</td>
		<th class="col-md-4">Purpose</td>
		<th class="col-md-2">Url</td>
		
	</tr>
	</thead>
	"""),_display_(Seq[Any](/*45.3*/for(climateService <- climateServices) yield /*45.41*/{_display_(Seq[Any](format.raw/*45.42*/("""
	<tr>
		<td><a href = """"),_display_(Seq[Any](/*47.19*/{routes.ClimateServiceController.oneService(climateService.getUrl())})),format.raw/*47.88*/(""""><font size="3">"""),_display_(Seq[Any](/*47.106*/climateService/*47.120*/.getClimateServiceName())),format.raw/*47.144*/("""</a></font></td>

		<td><span class=""""),_display_(Seq[Any](/*49.21*/climateService/*49.35*/.getClimateServiceName())),format.raw/*49.59*/(""" editable"  data-name='purpose'><font size="3">
				"""),_display_(Seq[Any](/*50.6*/climateService/*50.20*/.getPurpose())),format.raw/*50.33*/(""" </font></span></td>
		
		<td class = "text-center" ><span class=""""),_display_(Seq[Any](/*52.44*/climateService/*52.58*/.getClimateServiceName())),format.raw/*52.82*/(""" editable" id = "url" data-name='url'>
				<a href = """"),_display_(Seq[Any](/*53.17*/{routes.ClimateServiceController.oneService(climateService.getUrl())})),format.raw/*53.86*/(""""><img src='"""),_display_(Seq[Any](/*53.99*/climateService/*53.113*/.getPhoto())),format.raw/*53.124*/("""' style="height: 150px; width: 150px" ></a> </span></td>
	
	</tr>
	""")))})),format.raw/*56.3*/("""
    </table>
 
    
     """),_display_(Seq[Any](/*60.7*/if(false)/*60.16*/{_display_(Seq[Any](format.raw/*60.17*/("""
		    
	    """),_display_(Seq[Any](/*62.7*/form(routes.ClimateServiceController.downloadClimateService(), 'class -> "form-horizontal", 'role -> "form")/*62.115*/ {_display_(Seq[Any](format.raw/*62.117*/("""
	
	        <button type="submit" name="action" value="download">Download</button>
	    """)))})),format.raw/*65.7*/("""
    """)))})),format.raw/*66.6*/("""
""")))})),format.raw/*67.2*/("""
"""))}
    }
    
    def render(climateServices:List[metadata.ClimateService],climateServiceForm:play.data.Form[metadata.ClimateService]): play.api.templates.HtmlFormat.Appendable = apply(climateServices,climateServiceForm)
    
    def f:((List[metadata.ClimateService],play.data.Form[metadata.ClimateService]) => play.api.templates.HtmlFormat.Appendable) = (climateServices,climateServiceForm) => apply(climateServices,climateServiceForm)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/climateServices.scala.html
                    HASH: d0622f085180a71e2e5c750054cd6664c78afd6d
                    MATRIX: 3250->1205|3453->1335|3468->1342|3553->1346|3605->1362|3620->1368|3682->1408|3784->1482|3813->1483|3843->1486|3871->1487|3927->1314|3956->1333|3984->1502|4022->1505|4064->1538|4104->1540|4144->1545|4181->1560|4233->1576|4257->1591|4286->1598|4599->1876|4653->1914|4692->1915|4753->1940|4844->2009|4899->2027|4923->2041|4970->2065|5044->2103|5067->2117|5113->2141|5201->2194|5224->2208|5259->2221|5362->2288|5385->2302|5431->2326|5522->2381|5613->2450|5662->2463|5686->2477|5720->2488|5819->2556|5881->2583|5899->2592|5938->2593|5987->2607|6105->2715|6146->2717|6266->2806|6303->2812|6336->2814
                    LINES: 56->18|59->22|59->22|61->22|62->23|62->23|62->23|64->25|64->25|66->27|66->27|69->18|71->21|72->29|74->31|74->31|74->31|76->33|76->33|78->35|78->35|78->35|88->45|88->45|88->45|90->47|90->47|90->47|90->47|90->47|92->49|92->49|92->49|93->50|93->50|93->50|95->52|95->52|95->52|96->53|96->53|96->53|96->53|96->53|99->56|103->60|103->60|103->60|105->62|105->62|105->62|108->65|109->66|110->67
                    -- GENERATED --
                */
            