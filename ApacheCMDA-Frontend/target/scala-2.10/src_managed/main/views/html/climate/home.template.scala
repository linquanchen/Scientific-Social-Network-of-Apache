
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
object home extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[String,String,String,play.api.templates.HtmlFormat.Appendable] {

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
    def apply/*18.2*/(email: String, vfile: String, dataset: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._ 


Seq[Any](format.raw/*18.49*/("""

"""),format.raw/*21.1*/("""
"""),_display_(Seq[Any](/*22.2*/main("Home")/*22.14*/ {_display_(Seq[Any](format.raw/*22.16*/("""

<div class="jumbotron">
	<div class="row">

		<div class="col-lg-5">
			</br></br>
			<img src="/assets/images/main.jpg" height="480" width="330" />
		</div>
		<div class="col-lg-6">
			<h2>Climate Model Diagnostic Analyzer</h2>
			<br>
			<p> A repository of web services for multi-aspect physics-based and 
				phenomenon-oriented phenomenon-oriented climate model performance
				evaluation and diagnosis through the comprehensive and synergistic 
				use of multiple observational data, reanalysis data, and model outputs.
			</p>
			<br>
			<p> This repository is specially customized to support the 2015 JPL Center
				for Climate Sciences Summer School. The theme of the summer school is 
				<b>Using Satellite Observations to Advance Climate Models</b>. This repository 
				provides datasets and analysis tools for the students to use for their 
				group research projects.
			</p>
			
			<!-- """),_display_(Seq[Any](/*47.10*/if(!session.get("username"))/*47.38*/{_display_(Seq[Any](format.raw/*47.39*/(""" -->
			
			<p>
				"""),_display_(Seq[Any](/*50.6*/if(false)/*50.15*/{_display_(Seq[Any](format.raw/*50.16*/(""" <a class="btn btn-primary" href="climate/register"
					role="button">Register &raquo;</a> """)))})),format.raw/*51.42*/("""
			</p>
			<!-- """)))})),format.raw/*53.10*/(""" -->
		</div>
	</div>
</div>

""")))})),format.raw/*58.2*/("""
"""))}
    }
    
    def render(email:String,vfile:String,dataset:String): play.api.templates.HtmlFormat.Appendable = apply(email,vfile,dataset)
    
    def f:((String,String,String) => play.api.templates.HtmlFormat.Appendable) = (email,vfile,dataset) => apply(email,vfile,dataset)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/home.scala.html
                    HASH: fb5c5c9a762e71e7b93424b00a3fe3ff8e71fe74
                    MATRIX: 3190->1205|3349->1252|3378->1272|3415->1274|3436->1286|3476->1288|4420->2196|4457->2224|4496->2225|4552->2246|4570->2255|4609->2256|4734->2349|4784->2367|4846->2398
                    LINES: 56->18|60->18|62->21|63->22|63->22|63->22|88->47|88->47|88->47|91->50|91->50|91->50|92->51|94->53|99->58
                    -- GENERATED --
                */
            