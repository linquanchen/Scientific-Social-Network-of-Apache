
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
object tutorial extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.HtmlFormat.Appendable] {

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
    def apply():play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](_display_(Seq[Any](/*18.2*/main("Tutorial")/*18.18*/ {_display_(Seq[Any](format.raw/*18.20*/("""

<ol class="breadcrumb">
	<li class="active">Tutorial</li>
	<li><a href="/estimator">Collect Data</a></li>
	<li><a href="/estimator/1">Input Preference</a></li>
	<li><a href="/estimator/2">Workflow Summary</a></li>
	<li><a href="/estimator/3">Recommendation</a></li>
</ol>

<iframe width="550" height="315" src="https://www.youtube.com/embed/t9tgr3I2d5g" frameborder="0" allowfullscreen></iframe>
<iframe width="550" height="315" style="margin-left: 25px" src="https://www.youtube.com/embed/tftPn4YQbVE" frameborder="0" allowfullscreen></iframe>

<p>
In these videos, we will help you learn the available web services, how to use them, and help you to design your climate analytics workflows.

<!-- <ul>
<li>Run vistrails. (python VISTRAILS_PATH/vistrails/vistrails.py)</li>
<li>Import a workflow.</li>
<li>Click the menu Packages, and then Amazon Plugin. Inside that choose the 'Go to estimator'</li>
<li>Input the notification email and click Estimate. Then you can be directed to the estimator web page.</li>
</ul> -->
</p>

""")))})))}
    }
    
    def render(): play.api.templates.HtmlFormat.Appendable = apply()
    
    def f:(() => play.api.templates.HtmlFormat.Appendable) = () => apply()
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/tutorial.scala.html
                    HASH: 1e7bfca2424dd940cf9b13846237a72ca8362f99
                    MATRIX: 3270->1205|3295->1221|3335->1223
                    LINES: 59->18|59->18|59->18
                    -- GENERATED --
                */
            