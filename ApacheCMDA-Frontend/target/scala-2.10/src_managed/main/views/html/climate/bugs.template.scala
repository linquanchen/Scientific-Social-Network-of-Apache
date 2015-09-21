
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
object bugs extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[LinkedList[BugReport],play.api.templates.HtmlFormat.Appendable] {

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
    def apply/*18.2*/(bugReports: LinkedList[BugReport]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*18.37*/("""

"""),format.raw/*21.1*/("""
"""),_display_(Seq[Any](/*22.2*/main("Bug Reports")/*22.21*/ {_display_(Seq[Any](format.raw/*22.23*/("""
    
    """),_display_(Seq[Any](/*24.6*/flash_message())),format.raw/*24.21*/("""
        
    <h1> Bug Reports(s)</h1>
		<table class="table table-striped table-bordered ">
			<thead ">
			<tr>
	    	  <td>Solved</td>
	    	  <td>Report Title</td>
	    	  <td>Reporter Name</td>
	    	  <td>Reporter's email</td>
	    	  <td>Reporter's organization</td>
	    	  <td>Bug content</td>
	    	  <td>Operation</td>
	        </tr>
			</thead>
			
			<tbody>
            <tr>
               """),_display_(Seq[Any](/*42.17*/for(bugReport <- bugReports) yield /*42.45*/ {_display_(Seq[Any](format.raw/*42.47*/("""
                    <tr>
                    	<td>
                    		"""),_display_(Seq[Any](/*45.24*/{if(bugReport.getSolved()==1){"Yes"}
                    			else{"No"}
                    		})),format.raw/*47.24*/("""
                    			
                    	</td>
                        <td>
                            """),_display_(Seq[Any](/*51.30*/bugReport/*51.39*/.getTitle())),format.raw/*51.50*/("""
                        </td>
                        <td> 
                            """),_display_(Seq[Any](/*54.30*/bugReport/*54.39*/.getName())),format.raw/*54.49*/("""
                        </td>
                        <td> 
                            """),_display_(Seq[Any](/*57.30*/bugReport/*57.39*/.getEmail())),format.raw/*57.50*/("""
                        </td>
                        <td> 
                            """),_display_(Seq[Any](/*60.30*/bugReport/*60.39*/.getOrganization())),format.raw/*60.57*/("""
                        </td>
                        <td> 
                            """),_display_(Seq[Any](/*63.30*/bugReport/*63.39*/.getDescription())),format.raw/*63.56*/("""
                        </td>
                        
                        
                        <td>
                        <div class="input-group input-group-sm">
                        <span>
						"""),_display_(Seq[Any](/*70.8*/form(routes.BugReportController.solveReport())/*70.54*/{_display_(Seq[Any](format.raw/*70.55*/("""
               		 		<input name="idHolder" type="hidden" value=""""),_display_(Seq[Any](/*71.66*/bugReport/*71.75*/.getId())),format.raw/*71.83*/("""">
        					<input type="submit"  class="btn btn-success" value="Solve" onclick="return confirm('Are you sure you want to solve this issue?')">
        				""")))})),format.raw/*73.14*/("""	
        				</span>
                        <span>
						"""),_display_(Seq[Any](/*76.8*/form(routes.BugReportController.deleteReport())/*76.55*/{_display_(Seq[Any](format.raw/*76.56*/("""
               		 		<input name="idHolder" type="hidden" value=""""),_display_(Seq[Any](/*77.66*/bugReport/*77.75*/.getId())),format.raw/*77.83*/("""">
        					<input type="submit"  class="btn btn-danger" value="Delete" onclick="return confirm('Are you sure you want to delete this issue?')">
        				""")))})),format.raw/*79.14*/("""	
        				</span>
        				</div>
           				
						
						</td>
                    </tr>
                """)))})),format.raw/*86.18*/("""
            </tr>
            </tbody>

    </table>
    
""")))})))}
    }
    
    def render(bugReports:LinkedList[BugReport]): play.api.templates.HtmlFormat.Appendable = apply(bugReports)
    
    def f:((LinkedList[BugReport]) => play.api.templates.HtmlFormat.Appendable) = (bugReports) => apply(bugReports)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/bugs.scala.html
                    HASH: a68343c70d8cc00092db4ebadda46023e3406191
                    MATRIX: 3191->1205|3337->1240|3366->1259|3403->1261|3431->1280|3471->1282|3517->1293|3554->1308|3995->1713|4039->1741|4079->1743|4190->1818|4306->1912|4452->2022|4470->2031|4503->2042|4629->2132|4647->2141|4679->2151|4805->2241|4823->2250|4856->2261|4982->2351|5000->2360|5040->2378|5166->2468|5184->2477|5223->2494|5471->2707|5526->2753|5565->2754|5667->2820|5685->2829|5715->2837|5908->2998|6003->3058|6059->3105|6098->3106|6200->3172|6218->3181|6248->3189|6442->3351|6593->3470
                    LINES: 56->18|60->18|62->21|63->22|63->22|63->22|65->24|65->24|83->42|83->42|83->42|86->45|88->47|92->51|92->51|92->51|95->54|95->54|95->54|98->57|98->57|98->57|101->60|101->60|101->60|104->63|104->63|104->63|111->70|111->70|111->70|112->71|112->71|112->71|114->73|117->76|117->76|117->76|118->77|118->77|118->77|120->79|127->86
                    -- GENERATED --
                */
            