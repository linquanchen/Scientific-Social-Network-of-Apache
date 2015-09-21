
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
object addClimateServices extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[play.data.Form[metadata.ClimateService],play.api.templates.HtmlFormat.Appendable] {

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
    def apply/*18.2*/(climateServiceForm: play.data.Form[metadata.ClimateService]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._

def /*21.2*/scripts/*21.9*/:play.api.templates.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*21.13*/("""
	<script src='"""),_display_(Seq[Any](/*22.16*/routes/*22.22*/.Assets.at("javascripts/edit_button.js"))),format.raw/*22.62*/("""'></script>
""")))};
Seq[Any](format.raw/*18.63*/("""
"""),format.raw/*20.1*/("""
"""),format.raw/*23.2*/("""

"""),_display_(Seq[Any](/*25.2*/main("Climate Services", scripts)/*25.35*/ {_display_(Seq[Any](format.raw/*25.37*/("""
	
	"""),_display_(Seq[Any](/*27.3*/flash_message())),format.raw/*27.18*/("""    
     """),_display_(Seq[Any](/*28.7*/if(true)/*28.15*/{_display_(Seq[Any](format.raw/*28.16*/("""
	    <h1>Add a new climate service</h1>
	    """),_display_(Seq[Any](/*30.7*/form(routes.ClimateServiceController.newClimateService())/*30.64*/ {_display_(Seq[Any](format.raw/*30.66*/("""   
	        """),_display_(Seq[Any](/*31.11*/inputText(
	        	climateServiceForm("Name"),
	        	'_label -> "Name * (Please DO NOT contain spaces)"
	        ))),format.raw/*34.11*/("""
	        """),_display_(Seq[Any](/*35.11*/inputText(
	        	climateServiceForm("Purpose"),
	        	'_label -> "Purpose"
	        ))),format.raw/*38.11*/("""
	        """),_display_(Seq[Any](/*39.11*/inputText(
	        	climateServiceForm("Url"),
	        	'_label -> "Url"
	        ))),format.raw/*42.11*/(""" 
	        """),_display_(Seq[Any](/*43.11*/inputText(
	        	climateServiceForm("Scenario"),
	        	'_label -> "Scenario"
	        ))),format.raw/*46.11*/("""
	         """),_display_(Seq[Any](/*47.12*/inputText(
	        	climateServiceForm("Version"),
	        	'_label -> "Version"
	        ))),format.raw/*50.11*/(""" 
	        """),_display_(Seq[Any](/*51.11*/inputText(
	        	climateServiceForm("Root_Service"),
	        	'_label -> "Root_Service"
	        ))),format.raw/*54.11*/("""
	        
	        <input class="btn" type="submit" value="Add a new service">
	        <a href=""""),_display_(Seq[Any](/*57.20*/routes/*57.26*/.ClimateServiceController.climateServices())),format.raw/*57.69*/("""" class="btn">Cancel</a>
	    """)))})),format.raw/*58.7*/("""
	
		<br>
		    
	    """),_display_(Seq[Any](/*62.7*/form(routes.ClimateServiceController.downloadClimateService(), 'class -> "form-horizontal", 'role -> "form")/*62.115*/ {_display_(Seq[Any](format.raw/*62.117*/("""
	
	        <button type="submit" name="action" value="download">Download</button>
	    """)))})),format.raw/*65.7*/("""
    """)))})),format.raw/*66.6*/("""
""")))})),format.raw/*67.2*/("""
"""))}
    }
    
    def render(climateServiceForm:play.data.Form[metadata.ClimateService]): play.api.templates.HtmlFormat.Appendable = apply(climateServiceForm)
    
    def f:((play.data.Form[metadata.ClimateService]) => play.api.templates.HtmlFormat.Appendable) = (climateServiceForm) => apply(climateServiceForm)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/addClimateServices.scala.html
                    HASH: 609d0f7c55c4496c586feb113d388fdff25ef4f9
                    MATRIX: 3223->1205|3378->1286|3393->1293|3478->1297|3530->1313|3545->1319|3607->1359|3660->1266|3688->1284|3716->1372|3754->1375|3796->1408|3836->1410|3876->1415|3913->1430|3959->1441|3976->1449|4015->1450|4097->1497|4163->1554|4203->1556|4253->1570|4395->1690|4442->1701|4557->1794|4604->1805|4711->1890|4759->1902|4876->1997|4924->2009|5039->2102|5087->2114|5212->2217|5347->2316|5362->2322|5427->2365|5489->2396|5547->2419|5665->2527|5706->2529|5826->2618|5863->2624|5896->2626
                    LINES: 56->18|59->21|59->21|61->21|62->22|62->22|62->22|64->18|65->20|66->23|68->25|68->25|68->25|70->27|70->27|71->28|71->28|71->28|73->30|73->30|73->30|74->31|77->34|78->35|81->38|82->39|85->42|86->43|89->46|90->47|93->50|94->51|97->54|100->57|100->57|100->57|101->58|105->62|105->62|105->62|108->65|109->66|110->67
                    -- GENERATED --
                */
            